package uott.seg.mealerproject.adapters;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uott.seg.mealerproject.R;
import uott.seg.mealerproject.activities.CookInfoActivity;
import uott.seg.mealerproject.db.DatabaseHandler;
import uott.seg.mealerproject.misc.Meal;
import uott.seg.mealerproject.users.MealerUserCook;

public class MealSearchViewAdapter extends SimpleAdapter implements View.OnClickListener {
    Context context;
    ArrayList<HashMap<String, Object>> ahmMeal;
    protected DatabaseHandler db;
    String userID;

    public MealSearchViewAdapter(Context context, List<? extends Map<String, Object>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;

        ahmMeal = (ArrayList<HashMap<String, Object>>) data;
        Log.d("List size = ", String.valueOf(ahmMeal.size()));


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        RatingBar rb = (RatingBar)view.findViewById(R.id.ratingBar);
        HashMap<String, Object> mealItem = ahmMeal.get(position);

        long MealID = (long) mealItem.get(Meal.MEAL_ID);
        float rating = Float.valueOf((String)mealItem.get("rating"));
        //Log.d("Rating ", mealItem.get("rating"));
        rb.setRating(rating);


        Button btnCookInfo = (Button) view.findViewById(R.id.btnCookInfo);
        btnCookInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MealerUserCook cook = (MealerUserCook) mealItem.get("cookInfo");
                Intent intent = new Intent(view.getContext(), CookInfoActivity.class);

                intent.putExtra("FirstName", cook.getfName());
                intent.putExtra("LastName", cook.getlName());
                intent.putExtra("Description", cook.getCookDescription());
                intent.putExtra("Addr", cook.getAddr());
                intent.putExtra("Rating", cook.getRating());
                intent.putExtra("NumRatings", cook.getNumRating());

                view.getContext().startActivity(intent);
            }
        });

        TextView tvPickupTime = view.findViewById(R.id.tvMyPickupTime);
        TextView tvMealName = view.findViewById(R.id.tvMyMealName);
        TextView tvMealType = view.findViewById(R.id.tvMyMealType);
        TextView tvCuisineType = view.findViewById(R.id.tvMyCuisineType);
        TextView tvCookEmail = view.findViewById(R.id.tvCookEmail);

        Button btnTime = (Button) view.findViewById(R.id.btnAccept);
        Button btnOrder = (Button) view.findViewById(R.id.btnReject);
        btnOrder.setTag(MealID);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(view.getContext(), MealOrderActivity.class);
                String mealName = (String) mealItem.get(Meal.MEAL_NAME);
                String mealType = (String) mealItem.get(Meal.MEAL_TYPE);
                String cuisineType = (String) mealItem.get(Meal.CUISINE_TYPE);
                String cookEmail = (String) mealItem.get(Meal.COOK_EMAIL);

                String pickupTime = tvPickupTime.getText().toString();
                Log.d("Order  =============", mealName);
                Log.d("Order  =============", pickupTime);

                Log.d("Order  =============", mealType);
                Log.d("Order  =============", cuisineType);
                Log.d("Order  =============", cookEmail);

                if (!pickupTime.isEmpty()) {
                    db.addOrder(cookEmail, userID, mealName, mealType, cuisineType, pickupTime);
                    Log.d("Aici", "btnReject");
                    btnOrder.setText("ORDERED");
                    btnOrder.setEnabled(false);

                } else {

                    btnTime.setError("Select a pickup time");
                }
            }
        });


        //TextView tvPickupTime = (TextView) view.findViewById(R.id.tvPickupTime);
        Button btnDatePicker = (Button) view.findViewById(R.id.btnAccept);
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay, mHour, mMinute;
                final Calendar c = Calendar.getInstance();
                String[] pickupTime = new String[2];

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                btnTime.setError(null);

                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                //tvPickupTime.setText(hourOfDay + ":" + minute);
                                pickupTime[1] = hourOfDay + ":" + minute;
                                tvPickupTime.setText(pickupTime[0] + " " + pickupTime[1]);
                            }
                        }, mHour, mMinute, false);



                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext());
                DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        //tvPickupTime.setText(year + "/" + monthOfYear + "/" + dayOfMonth);

                        pickupTime[0] = year + "/" + monthOfYear + "/" + dayOfMonth;
                        timePickerDialog.show();
                    }
                };

                datePickerDialog.setOnDateSetListener(d);
                datePickerDialog.show();
            }
        });


        return view;

    }


    @Override
    public void onClick(View v) {

    }

    public void setDBHelper(DatabaseHandler db) {
        this.db = db;
    }
    public void setUserID(String userID) {this.userID = userID;}
}
