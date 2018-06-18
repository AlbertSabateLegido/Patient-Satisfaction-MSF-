package frontieres.sans.medecins.patientsatisfaction.Backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database_Numbers extends SQLiteOpenHelper {

    public static final String DATABASE = "generalsurvey.db" ;
    public static final int DATABASE_VERSION = 3;

    public static final String TABLE_NAME = "Surveys" ;
    public static final String TOTAL_SURVEYS = "total" ;
    public static final String SURVEYS_COMPLETED = "completed" ;
    public static final String AVG_TIME = "avgTime" ;
    public static final String CREATE_SENTIX = "CREATE TABLE " + TABLE_NAME + "(" +
            TOTAL_SURVEYS + " INTEGER ," +
            SURVEYS_COMPLETED + " INTEGER , " +
            AVG_TIME + "REAL )" ;

    private static final String TABLE_USER_INFORMATION = "User_information";
    private static final String SERVER_URL = "serverURL";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ID = "id";
    private static final String ORGANISATION_UNIT = "organisationUnit";
    private static final String CREATE_TABLE_USER_INFORMATION = "CREATE TABLE " + TABLE_USER_INFORMATION + "(" +
            SERVER_URL          + " TEXT," +
            USERNAME            + " TEXT," +
            PASSWORD            + " TEXT," +
            ID                  + " TEXT," +
            ORGANISATION_UNIT   + " TEXT)";

    public int survey_total ;
    public int survey_complited ;
    public double avg_time ;


    public Database_Numbers(Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
        /*
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        int count = cursor.getCount();
        if (cursor.moveToNext() == true) {
            ContentValues cn = new ContentValues() ;
            cn.put(TOTAL_SURVEYS, 0);
            cn.put(SURVEYS_COMPLETED, 0) ;
          //  cn.put(AVETIME,0.0) ;
        }
       else {
            survey_total = num_patient() ;
         //   avg_time = TimeAvg() ;
            survey_complited = NumFurmComplete() ;

        }
        */
    }
    @Override

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SENTIX);
        db.execSQL(CREATE_TABLE_USER_INFORMATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void storeUserInformation(String serverURL,String username,String password) {
        SQLiteDatabase readableDatabase = getReadableDatabase();

        Cursor cursor = readableDatabase.query(TABLE_USER_INFORMATION,null,null,
                null,null,null,null);

        SQLiteDatabase writableDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SERVER_URL,serverURL);
        contentValues.put(USERNAME,username);
        contentValues.put(PASSWORD,password);

        if(cursor.getCount() == 0) {
            System.out.println("INSERT USER INFORMATION");
            writableDatabase.insert(TABLE_USER_INFORMATION,null,contentValues);
        }
        if(cursor.getCount() == 1) {
            System.out.println("UPDATE USER INFORMATION");
            writableDatabase.update(TABLE_USER_INFORMATION,contentValues,null,null);
        }
    }

    public void storeOrganisationUnit(String organisationUnit) {
        SQLiteDatabase writableDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ORGANISATION_UNIT,organisationUnit);

        writableDatabase.update(TABLE_USER_INFORMATION,contentValues,null,null);
    }


    public void storeId(String id) {
        SQLiteDatabase writableDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,id);

        writableDatabase.update(TABLE_USER_INFORMATION,contentValues,null,null);
    }

    public List<String> getUserInformation() {
        SQLiteDatabase readableDatabase = getReadableDatabase();

        String[] columns = new String[] {
                SERVER_URL,
                USERNAME,
                PASSWORD,
                ID,
                ORGANISATION_UNIT
        };

        Cursor cursor = readableDatabase.query(TABLE_USER_INFORMATION,columns,null,
                null,null,null,null);

        if(cursor.moveToFirst()) {
            List<String> userInformation = new ArrayList<>();
            userInformation.add(cursor.getString(0));
            userInformation.add(cursor.getString(1));
            userInformation.add(cursor.getString(2));
            userInformation.add(cursor.getString(3));
            userInformation.add(cursor.getString(4));
            return userInformation;
        }

        return null;
    }

    public int num_patient () {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery =  "SELECT " + TOTAL_SURVEYS + " from " + TABLE_NAME ;
        Cursor cursor = db.rawQuery(selectQuery , null) ;
        if ( cursor.moveToFirst() == false) return 0;
        return cursor.getInt(cursor.getColumnIndex(TOTAL_SURVEYS)) ;
    }


    public int NumFurmComplete() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery =  "SELECT  " + SURVEYS_COMPLETED + " from " + TABLE_NAME ;
        Cursor cursor = db.rawQuery(selectQuery , null) ;
        if ( cursor.moveToFirst() == false) return 0;
        return cursor.getInt(cursor.getColumnIndex(SURVEYS_COMPLETED)) ;
    }

    public Double TimeAvg() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery =  "SELECT  " + AVG_TIME + " from " + TABLE_NAME ;
        Cursor cursor = db.rawQuery(selectQuery , null) ;
        if ( cursor.moveToFirst() == false) return 0.0;
        return cursor.getDouble(cursor.getColumnIndex(AVG_TIME)) ;
    }

    public void SurveyCompleted (double time) {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
       // db.execSQL(CREATE_SENTIX);
        ContentValues nums = new ContentValues() ;
       survey_complited = survey_complited + 1 ;
        nums.put(TOTAL_SURVEYS,survey_total);
    // nums.put(AVETIME ,time);
        nums.put(SURVEYS_COMPLETED,survey_complited);
        db.update(TABLE_NAME , nums , TOTAL_SURVEYS + " = "+survey_total, null) ;
    }

    public void SurveyStarted () {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues nums = new ContentValues() ;
        survey_total = survey_total + 1 ;
        nums.put(TOTAL_SURVEYS,survey_total);
        db.update(TABLE_NAME , nums , AVG_TIME + " = "+avg_time, null) ;
    }
}
