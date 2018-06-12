package frontieres.sans.medecins.patientsatisfaction.Backend;

import android.content.Context;

import java.util.List;

import frontieres.sans.medecins.patientsatisfaction.Backend.AsyncTask.StoreSurveyEventAsyncTask;
import frontieres.sans.medecins.patientsatisfaction.Survey;
import frontieres.sans.medecins.patientsatisfaction.Throwables.InsertRowDatabaseThrowable;
import frontieres.sans.medecins.patientsatisfaction.Throwables.NullDatabaseThrowable;

public class DatabaseManagerImpl implements DatabaseManager {

    Database database;
    Database_Numbers DatabaseGeneral ;


    public DatabaseManagerImpl(Context context) {
        database = new Database(context);
        DatabaseGeneral = new Database_Numbers(context) ;

    }

    @Override
    public void storeSurvey(Survey survey) {
        try {
            database.storeSurvey(survey);
            new MyStoreSurveyEventAsyncTask().execute(survey);
        } catch (NullDatabaseThrowable nullDatabaseThrowable) {
            nullDatabaseThrowable.printStackTrace();
        } catch (InsertRowDatabaseThrowable insertRowDatabaseThrowable) {
            insertRowDatabaseThrowable.printStackTrace();
        }
        DatabaseGeneral.SurveyCompleted (5);
    }

   public int num_answers(String answer) {

        return database.count ("patient") ;
    }

    private static class MyStoreSurveyEventAsyncTask extends StoreSurveyEventAsyncTask {

        @Override
        protected void onPostExecute(List<Integer> surveyId) {

        }
    }
}
