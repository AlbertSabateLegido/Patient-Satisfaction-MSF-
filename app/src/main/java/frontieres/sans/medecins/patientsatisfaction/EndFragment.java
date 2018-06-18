package frontieres.sans.medecins.patientsatisfaction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class EndFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> answeredQuestions = SurveyManager.getSurvey().getAnsweredQuestions();
        for(int i = 0; i+1 < answeredQuestions.size(); i = i+2) {
            System.out.println(answeredQuestions.get(i) + ": " + answeredQuestions.get(i+1) );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_end, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                assert activity != null;
                activity.restartQuestionary();
            }
        });

        return view;
    }
}
