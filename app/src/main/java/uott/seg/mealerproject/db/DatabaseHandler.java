package uott.seg.mealerproject.db;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import uott.seg.mealerproject.R;
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
    private static final int DATABASE_VERSION = 18;
    private static final String DATABASE_NAME = "mealer";
    private static final String TABLE_USER = "user";
    private static final String TABLE_CREDIT_CARD = "creditCard";
    private static final String TABLE_COOK_DESC = "cookDesc";
    private static final String TABLE_COMPL = "complaint";
    private static final String TABLE_ORDER = "mealOrder";
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
    private static final String RATING = "rating";
    private static final String NUM_RATING = "numRating";

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
    private static final String PICKUP_TIME = "timePickup";

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
        //adminValues.put(EMAIL, "admin@gmail.com");
        adminValues.put(EMAIL, "admin@mealer.com");
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

        // Create order table
        String CREATE_ORDER_TABLE = "CREATE TABLE " + TABLE_ORDER + "("
                + COOK_EMAIL + " TEXT," + CLIENT_EMAIL + " TEXT," + MEAL_NAME + " TEXT," + MEAL_TYPE + " TEXT," + CUISINE_TYPE + " TEXT,"
                + STATUS + " TINYINT," + PICKUP_TIME + " DATE," + INS_DATE + " DATE,"
                + RATING + " REAL," + USER_COMPL + " TEXT" + ")";

        db.execSQL(CREATE_ORDER_TABLE);

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
                + COOK_STATUS + " TINYINT," +  RATING + " REAL," + NUM_RATING + " INT,"
                + INS_DATE + " DATE,"
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
                + ALLERGENS  + " TEXT," + PRICE + " REAL,"
                + INGREDIENTS + " TEXT," + DESC + " TEXT,"
                + "PRIMARY KEY (" + MEAL_NAME +", " + MEAL_TYPE + ", " + CUISINE_TYPE + ", " + COOK_EMAIL + "), "
                + "FOREIGN KEY(" + COOK_EMAIL + ") REFERENCES " + TABLE_USER + "(" + EMAIL + ")"
                + " On UPDATE RESTRICT ON DELETE RESTRICT" + ")";
        db.execSQL(CREATE_MEAL_TABLE);
        // db.close(); // Closing database connection

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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);

        // Create tables again
        onCreate(db);
        // db.close(); // Closing database connection
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

        // db.close(); // Closing database connection
    }

    public void addAdmin(MealerUserAdmin admin) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues userValues = new ContentValues();

        addUser(userValues, admin);
        db.insert(TABLE_USER, null, userValues);
        // db.close(); // Closing database connection
    }

    public void addOrder(String cookEmail, String clientEmail, String mealName, String mealType, String cuisineType, String pickupTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues orderValues = new ContentValues();

        orderValues.put(MEAL_NAME, mealName);
        orderValues.put(MEAL_TYPE, mealType);
        orderValues.put(CUISINE_TYPE, cuisineType);
        orderValues.put(STATUS, EnumOrderStatus.PENDING.getStatusCode());
        orderValues.put(COOK_EMAIL_MEAL, cookEmail);
        orderValues.put(CLIENT_EMAIL, clientEmail);
        orderValues.put(PICKUP_TIME, pickupTime);
        orderValues.put(INS_DATE, getDateTime() );

        db.insert(TABLE_ORDER, null, orderValues);
        // db.close();
    }

    public void addCook(MealerUserCook cook) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues userValues = new ContentValues();
        ContentValues descValues = new ContentValues();

        addUser(userValues, cook);
        addDesc(descValues, cook.getCookDescription(), cook.getByteImgCheck(), cook.getEmail(), cook.getRating(), cook.getNumRating());

        db.insert(TABLE_USER, null, userValues);
        db.insert(TABLE_COOK_DESC, null, descValues);
        // db.close(); // Closing database connection
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
        // db.close(); // Closing database connection
    }

    //AICI
    public void addMyOrderUserComplaints(String complMsg, long RowId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "Update " + TABLE_ORDER
                + " Set " + USER_COMPL + " = " + "'" + complMsg + "'" + " Where RowId = ?";
        Log.d("Complaints SQL", sql);
        db.execSQL(sql, new String[] {String.valueOf(RowId)});
    }
    public String getMyOderUserComplaints(long RowId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String complMsg = "none";
        String sql = "Select complaints from " + TABLE_ORDER  + " WHERE RowId = ?";
        Cursor resultSet = db.rawQuery(sql, new String[]{String.valueOf(RowId)});
        while (resultSet.moveToNext()) {
            complMsg = resultSet.getString(0);
            Log.d("From DB, complaint", "complaints from db");
        }
        return complMsg;
        // db.close(); // Closing database connection

    }

    private void addDesc(ContentValues descValues, String desc, byte[] byteImgCheck, String email) {
        descValues.put(EMAIL, email);
        descValues.put(COOK_DESC, desc);
        descValues.put(VOID_CHK, byteImgCheck);
        descValues.put(INS_DATE, getDateTime() );
        descValues.put(COOK_STATUS, String.valueOf(EnumCookStatus.NORMAL.getStatusCode()));
        descValues.put(RATING, 0);
        descValues.put(NUM_RATING, 0);

    }

    private void addDesc(ContentValues descValues, String desc, byte[] byteImgCheck, String email, float rating, int numRating) {
        descValues.put(EMAIL, email);
        descValues.put(COOK_DESC, desc);
        descValues.put(VOID_CHK, byteImgCheck);
        descValues.put(INS_DATE, getDateTime() );
        descValues.put(COOK_STATUS, String.valueOf(EnumCookStatus.NORMAL.getStatusCode()));
        descValues.put(RATING, rating);
        descValues.put(NUM_RATING, numRating);
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
        // db.close(); // Closing database connection
    }

    public void removeMeal(long rowId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "Delete From " + TABLE_MEAL + " Where RowId = ?";
        db.execSQL(sql, new String[]{String.valueOf(rowId)});
        // db.close(); // Closing database connection
    }

    public EnumMealStatus getMealStatus(String cookEmail, String mealName) {
        EnumMealStatus mealStatus = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "Select Status from " + TABLE_MEAL  + " WHERE cookEmail = ? and mealName = ?";
        Cursor resultSet = db.rawQuery(sql, new String[]{cookEmail, mealName});

        while (resultSet.moveToNext()) {
            mealStatus = EnumMealStatus.getMealStatus((short) resultSet.getInt(0));
        }
        // db.close(); // Closing database connection
        return mealStatus;
    }

    public void setMealStatus(EnumMealStatus status, long rowId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "Update " + TABLE_MEAL + " Set " + STATUS  + " = " + status.getStatusCode() + " Where RowId = ?";
        Log.d("Set meal status =========== ", sql);
        db.execSQL(sql, new String[]{String.valueOf(rowId)});
        // db.close(); // Closing database connection
    }

    public void setOrderStatus(EnumOrderStatus status, long rowId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "Update " + TABLE_ORDER + " Set " + STATUS  + " = " + status.getStatusCode() + " Where RowId = ?";
        Log.d("Set meal status =========== ", sql);
        db.execSQL(sql, new String[]{String.valueOf(rowId)});
        // db.close(); // Closing database connection
    }

    public ArrayList<Meal> getMealList(String cookEmail) {
        // EnumMealStatus mealStatus = null;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Meal> mealList = new ArrayList<>();

        String sql = "Select RowId, mealName, status from " + TABLE_MEAL  + " WHERE cookEmail = ?";
        Cursor resultSet = db.rawQuery(sql, new String[]{cookEmail});

        while (resultSet.moveToNext()) {

            long rowId = resultSet.getLong(0);
            String mealName = resultSet.getString(1);
            String status = resultSet.getString(2);
            EnumMealStatus mealStatus = EnumMealStatus.getMealStatus((short) resultSet.getInt(2));
            Meal m = new Meal(cookEmail,mealName,mealStatus);
            m.setMealID(rowId);
            mealList.add(m);
        }

        // db.close(); // Closing database connection
        return mealList;
    }

    public boolean isUserExist(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "Select * from " + TABLE_USER  + " WHERE EMAIL = ?";
        Cursor resultSet = db.rawQuery(sql, new String[]{email});

        // db.close(); // Closing database connection
        return resultSet.moveToFirst();
    }



    public ArrayList<Meal> getMealSearchResult(String mealName, String mealType, String cuisineType) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] criteria = new String[3];

        ArrayList<Meal> result = new ArrayList<Meal>();
        String sql = "Select meal.RowId, meal.*," + " cook.*," + " desc.* "
                + " From " + TABLE_MEAL + " meal, " + TABLE_USER + " cook, " + TABLE_COOK_DESC + " desc "
                + " Where meal." + COOK_EMAIL + "=cook." + EMAIL
                + " And meal." + COOK_EMAIL + "=desc." + EMAIL
                + " And desc." + COOK_STATUS + "=" + String.valueOf(EnumCookStatus.NORMAL.getStatusCode())
                + " And " + MEAL_NAME + " Like ?"
                + " And " + MEAL_TYPE + " Like ?"
                + " And " + CUISINE_TYPE + " Like ?"
                + " And " + STATUS + " = " + String.valueOf(EnumMealStatus.AVAILABLE.getStatusCode());


        Cursor resultSet = db.rawQuery(sql, new String[] {mealName, mealType, cuisineType});

        Log.d("Meal count ", String.valueOf(resultSet.getCount()));

        while (resultSet.moveToNext()) {
            long rowID = resultSet.getLong(0);
            String mealName1 = resultSet.getString(1);
            String mealType1 = resultSet.getString(2);
            String cuisineType1 = resultSet.getString(3);

            int status = resultSet.getInt(4);
            String cookEmail = resultSet.getString(5);
            String allergens = resultSet.getString(6);

            float price = resultSet.getFloat(7);
            String ingredients = resultSet.getString(8);
            String description = resultSet.getString(9);

            Meal meal = new Meal(mealName1, mealType1, cuisineType1, cookEmail, allergens, price, ingredients, description);
            meal.setMealID(rowID);

            //String cookEmail = resultSet.getString(10);
            //String pwd = resultSet.getString(11)
            String fName = resultSet.getString(12);
            String lName = resultSet.getString(13);
            String addr = resultSet.getString(14);

            //int userType = resultSet.getInt(15);
            //int loginStatus = resultSet.getInt(16);
            //Long insDate = resultSet.getLong(17);

            MealerUserCook cook = new MealerUserCook(fName, lName, cookEmail, addr);
            Log.d("Cook fName", fName);
            Log.d("Cook lName", lName);
            Log.d("Cook addr", addr);

            //String cookEmail = resultSet.getString(18);
            String cookDesc = resultSet.getString(19);
            byte[] voidCheck = resultSet.getBlob(20);
            int cookStatus = resultSet.getInt(21);


            float rating = resultSet.getFloat(22);
            int numRating = resultSet.getInt(23);
            //Date insDate = resultSet.getInt(24);

            Log.d("DB rating", String.valueOf(rating));

            cook.setCookDescription(cookDesc);
            cook.setByteImgCheck(voidCheck);
            cook.setRating(rating);
            cook.setNumRating(numRating);

            Log.d("Cook ", cookDesc);

            meal.setCook(cook);
            result.add(meal);
        }

        // db.close(); // Closing database connection
        return result;
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

        // db.close(); // Closing database connection
        return complaints;
    }


