package frontieres.sans.medecins.patientsatisfaction.Backend;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import frontieres.sans.medecins.patientsatisfaction.Backend.AsyncTask.GetOrganisationUnitsAsyncTask;
import frontieres.sans.medecins.patientsatisfaction.Throwables.InsertRowDatabaseThrowable;
import frontieres.sans.medecins.patientsatisfaction.Throwables.NullDatabaseThrowable;

public class DatabaseManagerImpl implements DatabaseManager {

    Database database;

    public DatabaseManagerImpl(Context context) {
        database = new Database(context);
    }

    @Override
    public void storeSurvey(List<String> survey) {
        try {
            database.storeSurvey(survey);
        } catch (NullDatabaseThrowable nullDatabaseThrowable) {
            nullDatabaseThrowable.printStackTrace();
        } catch (InsertRowDatabaseThrowable insertRowDatabaseThrowable) {
            insertRowDatabaseThrowable.printStackTrace();
        }
    }
}
