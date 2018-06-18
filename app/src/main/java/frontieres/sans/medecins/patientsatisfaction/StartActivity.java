package frontieres.sans.medecins.patientsatisfaction;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import frontieres.sans.medecins.patientsatisfaction.Backend.AsyncTask.Authenticator;
import frontieres.sans.medecins.patientsatisfaction.Backend.DatabaseManager;
import frontieres.sans.medecins.patientsatisfaction.Backend.DatabaseManagerImpl;


public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Attaching the layout to the toolbar object
        Toolbar toolbar = findViewById(R.id.tool_bar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);

        Button bStart = findViewById(R.id.bStart);
        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        DatabaseManager databaseManager =  new DatabaseManagerImpl(getApplicationContext());

        List<String> userInformation = databaseManager.getUserInformation();
        if(userInformation != null) {
            Authenticator.setAuthenticator(userInformation.get(0),userInformation.get(1),
                    userInformation.get(2),userInformation.get(3),userInformation.get(4));

            System.out.println("SERVER_URL: " + userInformation.get(0));
            System.out.println("USERNAME: "   + userInformation.get(1));
            System.out.println("PASSWORD: "   + userInformation.get(2));
            System.out.println("ID: "         + userInformation.get(3));
            System.out.println("ORGUNIT: "    + userInformation.get(4));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}