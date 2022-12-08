package uott.seg.mealerproject.activities;

import static android.view.View.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import uott.seg.mealerproject.enums.EnumCookStatus;
import uott.seg.mealerproject.enums.EnumLoginStatus;
import uott.seg.mealerproject.enums.EnumUserType;
import uott.seg.mealerproject.misc.CreditCardInfo;
import uott.seg.mealerproject.misc.UserComplaint;
import uott.seg.mealerproject.misc.Meal;
import uott.seg.mealerproject.users.MealerUserAdmin;
import uott.seg.mealerproject.users.MealerUserClient;
import uott.seg.mealerproject.users.MealerUserCook;



public class MainActivity extends AppCompatActivity implements OnClickListener {

    String loginID;
    EnumUserType userType = EnumUserType.Client;

    RadioGroup rgLoginType;
    RadioButton rbClient, rbCook, rbAdmin;
    EditText edLoginID;

    // CREATE TABLES IN THE DATABASE
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
        //db.createAdmin();
        //db.createTestData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Insert user complaint into DB
        Log.d("Database", "Load DB for testing");
        loadDBForTesting();

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
                    Intent intent;

                    if (userType == EnumUserType.Admin) {
                        intent = new Intent(view.getContext(), WelcomeAdminActivity.class);
                    } else if (userType == EnumUserType.Client)   {
                        intent = new Intent(view.getContext(), WelcomeClientActivity.class);
                    } else if (userType == EnumUserType.Cook) {
                        intent = new Intent(view.getContext(), WelcomeCookActivity.class);
                    } else {
                        return;
                    }

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

    private void insertUserComplaint() {
        UserComplaint compl1 = new UserComplaint("cook1@mealer.com", "client1@mealer.com", "not a very good taste");
        db.addUserComplaints(compl1);

        UserComplaint compl2 = new UserComplaint("cook2@mealer.com", "client1@mealer.com","not ready on time");
        db.addUserComplaints((compl2));

        UserComplaint compl3 = new UserComplaint("cook2@mealer.com", "client2@mealer.com","bad food");
        db.addUserComplaints((compl3));
    }

    private void insertCook() {
        MealerUserCook cook1 = new MealerUserCook("Cook1", "Mealer", "cook1@mealer.com", "cook1", "1 cook street mealer city");
        cook1.setCookDescription("Cook1 at mealer city and cooks meal");
        cook1.setLoginStatus(EnumLoginStatus.NOT_LONGIN);
        cook1.setRating(4.6f);
        cook1.setNumRating(8);


        MealerUserCook cook2 = new MealerUserCook("Cook2", "Mealer", "cook2@mealer.com", "cook2", "2 cook street mealer city");
        cook2.setCookDescription("Cook2 at mealer city and cooks meal");
        cook2.setLoginStatus(EnumLoginStatus.NOT_LONGIN);
        cook2.setRating(2.8f);
        cook2.setNumRating(18);

        MealerUserCook cook3 = new MealerUserCook("Cook3", "Mealer", "cook3@mealer.com", "cook3", "3 cook street mealer city");
        cook3.setCookDescription("Cook3 at mealer city and cooks meal");
        cook3.setLoginStatus(EnumLoginStatus.NOT_LONGIN);

        db.addCook(cook1);
        db.addCook(cook2);
        db.addCook(cook3);
        //db.setCookStatus(cook3.getEmail(), EnumCookStatus.SUSPENDED);
    }

    private void addClientCardInfo (MealerUserClient client) {
        CreditCardInfo cardInfo = new CreditCardInfo(client.getfName() , "1111 0000", "01/22", "001");
    }

    private void insertClient() {
        MealerUserClient client1 = new MealerUserClient("Client1", "Mealer", "client1@mealer.com", "client1", "1 client street mealer city");
        client1.setCardInfo(new CreditCardInfo("Client1" , "1111 0000", "01/22", "001"));

        MealerUserClient client2 = new MealerUserClient("Client2", "Mealer", "client2@mealer.com", "client2", "2 client street mealer city");
        client2.setCardInfo(new CreditCardInfo("Client2" , "2222 0000", "02/22", "002"));

        db.addClient(client1);
        db.addClient(client2);

    }

    private void insertAdmin() {
        MealerUserAdmin adminUser = new MealerUserAdmin("Admin", "Mealer", "admin@gmail.com", "admin", "1 admin street mealer city");
        db.addAdmin(adminUser);
    }

    private void insertMeal(){
        Meal meal1 = new Meal("Meal1", "Main", "Indian", "cook1@mealer.com", "allergens", 10.0f, "tofu,onion,pepper","desc 1" );
        db.addMeal(meal1);

        Meal meal2 = new Meal("Meal2", "Apetizer", "Meditranean", "cook2@mealer.com", "allergens", 5.5f, "olive,tomatoes,pepper","desc 2" );
        db.addMeal(meal2);

        Meal meal3 = new Meal("Meal3", "Desert", "Conteporan", "cook3@mealer.com", "allergens", 6.0f, "strawberries,sugar,butter","desc 3" );
        db.addMeal(meal3);

        Meal meal4 = new Meal("Meal2", "Main", "Modern", "cook3@mealer.com", "allergens", 6.0f, "rice, fish, oil","desc 2" );
        db.addMeal(meal4);
    }


    private void loadDBForTesting() {
        // MS
        Intent intent = getIntent();
        boolean fromLogout = intent.getBooleanExtra("fromLogout", false);
        Log.e("the value passed", String.valueOf(fromLogout));
        // end MS
        if (!fromLogout) {
            db.removeAdmin();
            db.removeAllComplaints();
            db.removeAllCooks();
            db.removeAllClients();
            Log.d("removeallmeal", "Remove meal");
            db.removeAllMeal();
            insertAdmin();
            insertCook();
            insertClient();
            insertUserComplaint();
            insertMeal();
            Log.d("insertmeal","Inser MEal");
        }
    }

}