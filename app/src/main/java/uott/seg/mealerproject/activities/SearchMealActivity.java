package uott.seg.mealerproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import uott.seg.mealerproject.R;
import uott.seg.mealerproject.adapters.MealSearchViewAdapter;
import uott.seg.mealerproject.db.DatabaseHandler;
import uott.seg.mealerproject.misc.Meal;
import uott.seg.mealerproject.users.MealerUserCook;

public class SearchMealActivity extends AppCompatActivity {

    ArrayList<Meal> meals;
    ArrayList<HashMap<String, Object>> viewDataMeal = new ArrayList<>();
    protected DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_meal);

        Button btnBack2Search = (Button) findViewById(R.id.btnBack2CookWelcom);
        btnBack2Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String mealName, mealType, cuisineType, userID;

        mealName = (String) getIntent().getExtras().get("MealName");
        mealType = (String) getIntent().getExtras().get("MealType");
        cuisineType = (String) getIntent().getExtras().get("CuisineType");
        userID = (String) getIntent().getExtras().get("UserID");

        mealName = mealName.isEmpty()? "%" : mealName;
        mealType = mealType.isEmpty()? "%" : mealType;
        cuisineType = cuisineType.isEmpty()? "%" : cuisineType;

        Log.d("Meal Nmae", mealName);
        Log.d("Meal Type", mealType);
        Log.d("Cuisine Type", cuisineType);
        Log.d("User id ", userID);

        meals = db.getMealSearchResult(mealName, mealType, cuisineType);

        Log.d("meal size ", String.valueOf(meals.size())) ;


        meals.forEach(meal -> {
            HashMap<String, Object> hsMeal = new HashMap<>();

            hsMeal.put(Meal.MEAL_ID, meal.geMealID());
            hsMeal.put(Meal.MEAL_NAME, meal.getMealName());
            hsMeal.put(Meal.MEAL_TYPE, meal.getMealType());
            hsMeal.put(Meal.CUISINE_TYPE, meal.getCuisineType());

            hsMeal.put(Meal.MEAL_STATUS, meal.getMealStatus().toString());
            hsMeal.put(Meal.COOK_EMAIL, meal.getCookEmail());
            hsMeal.put(Meal.MEAL_ALLERGENS, meal.getAllergens());

            hsMeal.put(Meal.MEAL_PRICE, String.valueOf(meal.getPrice()));
            hsMeal.put(Meal.MEAL_INGREDIENTS, meal.getIngredients());
            hsMeal.put(Meal.MEAL_DESCRIPTION, meal.getDesc());

            MealerUserCook cook = meal.getCook();

            hsMeal.put("addr", meal.getCook().getAddr());
            hsMeal.put("rating", String.valueOf(cook.getRating()));
            hsMeal.put("ingredients", meal.getIngredients());
            hsMeal.put("allergens", meal.getAllergens());
            hsMeal.put("cookInfo", cook);


            viewDataMeal.add(hsMeal);
        });

        String[] from = {Meal.MEAL_NAME, Meal.MEAL_TYPE, Meal.CUISINE_TYPE, Meal.MEAL_PRICE, Meal.MEAL_DESCRIPTION, "addr", "rating", "ingredients", "allergens"};
        int[] to = {R.id.tvMyMealName, R.id.tvMyMealType, R.id.tvMyCuisineType, R.id.tvPrice, R.id.tvMealDesc, R.id.tvPickupAddress, R.id.tvRating, R.id.tvMealIngredients, R.id.tvMealAllergens};

        MealSearchViewAdapter adptMeal = new MealSearchViewAdapter(this, viewDataMeal, R.layout.search_meal_item, from, to);

        adptMeal.setDBHelper(db);
        adptMeal.setUserID(userID);

        ListView lvMeals = findViewById(R.id.lvMealSearch);
        lvMeals.setAdapter(adptMeal);



    }
}