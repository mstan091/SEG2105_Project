package uott.seg.mealerproject.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uott.seg.mealerproject.R;
import uott.seg.mealerproject.db.DatabaseHandler;
import uott.seg.mealerproject.enums.EnumUserType;


public class RegisterActivity extends AppCompatActivity {
    protected static final int MIN_LEN_NAME = 1;
    protected static final int MAX_LEN_NAME = 30;
    protected static final int MIN_LEN_EMAIL = 1;
    protected static final int MAX_LEN_EMAIL = 30;
    protected static final int MIN_LEN_ADDR =4;
    protected static final int MAX_LEN_ADDR = 128;
    protected static final int MIN_LEN_PWD = 3;
    protected static final int MAX_LEN_PWD = 16;

    boolean isValid;
    protected EnumUserType userType;
    protected EditText teEmail, tePwd, tePwd2, teFName, teLName, teAddr;
    protected String email, pwd, pwd2, fName, lName, addr;

    protected DatabaseHandler db = new DatabaseHandler(this);

    protected void validateUserInput() {

        isValid = true;

        EditText teEmail = findViewById(R.id.teEmail);
        EditText tePwd = findViewById(R.id.tePwd);
        EditText tePwd2 = findViewById(R.id.tePwd2);

        EditText teFName = findViewById(R.id.teFName);
        EditText teLName = findViewById(R.id.teLName);
        EditText teAddr = findViewById(R.id.teAddr);

        email = teEmail.getText().toString();
        pwd = tePwd.getText().toString();
        pwd2 = tePwd2.getText().toString();

        fName = teFName.getText().toString();
        lName = teLName.getText().toString();
        addr = teAddr.getText().toString();

        if (!validateEmai(email)) {
            teEmail.setError("Email format wrong");
            isValid = false;
        }

        if (fName.length() < MIN_LEN_NAME || fName.length() > MAX_LEN_NAME) {
            teFName.setError("Name is too short or too long");
            isValid = false;
        }

        if (lName.length() < MIN_LEN_NAME || fName.length() > MAX_LEN_NAME) {
            teLName.setError("Name is too short too long");
            isValid = false;
        }

        if (pwd.length() < MIN_LEN_PWD || pwd.length() > MAX_LEN_PWD) {
            tePwd.setError("Password is too short");
            isValid = false;
        }

        if (!pwd.equals(pwd2)) {
            tePwd2.setError("Passwords mismatch");
            isValid = false;
        }

        if (addr.length() < MIN_LEN_ADDR || addr.length() > MAX_LEN_ADDR) {
            teAddr.setError("Address is too short");
            isValid = false;
        }


    }

    private boolean validateEmai(String email) {
        String expression = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }


}
