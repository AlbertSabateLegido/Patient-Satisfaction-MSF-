package frontieres.sans.medecins.patientsatisfaction.Backend;

import java.util.List;

import frontieres.sans.medecins.patientsatisfaction.Survey;

public interface DatabaseManager {

    void storeSurvey(Survey survey);

    int num_answers(String answer) ;

    List<String> getUserInformation();

    void storeUserInformation(String serverURL, String username, String password);

    void storeOrganisationUnit(String organisationUnit);

    void storeId(String id);
}
