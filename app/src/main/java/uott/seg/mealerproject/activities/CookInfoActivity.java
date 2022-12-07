package uott.seg.mealerproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import uott.seg.mealerproject.R;

public class CookInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_info);

        Intent intent = getIntent();

        String fName = (String) intent.getExtras().get("FirstName");
        String lName = (String) intent.getExtras().get("LastName");
        String desc = (String) intent.getExtras().get("Description");
        String addr = (String) intent.getExtras().get("Addr");
        float rating = (float) intent.getExtras().get("Rating");
        int numRating = (int) intent.getExtras().get("NumRatings");

        TextView tvFName = findViewById(R.id.tvFirstName);
        TextView tvLName = findViewById(R.id.tvLastName);
        TextView tvDesc = findViewById(R.id.tvCookDescription);
        TextView tvAddr = findViewById(R.id.tvCookAddress);
        TextView tvNumRating = findViewById(R.id.tvNumOfRatings);
        RatingBar rbRating = findViewById(R.id.rbCookRating);
        TextView tvRating = findViewById(R.id.tvCookRating);

        tvFName.setText(fName);
        tvLName.setText(lName);
        tvDesc.setText(desc);
        tvAddr.setText(addr);

        tvRating.setText(String.valueOf(rating));
        rbRating.setRating(rating);
        tvNumRating.setText(String.valueOf(numRating));

        Button btnBack = findViewById(R.id.btnBackToMealList);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SearchMealActivity.class);

                finish();
            }
        });

    }
}