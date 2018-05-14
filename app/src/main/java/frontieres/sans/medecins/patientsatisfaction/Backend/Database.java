package frontieres.sans.medecins.patientsatisfaction.Backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import frontieres.sans.medecins.patientsatisfaction.Throwables.InsertRowDatabaseThrowable;
import frontieres.sans.medecins.patientsatisfaction.Throwables.NullDatabaseThrowable;

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE    = "survey.db";
    public static final String TABLE_NAME  = "survey";
    public static final String SURVEY_ID   = "survey_id";
    public static final String QUESTION    = "question_id";
    public static final String ANSWER      = "answer";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            SURVEY_ID   + " INTEGER," +
            QUESTION    + " TEXT," +
            ANSWER      + " TEXT," +
            "PRIMARY KEY (" + SURVEY_ID + "," + QUESTION + "))" ;

    public Database(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void storeSurvey (List<String> survey) throws NullDatabaseThrowable, InsertRowDatabaseThrowable {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        if (sqLiteDatabase == null) throw new NullDatabaseThrowable();

        int surveyId = nextSurveyId();

        for (int i = 0; i+1 < survey.size(); i+=2) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(SURVEY_ID,surveyId);
            contentValues.put(QUESTION,survey.get(i));
            contentValues.put(ANSWER,survey.get(i+1));
            long result = sqLiteDatabase.insert(TABLE_NAME,null ,contentValues);
            if(result == -1) throw new InsertRowDatabaseThrowable();
        }

        return;
    }

    private int nextSurveyId (){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query =  "SELECT MAX(" + SURVEY_ID + ") as " + SURVEY_ID +
                " FROM " + TABLE_NAME;
        Cursor cursor =  sqLiteDatabase.rawQuery(query, null);
        int count = cursor.getCount();
        if (count == 0) return 0 ;
        cursor.moveToFirst() ;
        int lastSurveyId = cursor.getInt(cursor.getColumnIndex(SURVEY_ID));
        return  lastSurveyId + 1 ;
    }

    public boolean delete (int surveyId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, SURVEY_ID + '=' + surveyId ,null) > 0 ;
    }
}
