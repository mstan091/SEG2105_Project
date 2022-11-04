package uott.seg.mealerproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uott.seg.mealerproject.R;

public class WelcomeAdminActivity extends  WelcomeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);

        Log.d("login as *************", welcomeMsg);
        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvWelcome.setText(welcomeMsg);

        Button btnCompl = findViewById(R.id.btnComplaint);
        btnCompl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ComplaintActivity.class);

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
    }






}