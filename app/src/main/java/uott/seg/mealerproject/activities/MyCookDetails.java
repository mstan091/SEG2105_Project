package uott.seg.mealerproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import uott.seg.mealerproject.R;
import uott.seg.mealerproject.db.DatabaseHandler;
import uott.seg.mealerproject.enums.EnumUserType;
import uott.seg.mealerproject.users.MealerUserCook;

public class MyCookDetails extends AppCompatActivity {

    String cookID;
    protected DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycook_details);

        Intent intent = getIntent();
        cookID = intent.getStringExtra("cookID");
        //userType = (EnumUserType) getIntent().getExtras().get("LoginType");
        //rbAvailable = (RadioButton) findViewById(R.id.rbAddAvailable);

        MealerUserCook mCook = db.getCookDescRating(cookID);

        String cookEmail = cookID;
        String Desc = mCook.getCookDescription();
        TextView cookDesc = (TextView) findViewById(R.id.tvCookDescription);
        cookDesc.setText(Desc);

        String fName = mCook.getfName();
        TextView cookFname = (TextView) findViewById(R.id.tvFirstName);
        cookFname.setText(fName);

        String lName = mCook.getfName();
        TextView cookLname = (TextView) findViewById(R.id.tvLastName);
        cookFname.setText(lName);

        String addr = mCook.getAddr();
        TextView cookAddr = (TextView) findViewById(R.id.tvCookAddress);
        cookAddr.setText(addr);

        float rating = mCook.getRating();
        TextView tvRating = (TextView) findViewById(R.id.tvCookRating);
        tvRating.setText(Integer.toString(Math.round(rating)));

        int cookNRating = mCook.getNumRating();
        TextView tvcookNRating = (TextView) findViewById(R.id.tvNumOfRatings);
        tvcookNRating.setText(Integer.toString(cookNRating));

        RatingBar rb = (RatingBar) findViewById(R.id.rbCookRating);
        rb.setRating(rating);



        //tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        //userID = (String) getIntent().getExtras().get("UserID");

    }
}
