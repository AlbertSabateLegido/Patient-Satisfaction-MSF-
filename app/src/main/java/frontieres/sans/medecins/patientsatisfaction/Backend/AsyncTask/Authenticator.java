package frontieres.sans.medecins.patientsatisfaction.Backend.AsyncTask;


import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class Authenticator {

    private static String username = "admin";
    private static String password = "district";

    public static String getAuthentication() throws UnsupportedEncodingException {
        byte[] data = (username + ':' + password).getBytes("UTF-8");
        return Base64.encodeToString(data, Base64.DEFAULT);
    }
}
