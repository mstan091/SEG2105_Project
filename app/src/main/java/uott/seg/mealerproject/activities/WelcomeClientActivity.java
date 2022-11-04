package uott.seg.mealerproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uott.seg.mealerproject.R;
import uott.seg.mealerproject.enums.EnumCookStatus;

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
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                // MS
                intent.putExtra("fromLogout", true);
                // End MS
                view.getContext().startActivity(intent);
            }
        });
    }
}