package frontieres.sans.medecins.patientsatisfaction.Backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Home on 18/05/2018.
 */
/*  db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(CREATE_SENTIX); */

public class Database_Numbers extends SQLiteOpenHelper {

    public static final String DATABASE = "generalsurvey.db" ;
    public static final String TABLE_NAME = "PATIENTS" ;
    public static final String TOTAL_PATIENTS = "NUM_PATIENTS_TOT" ;
    public static final String PATIENTS_COMPLETED = "NUM_PATIENT_COMPLETED" ;
    public  static final String AVETIME = "AVG_TIME" ;
    public static  final String CREATE_SENTIX = "CREATE TABLE " +
            TABLE_NAME + "(" + TOTAL_PATIENTS + " INTEGER ,"+ PATIENTS_COMPLETED +"   INTEGER , "  + AVETIME + "REAL )"  ;



  /*  public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            SURVEY_ID   + " INTEGER," +
            QUESTION    + " TEXT," +
            ANSWER      + " TEXT," +
            DATE        + " TEXT," +
            SYNC        + " BOOLEAN," +
            "PRIMARY KEY (" + SURVEY_ID + "," + QUESTION + "))" ;
*/

    public int survey_total ;

    public int survey_complited ;
    public double avg_time ;


    public Database_Numbers(Context context) {
        super(context, DATABASE, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        int count = cursor.getCount();
        if (cursor.moveToNext() == true) {
            ContentValues cn = new ContentValues() ;
            cn.put(TOTAL_PATIENTS , 0);
            cn.put(PATIENTS_COMPLETED , 0) ;
          //  cn.put(AVETIME,0.0) ;
        }
       else {
            survey_total = num_patient() ;
         //   avg_time = TimeAvg() ;
            survey_complited = NumFurmComplete() ;

        }

    }
    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_SENTIX);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public int num_patient () {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery =  "SELECT " + TOTAL_PATIENTS + " from " + TABLE_NAME ;
        Cursor cursor = db.rawQuery(selectQuery , null) ;
        if ( cursor.moveToFirst() == false) return 0;
        return cursor.getInt(cursor.getColumnIndex(TOTAL_PATIENTS)) ;
    }


    public int NumFurmComplete() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery =  "SELECT  " + PATIENTS_COMPLETED + " from " + TABLE_NAME ;
        Cursor cursor = db.rawQuery(selectQuery , null) ;
        if ( cursor.moveToFirst() == false) return 0;
        return cursor.getInt(cursor.getColumnIndex(PATIENTS_COMPLETED)) ;
    }

    public Double TimeAvg() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery =  "SELECT  " + AVETIME + " from " + TABLE_NAME ;
        Cursor cursor = db.rawQuery(selectQuery , null) ;
        if ( cursor.moveToFirst() == false) return 0.0;
        return cursor.getDouble(cursor.getColumnIndex(AVETIME)) ;
    }

    public void SurveyCompleted (double time) {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
       // db.execSQL(CREATE_SENTIX);
        ContentValues nums = new ContentValues() ;
       survey_complited = survey_complited + 1 ;
        nums.put(TOTAL_PATIENTS,survey_total);
    // nums.put(AVETIME ,time);
        nums.put(PATIENTS_COMPLETED,survey_complited);
        db.update(TABLE_NAME , nums , TOTAL_PATIENTS + " = "+survey_total, null) ;
    }

    public void SurveyStarted () {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues nums = new ContentValues() ;
        survey_total = survey_total + 1 ;
        nums.put(TOTAL_PATIENTS,survey_total);
        db.update(TABLE_NAME , nums , AVETIME + " = "+avg_time, null) ;
    }


}
