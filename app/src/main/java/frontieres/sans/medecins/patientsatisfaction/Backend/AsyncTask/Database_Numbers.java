package frontieres.sans.medecins.patientsatisfaction.Backend.AsyncTask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Home on 18/05/2018.
 */

public class Database_Numbers extends SQLiteOpenHelper {

    public static final String DATABASE = "generalsurvey.db" ;
    public static final String TABLE_NAME = "PATIENTS" ;
    public static final String TOTAL_PATIENTS = "NUM_PATIENTS_TOT" ;
    public static final String PATIENTS_COMPLETED = "NUM_PATIENT_COMPLETED" ;
    public static final String PATIENTS_SEND = "NUM_PATIENT_SENT" ;
    public  static final String AVETIME = "AVG_TIME" ;
    public static  final String CREATE_SENTIX = "create table " + TABLE_NAME + "(" + TOTAL_PATIENTS + " INTEGER ,"+ PATIENTS_COMPLETED +"   INTEGER , " +
    PATIENTS_SEND +" INTEGER " + AVETIME + "REAL )"  ;

    public Database_Numbers(Context context) {
        super(context, DATABASE, null, 1);
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int count = cursor.getCount();
        if (count == 0) {
            ContentValues cn = new ContentValues() ;
            cn.put(TOTAL_PATIENTS , 0);
            cn.put(PATIENTS_COMPLETED , 0) ;
            cn.put(PATIENTS_SEND,0);
            cn.put(AVETIME,0.0) ;
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
        String selectQuery =  "SELECT * " + TOTAL_PATIENTS + " from " + TABLE_NAME ;
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

    public int num_form_send() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery =  "SELECT  " + PATIENTS_SEND + " from " + TABLE_NAME ;
        Cursor cursor = db.rawQuery(selectQuery , null) ;
        if ( cursor.moveToFirst() == false) return 0;
        return cursor.getInt(cursor.getColumnIndex(TOTAL_PATIENTS)) ;
    }
    public void update_patients_nums (int patient_num , int patient_sent , int patient_complete , int last_patient_num) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues nums = new ContentValues() ;
        nums.put(TOTAL_PATIENTS , patient_num);
        nums.put(PATIENTS_COMPLETED,patient_sent);
        nums.put(PATIENTS_SEND , patient_complete);
        db.update(TABLE_NAME , nums , TOTAL_PATIENTS + " = "+last_patient_num , null) ;
    }


}
