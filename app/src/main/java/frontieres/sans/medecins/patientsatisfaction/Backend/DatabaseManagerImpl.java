package frontieres.sans.medecins.patientsatisfaction.Backend;

import android.content.Context;

import java.util.List;

import frontieres.sans.medecins.patientsatisfaction.Backend.AsyncTask.StoreSurveyEventAsyncTask;
import frontieres.sans.medecins.patientsatisfaction.Survey;
import frontieres.sans.medecins.patientsatisfaction.Throwables.InsertRowDatabaseThrowable;
import frontieres.sans.medecins.patientsatisfaction.Throwables.NullDatabaseThrowable;

public class DatabaseManagerImpl implements DatabaseManager {

    Database database;
    Database_Numbers database_numbers;


    public DatabaseManagerImpl(Context context) {
        database = new Database(context);
        database_numbers = new Database_Numbers(context) ;

    }

    @Override
    public void storeSurvey(Survey survey) {
        try {
            database.storeSurvey(survey);
            new StoreSurveyEventAsyncTask().execute(survey);
        } catch (NullDatabaseThrowable nullDatabaseThrowable) {
            nullDatabaseThrowable.printStackTrace();
        } catch (InsertRowDatabaseThrowable insertRowDatabaseThrowable) {
            insertRowDatabaseThrowable.printStackTrace();
        }
        database_numbers.SurveyCompleted (5);
    }

    public int num_answers(String answer) {
        return database.count ("patient") ;
    }

    @Override
    public List<String> getUserInformation() {
        return database_numbers.getUserInformation();
    }

    @Override
    public void storeUserInformation(String serverURL, String username, String password) {
        database_numbers.storeUserInformation(serverURL,username,password);
    }

    @Override
    public void storeOrganisationUnit(String organisationUnit) {
        database_numbers.storeOrganisationUnit(organisationUnit);
    }

    @Override
    public void storeId(String id) {
        database_numbers.storeId(id);
    }
}
