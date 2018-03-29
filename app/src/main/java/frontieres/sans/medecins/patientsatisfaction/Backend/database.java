package com.example.home.metges_sens_fronteres;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Home on 30/03/2018.
 */



public class database extends SQLiteOpenHelper {

    public static final String database = "answer_table" ;
    public static final String table_name = "answers" ;
    public static final String col1 = "id" ;
    public static final String col2 = "answer" ;

    public database(Context context) {
        super(context, database, null, 1);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        String S = "create table answers (ID INTEGER PRIMARY KEY AUTOINCREMENT , answer TEXT) " ;
        db.execSQL(S);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertData(String name,String surname,String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1,name);
        long result = db.insert(table_name,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
}
