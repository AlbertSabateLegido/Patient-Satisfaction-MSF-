package frontieres.sans.medecins.patientsatisfaction;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hsalf.smilerating.SmileRating;

public class MainFragment extends Fragment {

    private int layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null) {
            String qType = bundle.getString("QuestionType");
            if (qType.equals(SurveyManager.SMILEY_RATING)) {
                layout = R.layout.fragment_smileyrating;
                return;
            }
            if (qType.equals(SurveyManager.TWO_BUTTONS)) {
                layout = R.layout.fragment_twobuttons;
                return;
            }
            if (qType.equals(SurveyManager.THREE_BUTTONS)) {
                layout = R.layout.fragment_threebuttons;
                return;
            }
            if (qType.equals(SurveyManager.FOUR_BUTTONS)) {
                layout = R.layout.fragment_fourbuttons;
                return;
            }
        }
        //ERROR
        layout = R.layout.fragment_twobuttons;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(layout, container, false);
        showAnswers(v);
        return v;
    }

    private void showAnswers(View v) {
        if(SurveyManager.getCurrentQuestionType() == SurveyManager.SMILEY_RATING) {
            SmileRating smileRating = v.findViewById(R.id.smile_rating);
            smileRating.setOnRatingSelectedListener((SmileRating.OnRatingSelectedListener) getActivity());
            smileRating.setSelectedSmile(2);
            System.out.println("OnRatingSelectedListener assigned");
            return;
        }

        String[] answers = SurveyManager.getCurrentQuestionAnswers();
        Button bFirstAnswer = v.findViewById(R.id.bFirstAnswer);
        if(answers.length >= 0 && bFirstAnswer != null) {
            bFirstAnswer.setText(answers[0]);
            bFirstAnswer.setOnClickListener((View.OnClickListener) getActivity());
        }
        Button bSecondAnswer = v.findViewById(R.id.bSecondAnswer);
        if(answers.length >= 1 && bSecondAnswer != null){
            bSecondAnswer.setText(answers[1]);
            bSecondAnswer.setOnClickListener((View.OnClickListener) getActivity());
        }
        Button bThirdAnswer = v.findViewById(R.id.bThirdAnswer);
        if(answers.length >= 2 && bThirdAnswer != null) {
            bThirdAnswer.setText(answers[2]);
            bThirdAnswer.setOnClickListener((View.OnClickListener) getActivity());
        }
        Button bFourthAnswer = v.findViewById(R.id.bFourthAnswer);
        if(answers.length >= 3 && bFourthAnswer != null) {
            bFourthAnswer.setText(answers[3]);
            bFourthAnswer.setOnClickListener((View.OnClickListener) getActivity());
        }
    }
}
