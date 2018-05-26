package frontieres.sans.medecins.patientsatisfaction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class EndFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        List<String> answeredQuestions = SurveyManager.getSurvey().getAnsweredQuestions();
        for(int i = 0; i+1 < answeredQuestions.size(); i = i+2) {
            System.out.println(answeredQuestions.get(i) + ": " + answeredQuestions.get(i+1) );
        }
        return inflater.inflate(R.layout.fragment_end, container, false);

    }
}
