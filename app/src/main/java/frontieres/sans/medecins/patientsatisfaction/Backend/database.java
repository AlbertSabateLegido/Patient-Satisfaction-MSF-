package frontieres.sans.medecins.patientsatisfaction.Backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Home on 30/03/2018.
 */



public class database extends SQLiteOpenHelper {

    public static final String Database = "answer_table" ;
    public static final String Table_Name = "ANSWERS" ;
    public static final String col1_ID_PATIENT = "ID_PATIENT" ;
    public static final String col2_ID_QUESTION = "ID_QUESTION" ;
    public static final String col3_ANSWER = "ANSWER" ;
   public static final String CREATE_TABLE_SENTIX = "CREATE TABLE " + Table_Name +" ( "+col1_ID_PATIENT + " INTEGER ," +
            ","+ col2_ID_QUESTION +" INTEGER  ,  " + col3_ANSWER + " TEXT , PRIMARY KEY ("+col1_ID_PATIENT +" , " +col2_ID_QUESTION + ")) ;" ;

  /*  public static final String CREATE_TABLE_SENTIX = "CREATE TABLE ANSWERS (  INTEGER ,ID_QUESTION INTEGER  ,  ANSWER TEXT ," +
            " PRIMARY KEY (ID_PATIENT , ID_QUESTION)) ;" ;*/

    public database(Context context) {
        super(context, Database, null, 1);
    }


    @Override

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SENTIX);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertData(int ID_PATIENT,int ID_QUETION,String ANSWER) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1_ID_PATIENT,ID_PATIENT );
        contentValues.put(col2_ID_QUESTION,ID_QUETION );
        contentValues.put(col3_ANSWER,ANSWER );
        long result = db.insert(Table_Name,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true ;
    }
}
