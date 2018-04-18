package frontieres.sans.medecins.patientsatisfaction.Backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Home on 30/03/2018.
 */



public class database extends SQLiteOpenHelper {

    public static final String Database = "ANSWER_TABLE.db" ;
    public static final String Table_Name = "ANSWERS" ;
    public static final String col1_ID_PATIENT = "ID_PATIENT" ;
    public static final String col2_ID_QUESTION = "ID_QUESTION" ;
    public static final String col3_ANSWER = "ANSWER" ;

    //////



   public static final String CREATE_TABLE_SENTIX = "CREATE TABLE ANSWERS ( ID_PATIENT INTEGER  AUTOINCREMENT,ID_QUESTION INTEGER  , " +
           " ANSWER TEXT ,PRIMARY KEY (ID_PATIENT , ID_QUESTION)) " ;

    public database(Context context) {
        super(context, Database, null, 1);
    }


    @Override

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Table_Name + "(" + col1_ID_PATIENT + " INTEGER ,"+ col2_ID_QUESTION +"   INTEGER , " +
                 col3_ANSWER +" TEXT ,PRIMARY KEY ( "+col1_ID_PATIENT+" ," +col2_ID_QUESTION +"))");
    }




    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertData1(int ID_PATIENT,int ID_QUETION,String ANSWER) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db == null) {
            return false ;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1_ID_PATIENT,ID_PATIENT );
        contentValues.put(col2_ID_QUESTION,ID_QUETION );
        contentValues.put(col3_ANSWER,ANSWER );
        long result = db.insert(Table_Name,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }


   public int Next_Id_Patient (){
       SQLiteDatabase db = this.getWritableDatabase();
       String selectQuery =  "SELECT MAX("+ col1_ID_PATIENT +") as "+ col1_ID_PATIENT +"  FROM " +  Table_Name  ;
       Cursor cursor =  db.rawQuery(selectQuery, null);
       int count = cursor.getCount();
       if (count == 0) return 0 ;
       cursor.moveToFirst() ;
       int Last_Patient = cursor.getInt( cursor.getColumnIndex(col1_ID_PATIENT));
       return  Last_Patient + 1 ;
   }

public boolean delete (int n) {
    SQLiteDatabase db = this.getWritableDatabase();
    return db.delete(Table_Name , col1_ID_PATIENT + '=' + n ,null) > 0 ;

}



}
