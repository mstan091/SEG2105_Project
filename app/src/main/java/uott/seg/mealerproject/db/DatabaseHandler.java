package uott.seg.mealerproject.db;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import uott.seg.mealerproject.enums.EnumLoginStatus;
import uott.seg.mealerproject.enums.EnumUserType;
import uott.seg.mealerproject.misc.CreditCardInfo;
import uott.seg.mealerproject.users.MealerUser;
import uott.seg.mealerproject.users.MealerUserClinet;
import uott.seg.mealerproject.users.MealerUserCook;
import uott.seg.mealerproject.users.MealerUserCook;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "mealer";
    private static final String TABLE_USER = "user";
    private static final String TABLE_CREDIT_CARD = "creditCard";
    private static final String TABLE_COOK_DESC = "cookDesc";

    //private static final short USERID = 'uID';
    private static final String EMAIL = "email";
    private static final String PWD = "password";
    private static final String USER_TYPE = "userType";
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

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + EMAIL + " TEXT PRIMARY KEY," + PWD + " TEXT," + FNAME + " TEXT," + LNAME + " TEXT,"
                + ADDR + " TEXT," + USER_TYPE + " TINYINT," + INS_DATE + " DATE" + ")";

        db.execSQL(CREATE_USER_TABLE);


        String CREATE_CREDIT_CARD_TABLE = "CREATE TABLE " + TABLE_CREDIT_CARD + "("
                + EMAIL + " TEXT," + CARD_NUM + " TEXT," + HOLDER_NAME + " TEXT,"
                + SEC_CODE + " TEXT," + EXP_DATE + " TEXT," + INS_DATE + " DATE,"
                + "FOREIGN KEY(" + EMAIL + ") REFERENCES " + TABLE_USER + "(" + EMAIL + ")" + ")";

        db.execSQL(CREATE_CREDIT_CARD_TABLE);

        String CREATE_COOK_DESC_TABLE = "CREATE TABLE " + TABLE_COOK_DESC + "("
                + EMAIL + " TEXT," + COOK_DESC + " TEXT," + VOID_CHK + " BLOB," + INS_DATE + " DATE,"
                + "FOREIGN KEY(" + EMAIL + ") REFERENCES " + TABLE_USER + "(" + EMAIL + ")" + ")";

        db.execSQL(CREATE_COOK_DESC_TABLE);


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREDIT_CARD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COOK_DESC);

        // Create tables again
        onCreate(db);
    }

    // Add a client account
    public void addClient(MealerUserClinet client) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues userValues = new ContentValues();
        ContentValues cardValues = new ContentValues();

        addUser(userValues, client);
        addCard(cardValues, client.getCardInfo(), client.getEmail());

        db.insert(TABLE_USER, null, userValues);
        db.insert(TABLE_CREDIT_CARD, null, cardValues);

        db.close(); // Closing database connection
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

    private void addDesc(ContentValues descValues, String desc, byte[] byteImgCheck, String email) {
        descValues.put(EMAIL, email);
        descValues.put(COOK_DESC, desc);
        descValues.put(VOID_CHK, byteImgCheck);
        descValues.put(INS_DATE, getDateTime() );

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