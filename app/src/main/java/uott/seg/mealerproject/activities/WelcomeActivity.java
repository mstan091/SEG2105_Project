package uott.seg.mealerproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uott.seg.mealerproject.R;
import uott.seg.mealerproject.db.DatabaseHandler;
import uott.seg.mealerproject.enums.EnumLoginStatus;
import uott.seg.mealerproject.enums.EnumUserType;


public class WelcomeActivity extends AppCompatActivity {

    TextView tvWelcome;
    EnumUserType userType;
    String userID;
    String welcomeMsg;

    protected DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        userType = (EnumUserType) getIntent().getExtras().get("LoginType");
        userID = (String) getIntent().getExtras().get("UserID");


        Log.d("logintype", userType.toString());

        welcomeMsg = getString(R.string.welcome_msg, userType.toString());

        Log.d("login ********************", welcomeMsg);

        tvWelcome.setText(welcomeMsg);

        Button btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return true;
    }
}
