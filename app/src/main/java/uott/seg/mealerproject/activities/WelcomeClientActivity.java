package uott.seg.mealerproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import uott.seg.mealerproject.R;
import uott.seg.mealerproject.misc.MealOrder;

public class WelcomeClientActivity extends  WelcomeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_welcome);

        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvWelcome.setText(welcomeMsg);

        Button btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Intent intent = new Intent(view.getContext(), MainActivity.class);
                //view.getContext().startActivity(intent);

                finish();
            }
        });

        Button btnViewOrder = (Button) findViewById(R.id.btnViewOrder);
        btnViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MyOrderActivity.class);
                intent.putExtra("UserID", userID);

                //ArrayList<MealOrder> myOrder = db.getMyCurrentOrder(userID);
                view.getContext().startActivity(intent);
            }
        });

        Button btnFindFood = (Button) findViewById(R.id.btnSearchMeal);
        btnFindFood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SearchMealActivity.class);

                EditText teMealName = findViewById(R.id.teMealName);
                EditText teMealType = findViewById(R.id.teMealType);
                EditText teCuisineType = findViewById(R.id.teCuisineType);

                intent.putExtra(getResources().getString(R.string.meal_name), teMealName.getText().toString());
                intent.putExtra(getResources().getString(R.string.meal_type), teMealType.getText().toString());
                intent.putExtra(getResources().getString(R.string.cuisine_type), teCuisineType.getText().toString());
                intent.putExtra("UserID", userID);

                view.getContext().startActivity(intent);
            }
        });
    }
}