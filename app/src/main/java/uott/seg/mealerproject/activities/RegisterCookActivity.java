package uott.seg.mealerproject.activities;

import static android.view.View.*;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;

import uott.seg.mealerproject.R;
import uott.seg.mealerproject.enums.EnumUserType;
import uott.seg.mealerproject.users.MealerUserCook;


public class RegisterCookActivity extends  RegisterActivity {

    byte[] byteImgCheck = null;

    protected void validateUserInput() {
        //super.validateUserInput();

        Button btnRegister = (Button) findViewById(R.id.btnAddCook);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                EditText firstName = findViewById(R.id.teFName);
                EditText lastName = findViewById(R.id.teLName);
                EditText emailField = findViewById(R.id.teEmail);
                EditText password = findViewById(R.id.tePwd);
                EditText retypePassword = findViewById(R.id.tePwd2);
                EditText address = findViewById(R.id.teAddr);
                EditText cookDesc = findViewById(R.id.teDesc);

                String fN = firstName.getText().toString();
                String lN = lastName.getText().toString();
                String eF = emailField.getText().toString();
                String p = password.getText().toString();
                String rP = retypePassword.getText().toString();
                String a = address.getText().toString();
                String cD = cookDesc.getText().toString();
                String desc;
                //MealerUserCook cook = new MealerUserCook(fName, lName, email, pwd, addr);

                EditText teDesc = (EditText) findViewById(R.id.teDesc);
                desc = teDesc.getText().toString();

                validateUserInput();

                if (!isCheckImgLoaded()) {
                    isValid = false;
                }

                if (!isCookDescProvided()) {
                    isValid = false;
                }

                if (isValid) {
                    MealerUserCook cook = new MealerUserCook(fName, lName, email, pwd, addr);
                    Log.d("DB Insert:", "Add to database");

                    cook.setByteImgCheck(byteImgCheck);
                    cook.setCookDescription(desc);
                    cook.setUserType(userType);

                    db.addCook(cook);

                    // After register, redirect user back to login activity
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    view.getContext().startActivity(intent);

                    Toast.makeText(RegisterCookActivity.this, "Account registered. Please login", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(RegisterCookActivity.this, "Check input fields", Toast.LENGTH_LONG).show();
                }

                if (fN.isEmpty()) {
                    firstName.setError("Please enter your first name");
                }

                if (lN.isEmpty()) {
                    lastName.setError("Please enter your last name");
                }

                if (eF.isEmpty() || !eF.contains("@")) {
                    emailField.setError("Please enter a valid email");

                }

                if (p.isEmpty()) {
                    password.setError("Please enter a password");
                }

                if (rP.isEmpty() || rP != p) {
                    retypePassword.setError("Please reenter your password");
                }

                if (a.isEmpty()) {
                    address.setError("Please enter an address");
                }

                if (cD.isEmpty()) {
                    cookDesc.setError("Please add a cook description");
                }

            }
        });


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cook);

        userType = (EnumUserType) getIntent().getExtras().get("LoginType");

        Button btnLoad = (Button) findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

               userType = (EnumUserType) getIntent().getExtras().get("LoginType");

                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                File file = new File(directory, "mario" + ".png");

                ImageView ivCheck = (ImageView) findViewById(R.id.imageView);
                ivCheck.setImageResource(R.drawable.check1);

                Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.check1);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                byteImgCheck = bos.toByteArray();


            }
        });

       validateUserInput();

    }


    private boolean isCheckImgLoaded() {
        ImageView ivCheck = findViewById(R.id.imageView);

        return true;
    }

    private boolean isCookDescProvided() {

        return true;
    }

}
