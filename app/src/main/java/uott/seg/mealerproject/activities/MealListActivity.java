package uott.seg.mealerproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import uott.seg.mealerproject.R;
import uott.seg.mealerproject.db.*;
import uott.seg.mealerproject.adapters.*;
import uott.seg.mealerproject.misc.*;

public class MealListActivity extends AppCompatActivity {
    ArrayList<Meal> meals;
    ArrayList<HashMap<String, String>> viewDataMeals = new ArrayList<HashMap<String, String>>();
    protected DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);

        Intent intent = getIntent();
        String cookID = intent.getStringExtra("cookID");
        Log.e("cook welcome?", String.valueOf(cookID));

        meals = db.getMealList(cookID);
        Log.d("Meals size ", String.valueOf(meals.size()));

        meals.forEach(meal -> {
            HashMap<String, String> mealItem = new HashMap<>();
            mealItem.put(Meal.MEAL_ID, String.valueOf(meal.geMealID()));
            mealItem.put(Meal.MEAL_NAME, meal.getMealName());
            mealItem.put(Meal.MEAL_STATUS, String.valueOf(meal.getMealStatus()));
            mealItem.put(Meal.COOK_EMAIL, meal.getCookEmail());

            viewDataMeals.add(mealItem);
        });

        String[] from = {Meal.MEAL_NAME, Meal.MEAL_STATUS};
        int[] to = {R.id.tvMyMealName, R.id.tvMealStatus};

        MealViewAdapter adptMeal = new MealViewAdapter(this, viewDataMeals, R.layout.meal_item, from, to);
        adptMeal.setDBHelper(db);
        ListView lvMeals = findViewById(R.id.lvMeals);
        lvMeals.setAdapter(adptMeal);

        lvMeals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Listview click ", String.valueOf( i));
            }
        });

    }



}