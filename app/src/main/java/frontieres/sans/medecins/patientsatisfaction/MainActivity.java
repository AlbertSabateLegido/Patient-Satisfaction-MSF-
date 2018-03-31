package frontieres.sans.medecins.patientsatisfaction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuestionManager.createQuestions();

        showQuestion();
    }

    private void showQuestion() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("QuestionType", QuestionManager.getCurrentQuestionType());
        MyFragment fragment = new MyFragment();
        fragment.setArguments(bundle);
        ft.replace(R.id.container, fragment);
        ft.addToBackStack(null);
        ft.commit();

        TextView tvQuestion = findViewById(R.id.question);
        tvQuestion.setText(QuestionManager.getCurrentQuestionText());
    }

}
