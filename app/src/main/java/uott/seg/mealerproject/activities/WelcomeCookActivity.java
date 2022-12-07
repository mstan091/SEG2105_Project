package uott.seg.mealerproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uott.seg.mealerproject.R;
import uott.seg.mealerproject.enums.EnumCookStatus;

public class WelcomeCookActivity extends  WelcomeActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_welcome);


        EnumCookStatus status = db.getCookStatus(userID);
        String statusMsg = getString(R.string.cook_status_msg, status.toString());

        TextView tvCookStatus = findViewById(R.id.tvCookStatus);
        tvCookStatus.setText(statusMsg);

        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvWelcome.setText(welcomeMsg);

        if (status != EnumCookStatus.NORMAL) {
            Button btnM2 = findViewById(R.id.btnAddMealMenu);
            btnM2.setEnabled(false);
            Button btnM1 = findViewById(R.id.btnEditMealList);
            btnM1.setEnabled(false);
        } else {
            Button btnM2 = findViewById(R.id.btnAddMealMenu);
            btnM2.setEnabled(true);
            Button btnM1 = findViewById(R.id.btnEditMealList);
            btnM1.setEnabled(true);
        }

        Button btnViewOrder = (Button) findViewById(R.id.btnProcessOrder);
        btnViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProcessOrderActivity.class);
                intent.putExtra("UserID", userID);

                view.getContext().startActivity(intent);
            }
        });

        Button btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                // MS
                intent.putExtra("fromLogout", true);
                // End MS
                view.getContext().startActivity(intent);
            }
        });

        Button btnMeals = (Button) findViewById(R.id.btnEditMealList);
        btnMeals.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MealListActivity.class);
                // MS
                Log.d("cookID",userID);
                Log.d("LoginType",userType.toString());
                intent.putExtra("cookID", userID);
                intent.putExtra("LoginType", userType);
                // End MS
                view.getContext().startActivity(intent);
            }
        });

        Button btnAddMeal = (Button) findViewById(R.id.btnAddMealMenu);
        btnAddMeal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddMealActivity.class);
                // MS
                Log.d("cookID",userID);
                Log.d("LoginType",userType.toString());
                intent.putExtra("cookID", userID);
                intent.putExtra("LoginType", userType);
                view.getContext().startActivity(intent);
            }
        });

        Button btnMyProfile = (Button) findViewById(R.id.btnMyDetails);
        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MyCookDetails.class);
                intent.putExtra("cookID", userID);
                view.getContext().startActivity(intent);


            }
            });
        }

    }
