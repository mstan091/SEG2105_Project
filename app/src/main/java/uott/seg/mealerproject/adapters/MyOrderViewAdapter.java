package uott.seg.mealerproject.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uott.seg.mealerproject.R;
import uott.seg.mealerproject.db.DatabaseHandler;
import uott.seg.mealerproject.enums.EnumOrderStatus;
import uott.seg.mealerproject.misc.CookRating;
import uott.seg.mealerproject.misc.MealOrder;
import uott.seg.mealerproject.misc.UserComplaint;

public class MyOrderViewAdapter extends SimpleAdapter implements View.OnClickListener {

    Context context;
    ArrayList<HashMap<String, Object>> ahmMyOrder;
    protected DatabaseHandler db;
    int cookRate;


    public MyOrderViewAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);

        ahmMyOrder = (ArrayList<HashMap<String, Object>>) data;
        Log.d("ADAPTER List size = ", String.valueOf(ahmMyOrder.size()));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);
        ListView lvMyOrder = view.findViewById(R.id.lvMyOrder);
        HashMap<String, Object> orderItem = ahmMyOrder.get(position);

        long rowID;
        CookRating cookRating;
        String cookEmail, clientEmail, complMsg;
        EnumOrderStatus status;
        float rating;
        rowID = (long)orderItem.get(MealOrder.ORDER_ID);

        cookEmail = (String)orderItem.get(MealOrder.COOK_EMAIL);
        clientEmail = (String)orderItem.get(MealOrder.CLIENT_EMAIL);
        cookRating = db.getCookRating(cookEmail);

        EditText teCompl = view.findViewById(R.id.teMyComplaint);
        RatingBar rbMyCookRate = view.findViewById(R.id.rbMyCookRate);
        Button btnSubmit = view.findViewById(R.id.btnSubmit);
        Button btnRate = view.findViewById(R.id.btnRate);

        //AICI
        // GET STATUS: if not Completed you cannot rate
        status = db.getMyOrderStatus(rowID);
        Log.d("Order status", status.toString());
        // GET RATING
        rating = db.getMyOrderRating(rowID);
        Log.d("MYORDERRATING", String.valueOf(rating));
        // GET COMPLAINT
        complMsg = db.getMyOderUserComplaints(rowID);
        //Log.d("COMPLAINT_MESSAGE", complMsg);

        if (status.toString().equals("Completed") || status.toString().equals("Approved")) {
            if ((Math.round(rating) !=0)) {
                btnRate.setEnabled(false);
                rbMyCookRate.setRating(Math.round(rating));
            } else {
                btnRate.setEnabled(true);
            }
            // Complaints
            if (complMsg==null) {
                teCompl.setEnabled(true);
                btnSubmit.setEnabled(true);
                // if there are complaints
            } else {
                teCompl.setEnabled(false);
                btnSubmit.setEnabled(false);
                teCompl.setText(complMsg);
            }
        } else if (status.toString().equals("Pending")) {
            btnRate.setEnabled(false);
            // if there are no complaints
            if (complMsg==null) {
                teCompl.setEnabled(true);
                btnSubmit.setEnabled(true);
            // if there are complaints
            } else {
                teCompl.setEnabled(false);
                btnSubmit.setEnabled(false);
                teCompl.setText(complMsg);
            }

        } else {
            Log.d("RATING", String.valueOf(Math.round(rating)));
            if (Math.round(rating) == 0) {
                btnRate.setEnabled(true);
            } else {
                rbMyCookRate.setRating(Math.round(rating));
            }
        }

        btnSubmit.setTag(position);
        btnRate.setTag(position);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String complMsg = teCompl.getText().toString();

                if (!complMsg.isEmpty()) {
                    UserComplaint compl;
                    compl = new UserComplaint(cookEmail, clientEmail, complMsg);
                    db.addUserComplaints(compl);
                    Log.d("Comlaint",complMsg);
                    db.addMyOrderUserComplaints(complMsg, rowID);

                    btnSubmit.setText("Submitted");
                    btnSubmit.setEnabled(false);
                    teCompl.setEnabled(false);

                    db.setOrderStatus(EnumOrderStatus.COMPLETED, rowID);

                } else {
                    teCompl.setError("Write your complaint");
                }
            }
        });

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rating = (int)rbMyCookRate.getRating();
                Log.d("Cook's rating ", String.valueOf(rating));

                if (rating != 0) {
                    float newRating = (cookRating.getRating() * cookRating.getNumOfRating() + rating) / (cookRating.getNumOfRating() + 1);
                    db.setCookRating(cookEmail, newRating);

                    //AICI
                    db.setMyOrderRating(rowID,rating);

                    btnRate.setText("Submitted");
                    btnRate.setEnabled(false);
                    rbMyCookRate.setEnabled(false);

                    db.setOrderStatus(EnumOrderStatus.COMPLETED, rowID);
                }
            }
        });


        rbMyCookRate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                cookRate = (int) v;
                Log.d("New rating ============== ", String.valueOf(cookRate));

            }
        });

        return view;
    }


    @Override
    public void onClick(View view) {

    }

    public void setDBHelper(DatabaseHandler db) {
        this.db = db;
    }
}
