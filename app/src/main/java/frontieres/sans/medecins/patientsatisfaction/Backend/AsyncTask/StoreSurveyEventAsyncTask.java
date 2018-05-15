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
import java.text.SimpleDateFormat;
import java.util.List;


import frontieres.sans.medecins.patientsatisfaction.Survey;

public abstract class StoreSurveyEventAsyncTask extends AsyncTask<Survey, Void, Integer> {

    private String serverUrl = "http://7b3b0e65.ngrok.io";
    private String userpassw = "admin:district";

    private String program = "NWilOXg9fNG";
    private String orgUnit = "va0QxwJsMxt";
    private String storedBy = "RjfUlTAbAfw";

    private String QuestionDataElement = "dojkLBcssnT";
    private String AnswerDataElement = "sBUYDC6AYet";

    @Override
    protected Integer doInBackground(Survey... surveys) {
        try {
            URL url = new URL(serverUrl + "/api/events");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            byte[] data = userpassw.getBytes("UTF-8");
            String base64 = Base64.encodeToString(data, Base64.DEFAULT);

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Authorization", "Basic " + base64);

            JSONArray events = new JSONArray();

            System.out.println("#Survey: " + surveys.length);

            List<String> answeredQuestions = surveys[0].getAnsweredQuestions();

            for(int i = 0; i+1 < answeredQuestions.size(); i+=2) {
                JSONObject event = new JSONObject();
                event.put("program",program);
                event.put("orgUnit",orgUnit);
                event.put("eventDate","2018-05-15");
                event.put("status","COMPLETED");
                event.put("storedBy",storedBy);

                JSONArray dataElements = new JSONArray();
                JSONObject dataElement = new JSONObject();

                dataElement.put("dataElement",QuestionDataElement);
                dataElement.put("value",answeredQuestions.get(i));
                dataElements.put(dataElement);

                dataElement = new JSONObject();
                dataElement.put("dataElement",AnswerDataElement);
                dataElement.put("value",answeredQuestions.get(i+1));
                dataElements.put(dataElement);

                event.put("dataValues",dataElements);
                events.put(event);
            }

            JSONObject payload = new JSONObject();
            payload.put("events",events);

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(payload.toString());
            writer.flush();

            connection.connect();

            System.out.println("PAYLOAD: " + payload);
            System.out.println("RESPONSE POST: " + connection.getResponseMessage());

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return surveys[0].getSurveyId();
    }
}
