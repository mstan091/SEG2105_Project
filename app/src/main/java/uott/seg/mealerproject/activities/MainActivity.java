package uott.seg.mealerproject.activities;

import static android.view.View.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import uott.seg.mealerproject.R;
import uott.seg.mealerproject.db.DatabaseHandler;
import uott.seg.mealerproject.enums.EnumLoginStatus;
import uott.seg.mealerproject.enums.EnumUserType;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    String loginID;
    EnumUserType userType = EnumUserType.Client;

    RadioGroup rgLoginType;
    RadioButton rbClient, rbCook, rbAdmin;
    EditText edLoginID;

    protected DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onResume() {

        super.onResume();
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = null;

                Toast.makeText(MainActivity.this, "register", Toast.LENGTH_SHORT).show();

                if (userType == EnumUserType.Cook) {
                    intent = new Intent(view.getContext(), RegisterCookActivity.class);
                    intent.putExtra("LoginType", userType);
                    Toast.makeText(MainActivity.this, "Cook register !", Toast.LENGTH_SHORT).show();
                } else if (userType == EnumUserType.Client) {
                    intent = new Intent(view.getContext(), RegisterClientActivity.class);
                    intent.putExtra("LoginType", userType);
                } else {
                    Toast.makeText(MainActivity.this, "Admin no need to register !", Toast.LENGTH_SHORT).show();
                    return;
                }

                view.getContext().startActivity(intent);

            }
        });


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edLoginID = (EditText) findViewById(R.id.teLoginEmail);

        rbClient = (RadioButton) findViewById(R.id.rbClient);
        rbCook = (RadioButton) findViewById(R.id.rbCook);
        rbAdmin = (RadioButton) findViewById(R.id.rbAdmin);

        rbClient.setOnClickListener(this);
        rbCook.setOnClickListener(this);
        rbAdmin.setOnClickListener(this);


        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                EnumLoginStatus loginStatus = validateLogin(userType);


                if (loginStatus == EnumLoginStatus.SUCCESS ) {
                    Log.d("login", "Login successful");
                    Intent intent = new Intent(view.getContext(), WelcomeActivity.class);
                    intent.putExtra("UserID", edLoginID.getText().toString());
                    intent.putExtra("LoginType", userType);
                    view.getContext().startActivity(intent);

                } else if (loginStatus == EnumLoginStatus.USER_NOT_FOUND) {
                    EditText teEmail = findViewById(R.id.teLoginEmail);
                    teEmail.setError("User not registered");
                    Log.d("login ", "User not found");

                } else if (loginStatus == EnumLoginStatus.INVALID_PWD) {
                    EditText tePwd = findViewById(R.id.teLoginPwd);
                    tePwd.setError("Password not correct");
                    Log.d("login", "Invalid password") ;

                } else if (loginStatus == EnumLoginStatus.INVALID_USER_TYPE){
                    EditText teEmail = findViewById(R.id.teLoginEmail);
                    teEmail.setError("User not registered or wrong type");
                    Log.d("login", "Wrong user type");

                }
            }

        });

    }

    private EnumLoginStatus validateLogin(EnumUserType userType) {
        EditText teEmail = findViewById(R.id.teLoginEmail);
        EditText twPwd = findViewById(R.id.teLoginPwd);

        String email = teEmail.getText().toString();
        String pwd = twPwd.getText().toString();

        Log.d("login Check", "call db");
        EnumLoginStatus loginStatus = db.validateLogin(email, pwd, userType);
        return loginStatus;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rbClient:

                //if (rbClient.isChecked()) {
                    userType = EnumUserType.Client;
                    Toast.makeText(this, "Client login", Toast.LENGTH_SHORT).show();
                //}

                break;

            case R.id.rbCook:

                //if (rbClient.isChecked()) {
                    userType = EnumUserType.Cook;
                    Toast.makeText(this, "Cook login", Toast.LENGTH_SHORT).show();
                //}

                break;

            case R.id.rbAdmin:

                //if (rbClient.isChecked()) {
                    userType = EnumUserType.Admin;
                    Toast.makeText(this, "Admin login", Toast.LENGTH_SHORT).show();
                //}


        }
    }
}