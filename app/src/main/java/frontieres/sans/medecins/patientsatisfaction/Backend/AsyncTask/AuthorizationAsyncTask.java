package frontieres.sans.medecins.patientsatisfaction.Backend.AsyncTask;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ContentHandler;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import frontieres.sans.medecins.patientsatisfaction.Backend.DatabaseManager;
import frontieres.sans.medecins.patientsatisfaction.Backend.DatabaseManagerImpl;

public class AuthorizationAsyncTask extends AsyncTask<List<String>,Void,Integer> {

    @Override
    protected Integer doInBackground(List<String>... lists) {
        //{0} ServerURL, {1} Username, {2} Password
        String serverURL = lists[0].get(0);
        String username  = lists[0].get(1);
        String password  = lists[0].get(2);
        
        Integer returnValue = null;

        try {
            URL url = new URL(serverURL + "/api/me");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            byte[] data = (username + ':' + password).getBytes("UTF-8");
            String base64 = Base64.encodeToString(data, Base64.DEFAULT);

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Basic " + base64);
            
            returnValue = connection.getResponseCode();

            if(returnValue == 200) {
                Authenticator.setAuthenticator(serverURL,username,password);
                InputStream inputStream = connection.getInputStream();
                String response = convertStreamToString(inputStream);
                String userId = extractId(response);
                System.out.println("new ID: " + userId);
                Authenticator.setId(userId);

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    private String convertStreamToString(InputStream is) throws UnsupportedEncodingException {

        BufferedReader reader = new BufferedReader(new
                InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private String extractId(String response) {
        int index = response.indexOf('"' + "id" + '"');
        String subString = response.substring(index+6);
        int i = 0;
        while (subString.charAt(i) != '"') ++i;
        return subString.substring(0,i);
    }

}
