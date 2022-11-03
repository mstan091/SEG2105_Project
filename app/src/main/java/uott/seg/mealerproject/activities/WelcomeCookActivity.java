package uott.seg.mealerproject.activities;

import android.content.Intent;
import android.os.Bundle;
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