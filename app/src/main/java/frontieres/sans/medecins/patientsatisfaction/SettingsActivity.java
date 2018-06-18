package frontieres.sans.medecins.patientsatisfaction;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import frontieres.sans.medecins.patientsatisfaction.Backend.AsyncTask.Authenticator;
import frontieres.sans.medecins.patientsatisfaction.Backend.AsyncTask.AuthorizationAsyncTask;
import frontieres.sans.medecins.patientsatisfaction.Backend.AsyncTask.GetOrganisationUnitsAsyncTask;
import frontieres.sans.medecins.patientsatisfaction.Backend.DatabaseManager;
import frontieres.sans.medecins.patientsatisfaction.Backend.DatabaseManagerImpl;

public class SettingsActivity extends AppCompatActivity {

    private List<String> organisationUnitKeys;

    private String url;

    private Button bAccept;
    private Spinner  spinner;
    private FrameLayout progressBarHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final EditText etServerURL = findViewById(R.id.etServerURL);
        final EditText etUsername  = findViewById(R.id.etUsername);
        final EditText etPassword  = findViewById(R.id.etPassword);
        spinner     = findViewById(R.id.sOrganisationUnit);
        progressBarHolder = findViewById(R.id.progressBarHolder);


        final DatabaseManager databaseManager =  new DatabaseManagerImpl(getApplicationContext());
        List<String> userInformation = databaseManager.getUserInformation();
        bAccept = findViewById(R.id.bAccept);

        if(userInformation != null) {
            url = userInformation.get(0);
            etServerURL.setText(userInformation.get(0));
            etUsername.setText(userInformation.get(1));
            etPassword.setText(userInformation.get(2));
            new MyGetOrganisationUnitsAsyncTask().execute();
        }

        Button bCancel = findViewById(R.id.bCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> params = new ArrayList<>();
                params.add(etServerURL.getText().toString());
                params.add(etUsername.getText().toString());
                params.add(etPassword.getText().toString());
                try {
                    if(url.equals(etServerURL.getText().toString())){
                        if(spinner.getAdapter() == null) {
                            new MyGetOrganisationUnitsAsyncTask().execute();
                        }
                        else {
                            databaseManager.storeOrganisationUnit(organisationUnitKeys.get(spinner.getSelectedItemPosition()));
                            Authenticator.setOrganisationUnit(organisationUnitKeys.get(spinner.getSelectedItemPosition()));
                            Toast.makeText(getApplicationContext(),"Organisation Unit selected",Toast.LENGTH_LONG).show();
                        }
                        return;
                    }
                    Integer response = new AuthorizationAsyncTask().execute(params).get();
                    if(response != null && response == 200) {
                        url = etServerURL.getText().toString();
                        databaseManager.storeUserInformation(etServerURL.getText().toString(),
                                etUsername.getText().toString(),etPassword.getText().toString());
                        Authenticator.setAuthenticator(etServerURL.getText().toString(),
                                etUsername.getText().toString(),etPassword.getText().toString());
                        databaseManager.storeId(Authenticator.getId());
                        Toast.makeText(getApplicationContext(),"Connection successful",Toast.LENGTH_LONG).show();

                    }
                    else Toast.makeText(getBaseContext(),"Connection failed, response " + response,Toast.LENGTH_LONG).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class MyGetOrganisationUnitsAsyncTask extends GetOrganisationUnitsAsyncTask {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            bAccept.setEnabled(false);

            AlphaAnimation inAnimation = new AlphaAnimation(0f, 1f);
            inAnimation.setDuration(200);
            progressBarHolder.setAnimation(inAnimation);
            progressBarHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Map<String, String> organisationUnitsMap) {
            super.onPostExecute(organisationUnitsMap);

            Set<String> keySet = organisationUnitsMap.keySet();
            List<String> organisationUnitNames = new ArrayList<>(keySet);
            Collections.sort(organisationUnitNames);
            organisationUnitKeys  = new ArrayList<>();

            for(String name:organisationUnitNames) {
                organisationUnitKeys.add(organisationUnitsMap.get(name));
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, organisationUnitNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            if(Authenticator.getOrganisationUnit() != null) {
                int i = organisationUnitKeys.indexOf(Authenticator.getOrganisationUnit());
                if (i >= 0) spinner.setSelection(i);
            }

            AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);
            outAnimation.setDuration(200);
            progressBarHolder.setAnimation(outAnimation);
            progressBarHolder.setVisibility(View.GONE);

            bAccept.setEnabled(true);
        }
    }
}
