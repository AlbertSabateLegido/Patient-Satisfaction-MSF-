package frontieres.sans.medecins.patientsatisfaction.Backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import frontieres.sans.medecins.patientsatisfaction.Survey;
import frontieres.sans.medecins.patientsatisfaction.Throwables.InsertRowDatabaseThrowable;
import frontieres.sans.medecins.patientsatisfaction.Throwables.NullDatabaseThrowable;

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE    = "survey.db";
    public static final String TABLE_NAME  = "survey";
    public static final String SURVEY_ID   = "survey_id";
    public static final String QUESTION    = "question_id";
    public static final String ANSWER      = "answer";
    public static final String DATE        = "date";
    public static final String SYNC        = "sync";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            SURVEY_ID   + " INTEGER," +
            QUESTION    + " TEXT," +
            ANSWER      + " TEXT," +
            DATE        + " TEXT," +
            SYNC        + " BOOLEAN," +
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

    public void storeSurvey (Survey survey) throws NullDatabaseThrowable, InsertRowDatabaseThrowable {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        if (sqLiteDatabase == null) throw new NullDatabaseThrowable();

        List<String> answeredQuestions = survey.getAnsweredQuestions();


        for (int i = 0; i+1 < answeredQuestions.size(); i+=2) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(SURVEY_ID,survey.getSurveyId());
            contentValues.put(QUESTION,answeredQuestions.get(i));
            contentValues.put(ANSWER,answeredQuestions.get(i+1));
            contentValues.put(DATE,new SimpleDateFormat("yyyy-MM-dd").format(survey.getDate()));
            contentValues.put(SYNC,false);
            long result = sqLiteDatabase.insert(TABLE_NAME,null ,contentValues);
            if(result == -1) throw new InsertRowDatabaseThrowable();
        }

        return;
    }
}
