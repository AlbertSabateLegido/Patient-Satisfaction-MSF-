package frontieres.sans.medecins.patientsatisfaction.Backend.AsyncTask;

import android.os.AsyncTask;
import android.util.Base64;

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

public abstract class GetOrganisationUnitsAsyncTask extends AsyncTask<String,Void,Map<String,String>>{

    private String serverUrl = "https://play.dhis2.org/dev";
    private String userpassw = "admin:district";

    @Override
    protected Map<String, String> doInBackground(String... voids) {

        Map<String,String> organisationUnits = new HashMap<>();

        try {
            URL url = new URL(serverUrl + "/api/organisationUnits");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            byte[] data = userpassw.getBytes("UTF-8");
            String base64 = Base64.encodeToString(data, Base64.DEFAULT);

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
                    organisationUnits.put(organisationUnit.getString("id"),
                            organisationUnit.getString("displayName"));
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
                        organisationUnits.put(organisationUnit.getString("id"),
                                organisationUnit.getString("displayName"));
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
