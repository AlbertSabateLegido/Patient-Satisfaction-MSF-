package frontieres.sans.medecins.patientsatisfaction.Backend.AsyncTask;


import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class Authenticator {

    private static String SERVER_URL;
    private static String USERNAME;
    private static String PASSWORD;
    private static String ID;
    private static String ORGANISATION_UNIT;

    public static String getAuthentication() throws UnsupportedEncodingException {
        byte[] data = (USERNAME + ':' + PASSWORD).getBytes("UTF-8");
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    public static String getServerUrl() {
        return SERVER_URL;
    }

    public static void setAuthenticator(String serverURL, String username, String password) {
        Authenticator.SERVER_URL = serverURL;
        Authenticator.USERNAME = username;
        Authenticator.PASSWORD = password;
    }

    public static void setAuthenticator(String serverURL, String username, String password,String id,
                                        String organisationUnit) {
        setAuthenticator(serverURL,username,password);
        Authenticator.ID = id;
        Authenticator.ORGANISATION_UNIT = organisationUnit;
    }

    public static void setOrganisationUnit(String organisationUnit) {
        Authenticator.ORGANISATION_UNIT = organisationUnit;
    }

    public static String getOrganisationUnit() {
        return ORGANISATION_UNIT;
    }

    public static void setId(String id) {
        Authenticator.ID = id;
    }

    public static String getId() {
        return ID;
    }
}
