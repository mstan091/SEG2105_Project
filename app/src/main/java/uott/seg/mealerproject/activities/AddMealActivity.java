package uott.seg.mealerproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import uott.seg.mealerproject.R;
import uott.seg.mealerproject.db.*;
import uott.seg.mealerproject.enums.*;
import uott.seg.mealerproject.misc.*;
import uott.seg.mealerproject.users.*;

public class AddMealActivity extends AppCompatActivity implements View.OnClickListener {
    String mealName;
    String mealType;
    String cuisineType;
    EnumMealStatus status;
    String cookID;
    String allergens;
    String ingredients;
    float price;
    String description;
    RadioGroup rgStatus;
    RadioButton rbAvailable, rbUnavailable;
    EnumUserType userType;
    boolean isValid;

    protected static final int MIN_LEN_MEAL_NAME = 1;
    protected static final int MAX_LEN_MEAL_NAME = 30;
    protected static final int MIN_LEN_MEAL_TYPE = 4;
    protected static final int MAX_LEN_MEAL_TYPE = 30;
    protected static final int MIN_LEN_CUISINE_TYPE =4;
    protected static final int MAX_LEN_CUISINE_TYPE = 30;
    protected static final int MIN_LEN_ALLERGENS = 4;
    protected static final int MAX_LEN_ALLERGENS = 30;
    protected static final int MIN_LEN_INGREDIENTS =4;
    protected static final int MAX_LEN_INGREDIENTS = 60;
    protected static final int MIN_LEN_PRICE =1;
    protected static final int MAX_LEN_PRICE = 5;
    protected static final int MIN_LEN_DESC =1;
    protected static final int MAX_LEN_DESC = 60;


    protected DatabaseHandler db = new DatabaseHandler(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        Intent intent = getIntent();
        cookID = intent.getStringExtra("cookID");
        userType = (EnumUserType) getIntent().getExtras().get("LoginType");
        Log.d("cookID",cookID);
        Log.d("LoginType",userType.toString());
        //Log.d("AddMealActivity ONCREATE", String.valueOf(cookID));
        rbAvailable = (RadioButton) findViewById(R.id.rbAddAvailable);
        rbUnavailable = (RadioButton) findViewById(R.id.rbAddUnavailable);
        rbAvailable.setOnClickListener(this);
        rbUnavailable.setOnClickListener(this);

        Button btnSaveMeal = (Button) findViewById(R.id.btnSaveMeal);
        btnSaveMeal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                validateUserInput();
                if (isValid) {
                    if (rbAvailable.isChecked())
                    {
                        // no radio buttons are checked
                        status = EnumMealStatus.AVAILABLE;
                    }
                    Meal newMeal = new Meal(mealName, mealType,cuisineType, cookID, allergens, price, ingredients,description,status);
                    Log.d("DB insert", "onClick AddMealActivity");
                    db.addMeal(newMeal);
                    Intent intent = new Intent(view.getContext(), WelcomeCookActivity.class);
                    Log.d("cookID_1",cookID);
                    Log.d("LoginType_1",userType.toString());
                    intent.putExtra("cookID", cookID);
                    // In WelcomeCookActivity I need UserID anf LoginType
                    intent.putExtra("UserID", cookID);
                    intent.putExtra("LoginType", userType);

                    view.getContext().startActivity(intent);
                    Toast.makeText(AddMealActivity.this, "The menu has been updated.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void validateUserInput() {
        EditText teAddMealName = findViewById(R.id.teAddMealName);
        EditText teAddMealType = findViewById(R.id.teAddMealType);
        EditText teAddCuisineType = findViewById(R.id.teAddCuisineType);
        EditText teAddAllergens = findViewById(R.id.teAddAllergens);
        EditText teAddIngredients = findViewById(R.id.teAddIngredients);
        EditText teAddPrice = findViewById(R.id.teAddPrice);
        EditText teAddDescription = findViewById(R.id.teAddDescription);

        mealName = teAddMealName.getText().toString();
        if (mealName == null) {
            Log.d("mealName", "mealName is null");
        } else {
            Log.d("mealName", mealName);
        }
        mealType = teAddMealType.getText().toString();
        cuisineType = teAddCuisineType.getText().toString();
        allergens = teAddAllergens.getText().toString();
        ingredients = teAddIngredients.getText().toString();
        description =teAddDescription.getText().toString();
        // price= Float.parseFloat(teAddPrice.getText().toString());

        isValid = true;

        // if the price is empty
        if ((teAddPrice.getText().toString().length() == 0)) {
            isValid = false;
            teAddPrice.setError("Price cannot be empty");
        } else {
            // if the price is not empty check if it is a number
            if (teAddPrice.getText().toString().matches("[0-9]*\\.?[0-9]+")) {
                price= Float.parseFloat(teAddPrice.getText().toString());
            } else {
                //if the price is not empty but it is not a number
                isValid = false;
                teAddPrice.setError("Price is not a number");
            }
        }


        if (mealName.length() < MIN_LEN_MEAL_NAME || mealName.length() > MAX_LEN_MEAL_NAME) {
            teAddMealName.setError("Meal Name is too short or too long");
            isValid = false;
        }

        if (mealType.length() < MIN_LEN_MEAL_TYPE || mealType.length() > MAX_LEN_MEAL_TYPE) {
            teAddMealType.setError("Meal type is too short or too long");
            isValid = false;
        }

        if (cuisineType.length() < MIN_LEN_CUISINE_TYPE || cuisineType.length() > MAX_LEN_CUISINE_TYPE) {
            teAddCuisineType.setError("Cuisine type  is too short or too long");
            isValid = false;
        }


        if (allergens.length() < MIN_LEN_ALLERGENS || allergens.length() > MAX_LEN_ALLERGENS) {
            teAddAllergens.setError("Adllergens is too short or too long");
            isValid = false;
        }

        if (ingredients.length() < MIN_LEN_INGREDIENTS || ingredients.length() > MAX_LEN_INGREDIENTS) {
            teAddIngredients.setError("Ingredients is too short or too long");
            isValid = false;
        }

        if (description.length() < MIN_LEN_DESC || description.length() > MAX_LEN_DESC) {
            teAddDescription.setError("Meal Description is too short or too long");
            isValid = false;
        }
    }

    /*protected void onResume() {
        super.onResume();
        Log.d("RESUME", "RESUME AddMealActivity->MealList");
        Button btnSaveMeal = (Button) findViewById(R.id.btnSaveMeal);
        btnSaveMeal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MealListActivity.class);
                //Toast.makeText(MainActivity.this, "register", Toast.LENGTH_SHORT).show();
                Log.d("on resume", "onResume AddMealActivity");
                intent.putExtra("cookID", cookID);
                view.getContext().startActivity(intent);
            }
        });


    }*/

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rbAddAvailable:
                status = EnumMealStatus.AVAILABLE;
                Toast.makeText(this, "Available", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rbAddUnavailable:
                status = EnumMealStatus.UNAVAILABLE;
                Toast.makeText(this, "UNAvailable", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
