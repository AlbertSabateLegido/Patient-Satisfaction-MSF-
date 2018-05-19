package frontieres.sans.medecins.patientsatisfaction.Backend.AsyncTask;

import android.os.AsyncTask;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import frontieres.sans.medecins.patientsatisfaction.Survey;

public abstract class StoreSurveyEventAsyncTask extends AsyncTask<Survey, Void, List<Integer>> {

    private String serverUrl = "http://7b3b0e65.ngrok.io";
    private String userpassw = "admin:district";

    private String program = "NWilOXg9fNG";
    private String orgUnit = "va0QxwJsMxt";
    private String storedBy = "RjfUlTAbAfw";

    private String questionDataElement = "dojkLBcssnT";
    private String answerDataElement = "sBUYDC6AYet";

    @Override
    protected List<Integer> doInBackground(Survey... surveys) {

        List<Integer> storedSurveyIds = new ArrayList<>();

        try {
            URL url = new URL(serverUrl + "/api/events");
            String base64 = Authenticator.getAuthentication();

            for(Survey survey:surveys) {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Authorization", "Basic " + base64);

                JSONArray events = new JSONArray();

                List<String> answeredQuestions = survey.getAnsweredQuestions();

                for (int i = 0; i + 1 < answeredQuestions.size(); i += 2) {
                    JSONObject event = new JSONObject();
                    event.put("program", program);
                    event.put("orgUnit", orgUnit);
                    event.put("eventDate", "2018-05-15");
                    event.put("status", "COMPLETED");
                    event.put("storedBy", storedBy);

                    JSONArray dataElements = new JSONArray();
                    JSONObject dataElement = new JSONObject();

                    dataElement.put("dataElement", questionDataElement);
                    dataElement.put("value", answeredQuestions.get(i));
                    dataElements.put(dataElement);

                    dataElement = new JSONObject();
                    dataElement.put("dataElement", answerDataElement);
                    dataElement.put("value", answeredQuestions.get(i + 1));
                    dataElements.put(dataElement);

                    event.put("dataValues", dataElements);
                    events.put(event);
                }

                JSONObject payload = new JSONObject();
                payload.put("events", events);

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(payload.toString());
                writer.flush();

                connection.connect();

                System.out.println("PAYLOAD: " + payload);
                System.out.println("RESPONSE POST: " + connection.getResponseMessage());

                if(connection.getResponseCode() == 200) storedSurveyIds.add(survey.getSurveyId());
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return storedSurveyIds;
    }
}
