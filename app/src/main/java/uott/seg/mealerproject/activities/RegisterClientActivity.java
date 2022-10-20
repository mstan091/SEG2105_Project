package uott.seg.mealerproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uott.seg.mealerproject.R;
import uott.seg.mealerproject.enums.EnumUserType;
import uott.seg.mealerproject.misc.CreditCardInfo;
import uott.seg.mealerproject.users.MealerUserClinet;


public class RegisterClientActivity extends  RegisterActivity {



    private static final int SEC_LEN = 3;

    private static final int MIN_LEN_CARD_NUM = 4;
    private static final int MAX_LEN_CARD_NUM = 16;

    @Override
    protected void validateUserInput() {

        setContentView(R.layout.activity_register_client);
        userType = (EnumUserType) getIntent().getExtras().get("LoginType");

        //DatabaseHandler db = new DatabaseHandler(this);


        Button btnRegister = (Button) findViewById(R.id.btnAddClient);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CreditCardInfo cardInfo = validateCardInfo();
                String holderName, cardNum, expireDate, secCode;

                EditText firstName = findViewById(R.id.teFName);
                EditText lastName = findViewById(R.id.teLName);
                EditText emailField = findViewById(R.id.teEmail);
                EditText password = findViewById(R.id.tePwd);
                EditText retypePassword = findViewById(R.id.tePwd2);
                EditText address = findViewById(R.id.teAddr);

                EditText teHolderName = findViewById(R.id.teHolderName);
                EditText teCardNum = findViewById(R.id.teCardNum);
                EditText teExpireDate = findViewById(R.id.teExpireDate);
                EditText teSecCode = findViewById(R.id.teSecCod);

                holderName = teHolderName.getText().toString();
                cardNum = teCardNum.getText().toString();
                expireDate = teExpireDate.getText().toString();
                secCode = teSecCode.getText().toString();

                String fN = firstName.getText().toString();
                String lN = lastName.getText().toString();
                String eF = emailField.getText().toString();
                String p = password.getText().toString();
                String rP = retypePassword.getText().toString();
                String a = address.getText().toString();


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

                if (secCode.length() != SEC_LEN) {
                    teSecCode.setError("Please enter a valid security code");
                }


                if (holderName.isEmpty() || holderName.length() < MIN_LEN_NAME || holderName.length() > MAX_LEN_NAME) {
                    teHolderName.setError("Name is to short or too long");

                }

                Log.d("1", "validate4d holder name");


                if (cardNum.isEmpty() || cardNum.length() < MIN_LEN_CARD_NUM || cardNum.length() > MAX_LEN_CARD_NUM) {
                    teCardNum.setError("Card num is to short or too long");

                }

                Log.d("2", "validated card number");

                if (expireDate.isEmpty()) {
                    teExpireDate.setError("Expiry date is not valid or has passed.");

                }

                Log.d("3", "validated expiration date");

                if (isUserExist(email)) {
                    teEmail.setError("User account exist");
                }


                if (isUserExist(email)) {
                    teEmail.setError("User account exist");
                    return;
                }

                if (isValid) {
                    MealerUserClinet client = new MealerUserClinet(fName, lName, email, pwd, addr);
                    client.setUserType(userType);
                    client.setCardInfo(cardInfo);

                    Log.d("DB Insert:", "Add to database");

                    db.addClient(client);

                    // After register, redirect user back to login activity
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    view.getContext().startActivity(intent);

                    Toast.makeText(RegisterClientActivity.this, "Account registered. Please login", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(RegisterClientActivity.this, "Check input fields", Toast.LENGTH_LONG).show();
                }



            }
                //super.validateUserInput();




});

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);
        userType = (EnumUserType) getIntent().getExtras().get("LoginType");
        validateUserInput();
        //DatabaseHandler db = new DatabaseHandler(this);

    }

    private boolean isCardExpired(String expireDate) {
        // ToDo validation here

        return false;
    }



    private boolean isUserExist(String email) {
        // ToDo

        return false;
    }

    private CreditCardInfo validateCardInfo() {
        String holderName, cardNum, expireDate, secCode;
        CreditCardInfo cardInfo;

        EditText teHolderName = findViewById(R.id.teHolderName);
        EditText teCardNum = findViewById(R.id.teCardNum);
        EditText teExpireDate = findViewById(R.id.teExpireDate);
        EditText teSecCode = findViewById(R.id.teSecCod);

        holderName = teHolderName.getText().toString();
        cardNum = teCardNum.getText().toString();
        expireDate = teExpireDate.getText().toString();
        secCode = teSecCode.getText().toString();

        cardInfo = new CreditCardInfo(holderName, cardNum, expireDate, secCode);
        if (cardInfo.getHolderName().length() < MIN_LEN_NAME || cardInfo.getHolderName().length() > MAX_LEN_NAME) {
            teHolderName.setError("Name is to short or too long");
            isValid = false;
        }

        Log.d("1", "validate4d holder name");

        //Toast.makeText(RegisterClientActivity.this, "name len " + holderName.length(), Toast.LENGTH_LONG).show();
        //Toast.makeText(RegisterClientActivity.this, "num len " + cardNum.length(), Toast.LENGTH_LONG).show();

        if (cardNum.length() < MIN_LEN_CARD_NUM || cardNum.length() > MAX_LEN_CARD_NUM) {
            teCardNum.setError("Card num is to short or too long");
            isValid = false;
        }

        Log.d("2", "validated card number");

        if (isCardExpired(expireDate)) {
            teExpireDate.setError("Expire data is not valid");
            isValid = false;
        }

        Log.d("3", "validated expiration date");

        return cardInfo;
    }


}