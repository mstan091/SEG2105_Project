package uott.seg.mealerproject.db;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import uott.seg.mealerproject.enums.*;
import uott.seg.mealerproject.enums.EnumComplaintStatus;
import uott.seg.mealerproject.enums.EnumCookStatus;
import uott.seg.mealerproject.enums.EnumLoginStatus;
import uott.seg.mealerproject.enums.EnumUserType;
import uott.seg.mealerproject.misc.*;
import uott.seg.mealerproject.misc.CreditCardInfo;
import uott.seg.mealerproject.misc.UserComplaint;
import uott.seg.mealerproject.users.MealerUser;
import uott.seg.mealerproject.users.MealerUserAdmin;
import uott.seg.mealerproject.users.MealerUserClient;
import uott.seg.mealerproject.users.MealerUserCook;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 12;
    private static final String DATABASE_NAME = "mealer";
    private static final String TABLE_USER = "user";
    private static final String TABLE_CREDIT_CARD = "creditCard";
    private static final String TABLE_COOK_DESC = "cookDesc";
    private static final String TABLE_COMPL = "complaint";
    //Meal table
    private static final String TABLE_MEAL = "meal";

    //private static final short USERID = 'uID';
    private static final String EMAIL = "email";
    private static final String PWD = "password";
    private static final String USER_TYPE = "userType";
    private static final String LOGIN_STAT = "loginStatus";
    private static final String INS_DATE = "insDate";

    private static final String FNAME = "fName";
    private static final String LNAME = "lName";
    private static final String ADDR = "address";

    private static final String CARD_NUM = "cardNum";
    private static final String HOLDER_NAME = "holderName";
    private static final String SEC_CODE = "secCode";
    private static final String EXP_DATE = "expDate";

    private static final String COOK_DESC = "cookDesc";
    private static final String VOID_CHK = "voidCheck";
    private static final String COOK_STATUS = "cookStatus";

    private static final String COOK_EMAIL = "cookEmail";
    private static final String CLIENT_EMAIL = "clientEmail";
    private static final String USER_COMPL =  "complaints";
    private static final String COMPL_STAT = "complStatus";

    // Meal table columns
    private static final String MEAL_NAME = "mealName";
    private static final String MEAL_TYPE = "mealType";
    private static final String CUISINE_TYPE =  "cuisineType";
    private static final String STATUS = "status";
    private static final String COOK_EMAIL_MEAL = "cookEmail";
    private static final String ALLERGENS = "allergens";
    private static final String PRICE =  "price";
    private static final String INGREDIENTS = "ingredients";
    private static final String DESC = "description";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void createAdmin() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
                + EMAIL + " TEXT PRIMARY KEY," + PWD + " TEXT," + FNAME + " TEXT," + LNAME + " TEXT,"
                + ADDR + " TEXT," + USER_TYPE + " TINYINT," + INS_DATE + " DATE" + ")";

        db.execSQL(CREATE_USER_TABLE);
        // Create Admin account
        ContentValues adminValues = new ContentValues();
        adminValues.put(EMAIL, "admin@gmail.com");
        adminValues.put(PWD, "admin");

        adminValues.put(FNAME, "John");
        adminValues.put(LNAME, "Smith");
        adminValues.put(ADDR, "1200 Albert Street, Ottawa, ON, K1N 3P2");
        adminValues.put(USER_TYPE, "3");
        adminValues.put(INS_DATE, getDateTime() );

        db.insert(TABLE_USER, null, adminValues);
    }

    public void createTestData() {
        SQLiteDatabase db = this.getWritableDatabase();

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON");

        // Create User table
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + EMAIL + " TEXT," + PWD + " TEXT," + FNAME + " TEXT," + LNAME + " TEXT,"
                + ADDR + " TEXT," + USER_TYPE + " TINYINT," + LOGIN_STAT + " TINYINT," + INS_DATE + " DATE,"
                + "PRIMARY KEY (" + EMAIL +", " + USER_TYPE + ")"
                + ")";

        db.execSQL(CREATE_USER_TABLE);

        // Create Credit Card Info table
        String CREATE_CREDIT_CARD_TABLE = "CREATE TABLE " + TABLE_CREDIT_CARD + "("
                + EMAIL + " TEXT," + CARD_NUM + " TEXT," + HOLDER_NAME + " TEXT,"
                + SEC_CODE + " TEXT," + EXP_DATE + " TEXT," + INS_DATE + " DATE,"
                + "FOREIGN KEY(" + EMAIL + ") REFERENCES " + TABLE_USER + "(" + EMAIL + ")"
                + " On UPDATE RESTRICT ON DELETE RESTRICT"
                + ")";

        db.execSQL(CREATE_CREDIT_CARD_TABLE);

        // Create cook table
        String CREATE_COOK_DESC_TABLE = "CREATE TABLE " + TABLE_COOK_DESC + "("
                + EMAIL + " TEXT," + COOK_DESC + " TEXT," + VOID_CHK + " BLOB,"
                + COOK_STATUS + " TINYINT," + INS_DATE + " DATE,"
                + "FOREIGN KEY(" + EMAIL + ") REFERENCES " + TABLE_USER + "(" + EMAIL + ")"
                + " On UPDATE RESTRICT ON DELETE RESTRICT"
                + ")";

        db.execSQL(CREATE_COOK_DESC_TABLE);

        // Create compliant table
        String CREATE_COMPL_TABLE = "CREATE TABLE " + TABLE_COMPL + "("
            + COOK_EMAIL + " TEXT," + CLIENT_EMAIL + " TEXT," + USER_COMPL + " TEXT,"
            + COMPL_STAT + " TINYINT," + INS_DATE + " DATE,"
            + "FOREIGN KEY(" + COOK_EMAIL + ") REFERENCES " + TABLE_USER + "(" + EMAIL + ")"
            + " On UPDATE RESTRICT ON DELETE RESTRICT" + ")";

        db.execSQL(CREATE_COMPL_TABLE);

        // Create meal table
        String CREATE_MEAL_TABLE = "CREATE TABLE " + TABLE_MEAL + "("
                + MEAL_NAME + " TEXT," + MEAL_TYPE + " TEXT," + CUISINE_TYPE + " TEXT,"
                + STATUS + " TINYINT," + COOK_EMAIL_MEAL + " TEXT,"
                + ALLERGENS  + " TEXT," + PRICE + " TINYINT,"
                + INGREDIENTS + " TEXT," + DESC + " TEXT,"
                + "FOREIGN KEY(" + COOK_EMAIL_MEAL + ") REFERENCES " + TABLE_USER + "(" + EMAIL + ")"
                + " On UPDATE RESTRICT ON DELETE RESTRICT" + ")";
        db.execSQL(CREATE_MEAL_TABLE);


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("PRAGMA foreign_keys = ON");

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREDIT_CARD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COOK_DESC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEAL);

        // Create tables again
        onCreate(db);
    }

    // Add a client account
    public void addClient(MealerUserClient client) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues userValues = new ContentValues();
        ContentValues cardValues = new ContentValues();

        addUser(userValues, client);
        addCard(cardValues, client.getCardInfo(), client.getEmail());

        db.insert(TABLE_USER, null, userValues);
        db.insert(TABLE_CREDIT_CARD, null, cardValues);

        db.close(); // Closing database connection
    }

    public void addAdmin(MealerUserAdmin admin) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues userValues = new ContentValues();

        addUser(userValues, admin);
        db.insert(TABLE_USER, null, userValues);

    }

    public void addCook(MealerUserCook cook) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues userValues = new ContentValues();
        ContentValues descValues = new ContentValues();

        addUser(userValues, cook);
        addDesc(descValues, cook.getCookDescription(), cook.getByteImgCheck(), cook.getEmail());

        db.insert(TABLE_USER, null, userValues);
        db.insert(TABLE_COOK_DESC, null, descValues);

    }

    public  void addUserComplaints(UserComplaint compl) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues complValues = new ContentValues();

        complValues.put(COOK_EMAIL, compl.getCookEmail());
        complValues.put(CLIENT_EMAIL, compl.getClientEmail());
        complValues.put(USER_COMPL, compl.getUserCompl());
        complValues.put(COMPL_STAT, EnumComplaintStatus.NEW.getStatusCode());
        complValues.put(INS_DATE, getDateTime() );

        db.insert(TABLE_COMPL, null, complValues);
    }

    private void addDesc(ContentValues descValues, String desc, byte[] byteImgCheck, String email) {
        descValues.put(EMAIL, email);
        descValues.put(COOK_DESC, desc);
        descValues.put(VOID_CHK, byteImgCheck);
        descValues.put(INS_DATE, getDateTime() );
        descValues.put(COOK_STATUS, String.valueOf(EnumCookStatus.NORMAL.getStatusCode()));
    }

    private void addCard(ContentValues cardValues, CreditCardInfo cardInfo, String email) {

        cardValues.put(EMAIL, email);
        cardValues.put(CARD_NUM, cardInfo.getCardNum());
        cardValues.put(HOLDER_NAME, cardInfo.getHolderName());
        cardValues.put(EXP_DATE, cardInfo.getExpireDate());
        cardValues.put(SEC_CODE, cardInfo.getSecCode());
        cardValues.put(INS_DATE, getDateTime() );

    }

    private void addUser(ContentValues userValues, MealerUser user) {

        userValues.put(EMAIL, user.getEmail());
        userValues.put(PWD, user.getPwd());

        userValues.put(FNAME, user.getfName());
        userValues.put(LNAME, user.getlName());
        userValues.put(ADDR, user.getAddr());
        userValues.put(USER_TYPE, user.getUserType().getUserTypeCode());
        userValues.put(INS_DATE, getDateTime() );

        userValues.put(LOGIN_STAT, String.valueOf(EnumLoginStatus.NOT_LONGIN.getStatusCode()));
    }

    public  void addMeal(Meal meal) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues mealValues = new ContentValues();

        mealValues.put(MEAL_NAME, meal.getMealName());
        mealValues.put(MEAL_TYPE, meal.getMealType());
        mealValues.put(CUISINE_TYPE, meal.getCuisineType() );
        mealValues.put(STATUS, meal.getMealStatus().getStatusCode());
        mealValues.put(COOK_EMAIL_MEAL, meal.getCookEmail());
        mealValues.put(ALLERGENS, meal.getAllergens() );
        mealValues.put(INGREDIENTS, meal.getIngredients() );
        mealValues.put(PRICE, meal.getPrice());
        mealValues.put(DESC, meal.getDesc() );
        db.insert(TABLE_MEAL, null, mealValues);
    }

    public void removeMeal(long rowId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "Delete From " + TABLE_MEAL + " Where RowId = ?";
        db.execSQL(sql, new String[]{String.valueOf(rowId)});
    }

    public EnumMealStatus getMealStatus(String cookEmail, String mealName) {
        EnumMealStatus mealStatus = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "Select Status from " + TABLE_MEAL  + " WHERE cookEmail = ? and mealName = ?";
        Cursor resultSet = db.rawQuery(sql, new String[]{cookEmail, mealName});

        while (resultSet.moveToNext()) {
            mealStatus = EnumMealStatus.getMealStatus((short) resultSet.getInt(0));
        }
        return mealStatus;
    }

    public void setMealStatus(EnumMealStatus status, long rowId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "Update " + TABLE_MEAL + " Set " + STATUS  + " = " + status.getStatusCode() + " Where RowId = ?";
        Log.d("Set meal status =========== ", sql);
        db.execSQL(sql, new String[]{String.valueOf(rowId)});
    }

    public ArrayList<Meal> getMealList(String cookEmail) {
        // EnumMealStatus mealStatus = null;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Meal> mealList = new ArrayList<>();
        //Aici
        String sql = "Select RowId, mealName, status from " + TABLE_MEAL  + " WHERE cookEmail = ?";
        Cursor resultSet = db.rawQuery(sql, new String[]{cookEmail});

        while (resultSet.moveToNext()) {
            //Aici
            long rowId = resultSet.getLong(0);
            String mealName = resultSet.getString(1);
            String status = resultSet.getString(2);
            EnumMealStatus mealStatus = EnumMealStatus.getMealStatus((short) resultSet.getInt(2));
            Meal m = new Meal(cookEmail,mealName,mealStatus);
            m.setMealID(rowId);
            mealList.add(m);
        }
        return mealList;
    }

    public boolean isUserExist(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "Select * from " + TABLE_USER  + " WHERE EMAIL = ?";
        Cursor resultSet = db.rawQuery(sql, new String[]{email});

        return resultSet.moveToFirst();
    }

    public ArrayList<UserComplaint> getUserComplaint(EnumComplaintStatus status) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<UserComplaint> complaints = new ArrayList<UserComplaint>();

        String sql = "Select RowId, cookEmail, clientEmail, complaints from " + TABLE_COMPL  + " WHERE complStatus = ?";
        Cursor resultSet = db.rawQuery(sql, new String[]{String.valueOf(EnumComplaintStatus.NEW.getStatusCode())});

        while (resultSet.moveToNext()) {
            long rowId = resultSet.getLong(0);
            String cookEmail = resultSet.getString(1);
            String clientEmail = resultSet.getString(2);
            String strCompl = resultSet.getString(3);

            UserComplaint complaint = new UserComplaint(cookEmail, clientEmail, strCompl);
            complaint.setComplId(rowId);
            complaints.add(complaint);
        }

        return complaints;
    }

    public void setComplaintStatus(EnumComplaintStatus status, long rowId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "Update " + TABLE_COMPL + " Set " + COMPL_STAT  + " = " + status.getStatusCode() + " Where RowId = ?";

        Log.d("Update Complaint =========== ", sql);
        db.execSQL(sql, new String[]{String.valueOf(rowId)});
    }

    public  void setCookStatus (String cookEmail, EnumCookStatus status) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "Update " + TABLE_COOK_DESC + " Set " + COOK_STATUS + " = " + String.valueOf(status.getStatusCode()) + " Where email = ?";
        db.execSQL(sql, new String[] {cookEmail});
    }

    public EnumCookStatus getCookStatus(String cookEmail) {
        EnumCookStatus cookStatus = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "Select cookStatus from " + TABLE_COOK_DESC  + " WHERE email = ?";
        Log.d("Cook status", sql);
        Cursor resultSet = db.rawQuery(sql, new String[]{cookEmail});

        while (resultSet.moveToNext()) {

            cookStatus = EnumCookStatus.getCookStatus((short) resultSet.getInt(0));

        }

        return cookStatus;
    }

    public void removeAllComplaints() {

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "Delete From " + TABLE_COMPL;

        db.execSQL(sql);
    }

    public void removeAllCooks() {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "Delete From " + TABLE_COOK_DESC;
        db.execSQL(sql);

        sql = "Delete From " + TABLE_USER + " Where userType = ?" ;
        db.execSQL(sql, new String[]{String.valueOf(EnumUserType.Cook.getUserTypeCode())});

    }

    public void removeAllClients() {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "Delete From " + TABLE_CREDIT_CARD;
        db.execSQL(sql);

        sql = "Delete From " + TABLE_USER + " Where userType = ?";
        db.execSQL(sql, new String[]{String.valueOf(EnumUserType.Client.getUserTypeCode())});

    }

    public void removeAdmin() {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "Delete From " + TABLE_USER + " Where userType = ?";
        db.execSQL(sql,  new String[]{String.valueOf(EnumUserType.Admin.getUserTypeCode())});
    }

    public void removeAllMeal() {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "Delete From " + TABLE_MEAL;
        db.execSQL(sql);
    }

    public EnumLoginStatus validateLogin(String email, String pwd, EnumUserType userType) {
        MealerUser user = null;

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "Select * from " + TABLE_USER  + " WHERE EMAIL = ?";
        Cursor resultSet = db.rawQuery(sql, new String[]{email});

        if (resultSet != null) {
            if (resultSet.moveToFirst()) {

                String loginEmail = resultSet.getString(0);
                String loginPwd = resultSet.getString(1);
                String fName = resultSet.getString(2);
                String lName = resultSet.getString(3);
                String addr = resultSet.getString(4);
                int loginType = resultSet.getInt(5);

                Log.d("login email = ", loginEmail);
                if (!loginPwd.equals(pwd)) {
                    return EnumLoginStatus.INVALID_PWD;
                }

                if (loginType != userType.getUserTypeCode()) {
                    Log.d("userType", "user type wrong");
                    return EnumLoginStatus.INVALID_USER_TYPE;
                }
            } else {
                Log.d("user:", "user not found");
                return EnumLoginStatus.USER_NOT_FOUND;
            }
        }

        return EnumLoginStatus.SUCCESS;
    }

    private String getDateTime() {

        SimpleDateFormat dateFormat = new SimpleDateFormat(

                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        Date date = new Date();

        return dateFormat.format(date);

    }


}