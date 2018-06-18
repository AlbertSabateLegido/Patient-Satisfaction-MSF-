package frontieres.sans.medecins.patientsatisfaction.Backend.AsyncTask;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class GetOrganisationUnitsAsyncTask extends AsyncTask<Void,Void,Map<String,String>>{

    @Override
    protected Map<String, String> doInBackground(Void... voids) {

        Map<String,String> organisationUnits = new HashMap<>();

        try {
            URL url = new URL(Authenticator.getServerUrl() + "/api/organisationUnits");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            String base64 = Authenticator.getAuthentication();

            System.out.println("Base64: " + base64);

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Basic " + base64);

            if (connection.getResponseCode() == 200) {
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder response = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                JSONObject jsonObject  = new JSONObject(response.toString());
                JSONObject pagerObject = jsonObject.getJSONObject("pager");
                JSONArray bodyArray   = jsonObject.getJSONArray("organisationUnits");

                for(int i = 0; i < bodyArray.length(); ++i) {
                    JSONObject organisationUnit = bodyArray.getJSONObject(i);
                    organisationUnits.put(organisationUnit.getString("displayName"),
                            organisationUnit.getString("id"));
                }

                while (pagerObject.has("nextPage")) {
                    url = new URL(pagerObject.getString("nextPage"));
                    connection = (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Authorization", "Basic " + base64);

                    inputStream = new BufferedInputStream(connection.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    jsonObject  = new JSONObject(response.toString());
                    pagerObject = jsonObject.getJSONObject("pager");
                    bodyArray   = jsonObject.getJSONArray("organisationUnits");

                    for(int i = 0; i < bodyArray.length(); ++i) {
                        JSONObject organisationUnit = bodyArray.getJSONObject(i);
                        organisationUnits.put(organisationUnit.getString("displayName"),
                                organisationUnit.getString("id"));
                    }
                }

            } else {
                // Error handling code goes here
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return organisationUnits;
    }
}