/*
    public ArrayList<MealOrder> getMyOrder(String clientEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MealOrder> orders = new ArrayList<MealOrder>();

        String sql = "Select RowId, cookEmail, clientEmail, mealName, mealType, cuisineType, timePickup from " + TABLE_ORDER  + " WHERE status = ? And clientEmail = ?";
        Cursor resultSet = db.rawQuery(sql, new String[]{String.valueOf(EnumOrderStatus.PENDING.getStatusCode()), clientEmail});

        while (resultSet.moveToNext()) {
            Long rowId = resultSet.getLong(0);
            String cookEmail = resultSet.getString(1);
            //String clientEmail = resultSet.getString(2);

            String mealName = resultSet.getString(3);
            String mealType = resultSet.getString(4);
            String cuisineType = resultSet.getString(5);

            String pickupTime = resultSet.getString(6);

            MealOrder order = new MealOrder(mealName, mealType, cuisineType, cookEmail, clientEmail, pickupTime);
            order.setRowID( rowId);
            orders.add(order);
        }

        return  orders;

    }*/

    public ArrayList<MealOrder> getMyCurrentOrder(String clientEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MealOrder> orders = new ArrayList<MealOrder>();

        //String sql = "Select RowId, cookEmail, clientEmail, mealName, mealType, cuisineType, timePickup, status from " + TABLE_ORDER
        //        + " WHERE clientEmail = ? and status < 100";
        String sql = "Select RowId, cookEmail, clientEmail, mealName, mealType, cuisineType, timePickup, status from " + TABLE_ORDER + " WHERE clientEmail = ?";
        Cursor resultSet = db.rawQuery(sql, new String[]{ clientEmail});

        while (resultSet.moveToNext()) {
            Long rowId = resultSet.getLong(0);
            String cookEmail = resultSet.getString(1);
            //String clientEmail = resultSet.getString(2);

            String mealName = resultSet.getString(3);
            String mealType = resultSet.getString(4);
            String cuisineType = resultSet.getString(5);

            Log.d("My Order Cook Email ========", cookEmail);
            Log.d("My Order MealName ========", mealName);
            Log.d("My Order mealType ========", mealType);
            Log.d("My Order cuisineType  ========", cuisineType);

            String pickupTime = resultSet.getString(6);

            short status = (short)resultSet.getInt(7);
            EnumOrderStatus orderStatus = EnumOrderStatus.getOrderStatus(status);

            MealOrder order = new MealOrder(mealName, mealType, cuisineType, cookEmail, clientEmail, pickupTime);
            order.setRowID( rowId);
            order.setStatus(orderStatus);
            orders.add(order);
        }

        // db.close(); // Closing database connection
        return  orders;
    }
    //AICI
    public EnumOrderStatus getMyOrderStatus(long RowId) {
        EnumOrderStatus myOrderStatus = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "Select Status from " + TABLE_ORDER  + " WHERE RowId = ?";
        Cursor resultSet = db.rawQuery(sql, new String[]{String.valueOf(RowId)});

        while (resultSet.moveToNext()) {
            myOrderStatus = EnumOrderStatus.getOrderStatus((short) resultSet.getInt(0));
        }
        // db.close(); // Closing database connection
        return myOrderStatus;

    }

    public ArrayList<MealOrder> getCurrentOrder(String cookEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MealOrder> orders = new ArrayList<MealOrder>();

        String sql = "Select RowId, cookEmail, clientEmail, mealName, mealType, cuisineType, timePickup from " + TABLE_ORDER  + " WHERE status = ? And cookEmail = ?";
        Cursor resultSet = db.rawQuery(sql, new String[]{String.valueOf(EnumOrderStatus.PENDING.getStatusCode()), cookEmail});

        while (resultSet.moveToNext()) {
            Long rowId = resultSet.getLong(0);
            //String cookEmail = resultSet.getString(1);
            String clientEmail = resultSet.getString(2);

            String mealName = resultSet.getString(3);
            String mealType = resultSet.getString(4);
            String cuisineType = resultSet.getString(5);

            String pickupTime = resultSet.getString(6);

            MealOrder order = new MealOrder(mealName, mealType, cuisineType, cookEmail, clientEmail, pickupTime);
            order.setRowID( rowId);
            orders.add(order);
        }

        // db.close(); // Closing database connection
        return  orders;
    }


    public void setComplaintStatus(EnumComplaintStatus status, long rowId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "Update " + TABLE_COMPL + " Set " + COMPL_STAT  + " = " + status.getStatusCode() + " Where RowId = ?";

        Log.d("Update Complaint =========== ", sql);
        db.execSQL(sql, new String[]{String.valueOf(rowId)});
        // db.close(); // Closing database connection
    }

    //AICI
    public void setMyOrderRating(long RowId,float rating) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "Update " + TABLE_ORDER
                + " Set " + RATING + " = " + String.valueOf(rating) + " Where RowId = ?";
        db.execSQL(sql, new String[] {String.valueOf(RowId)});
    }

    public float getMyOrderRating(long RowId) {
        SQLiteDatabase db = this.getReadableDatabase();
        float rating = 0.0f;

        String sql = "Select rating from " + TABLE_ORDER  + " WHERE RowId = ?";
        Cursor resultSet = db.rawQuery(sql, new String[]{String.valueOf(RowId)});
        while (resultSet.moveToNext()) {
            rating = resultSet.getFloat(0);
        }
        return rating;
    }

    public void setCookRating (String cookEmail, float rating) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "Update " + TABLE_COOK_DESC
                + " Set " + RATING + " = " + String.valueOf(rating) + ", " + NUM_RATING + "=" + NUM_RATING + "+ 1"
                + " Where email = ?";
        db.execSQL(sql, new String[] {cookEmail});
        String sqlOrder = "Update " + TABLE_ORDER
                + " Set " + RATING + " = " + String.valueOf(rating) + ", " + NUM_RATING + "=" + NUM_RATING + "+ 1"
                + " Where email = ?";

        // db.close(); // Closing database connection
    }

    public String getCookNumRating(String cookEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "Select numRating from " + TABLE_COOK_DESC  + " WHERE email = ?";
        Cursor resultSet = db.rawQuery(sql, new String[]{cookEmail});
        return resultSet.toString();
    }

    public MealerUserCook getCookDescRating(String cookEmail) {
        String cookDescription = "";
        String cookAddr = "";
        String fName = "";
        String lName = "";
        float rating = 0.0f;
        int numRating = 0;

        MealerUserCook userCook;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "Select cookDesc, rating, numRating from " + TABLE_COOK_DESC  + " WHERE email = ?";
        Cursor resultSet = db.rawQuery(sql, new String[]{cookEmail});
        while (resultSet.moveToNext()) {
             cookDescription = resultSet.getString(0);
             rating = resultSet.getFloat(1);
             numRating = resultSet.getInt(2);
        }

        sql = "Select fName, lName, address from " + TABLE_USER  + " WHERE email = ?";
        Cursor resultSetAddr = db.rawQuery(sql, new String[]{cookEmail});
        while (resultSetAddr.moveToNext()) {
            cookAddr = resultSetAddr.getString(2);
            fName = resultSetAddr.getString(0);
            lName = resultSetAddr.getString(1);
        }
        userCook = new MealerUserCook(fName, lName, cookEmail, cookAddr);
        userCook.setCookDescription(cookDescription);
        userCook.setRating(rating);
        userCook.setNumRating(numRating);
        return userCook;

    }


    public CookRating getCookRating(String cookEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        CookRating cookRating = null;
        float rating;
        int numOfRating;

        String sql = "Select rating, numRating from " + TABLE_COOK_DESC  + " WHERE email = ?";
        Cursor resultSet = db.rawQuery(sql, new String[]{cookEmail});

        while (resultSet.moveToNext()) {
            rating = resultSet.getFloat(0);
            numOfRating = resultSet.getInt(1);

            cookRating = new CookRating(rating, numOfRating);
        }

        // db.close(); // Closing database connection
        return cookRating;
    }

    public  void setCookStatus (String cookEmail, EnumCookStatus status) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "Update " + TABLE_COOK_DESC + " Set " + COOK_STATUS + " = " + String.valueOf(status.getStatusCode()) + " Where email = ?";
        db.execSQL(sql, new String[] {cookEmail});

        // db.close(); // Closing database connection
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

        // db.close(); // Closing database connection
        return cookStatus;
    }

    public void removeAllComplaints() {

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "Delete From " + TABLE_COMPL;

        db.execSQL(sql);
        // db.close(); // Closing database connection
    }

    public void removeAllCooks() {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "Delete From " + TABLE_COOK_DESC;
        db.execSQL(sql);

        sql = "Delete From " + TABLE_USER + " Where userType = ?" ;
        db.execSQL(sql, new String[]{String.valueOf(EnumUserType.Cook.getUserTypeCode())});

        // db.close(); // Closing database connection
    }

    public void removeAllClients() {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "Delete From " + TABLE_CREDIT_CARD;
        db.execSQL(sql);

        sql = "Delete From " + TABLE_USER + " Where userType = ?";
        db.execSQL(sql, new String[]{String.valueOf(EnumUserType.Client.getUserTypeCode())});

        // db.close(); // Closing database connection
    }

    public void removeAdmin() {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "Delete From " + TABLE_USER + " Where userType = ?";
        db.execSQL(sql,  new String[]{String.valueOf(EnumUserType.Admin.getUserTypeCode())});

        // db.close(); // Closing database connection
    }

    public void removeAllMeal() {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "Delete From " + TABLE_MEAL;
        db.execSQL(sql);

        // db.close(); // Closing database connection
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


        // db.close(); // Closing database connection
        return EnumLoginStatus.SUCCESS;
    }

    private String getDateTime() {

        SimpleDateFormat dateFormat = new SimpleDateFormat(

                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        Date date = new Date();

        return dateFormat.format(date);

    }


}