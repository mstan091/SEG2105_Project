package uott.seg.mealerproject.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uott.seg.mealerproject.R;
import uott.seg.mealerproject.db.*;
import uott.seg.mealerproject.enums.*;
import uott.seg.mealerproject.misc.*;

public class MealViewAdapter extends SimpleAdapter implements View.OnClickListener {

    Context context;
    ArrayList<HashMap<String, String>> ahmMeal;
    protected DatabaseHandler db;

    public MealViewAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;

        ahmMeal = (ArrayList<HashMap<String, String>>) data;
        Log.d("ADAPTER List size = ", String.valueOf(ahmMeal.size()));
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);
        ListView lvMeals = view.findViewById(R.id.lvMeals);

        Button btnStatus = view.findViewById(R.id.btnStatus);
        //Button btnEdit = view.findViewById(R.id.btnEdit);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        btnStatus.setOnClickListener(this);
        //btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        btnStatus.setTag(position);
        //btnEdit.setTag(position);
        btnDelete.setTag(position);

        TextView tvMealName = view.findViewById(R.id.tvMyMealName);
        TextView tvMealStatus = view.findViewById(R.id.tvMealStatus);

        return view;
    }

    public void onClick(View v) {

        int n = (int)v.getTag();

        Log.d("Item index", String.valueOf(n));
        HashMap<String, String> mealItem = ahmMeal.get(n);
        long rowId = Long.parseLong(mealItem.get(Meal.MEAL_ID));
        Log.d("RowID " ,  String.valueOf(rowId));

        String mealName = mealItem.get(Meal.MEAL_NAME);
        String cookEmail = mealItem.get(Meal.COOK_EMAIL);
        EnumMealStatus curStatus = db.getMealStatus(cookEmail,mealName);

        switch (v.getId()) {
            case R.id.btnStatus:
                if (curStatus.getStatusCode() == -1) {
                    db.setMealStatus(EnumMealStatus.AVAILABLE, rowId);
                    // update the screen with the value in db
                    ahmMeal.get(n).put(Meal.MEAL_STATUS, String.valueOf(db.getMealStatus(cookEmail,mealName)));
                } else {
                    db.setMealStatus(EnumMealStatus.UNAVAILABLE, rowId);
                    // update the screen with the value in db
                    ahmMeal.get(n).put(Meal.MEAL_STATUS, String.valueOf(db.getMealStatus(cookEmail,mealName)));
                }
                Log.d("Status button","STATUS BUTTON");
                break;

            //case R.id.btnEdit:
                //db.setComplaintStatus(EnumComplaintStatus.SUSPENDED_COOK, rowId);

                //if (curStatus.getStatusCode() > EnumCookStatus.SUSPENDED.getStatusCode()) {
                //    db.setCookStatus(cookEmail, EnumCookStatus.SUSPENDED);
                //}
                //Log.d("Edit button","EDIT BUTTON");
            //    break;

            case R.id.btnDelete:
                // if the status is "Available" do not delete it
                if (curStatus.getStatusCode() != 0) {
                    db.removeMeal(rowId);
                    ahmMeal.remove(n);
                    break;
                } else {
                    Context ctx = v.getContext();
                    Toast.makeText(ctx, "Available meals cannot be deleted!", Toast.LENGTH_LONG).show();
                    break;
                }
        }
        notifyDataSetChanged();

    }

    public void setDBHelper(DatabaseHandler db) {
        this.db = db;
    }
}
