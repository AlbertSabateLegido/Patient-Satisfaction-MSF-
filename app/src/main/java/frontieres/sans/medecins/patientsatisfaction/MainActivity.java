package frontieres.sans.medecins.patientsatisfaction;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuestionManager.createQuestions();

        showQuestion();
    }


    public void showQuestion() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();


        if(QuestionManager.currentIdQuestion == null){
            ft.replace(R.id.container,new EndFragment());
            ft.commit();

            TextView tvQuestion = findViewById(R.id.question);
            tvQuestion.setText("Thanks");

            return;
        }

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

    @Override
    public void onClick(View view) {
        String answer = getAnswer(view);
        //guardar resposta a la bd
        QuestionManager.nextQuestion(answer);
        showQuestion();
    }

    private String getAnswer(View view) {
        String[] answers = QuestionManager.getCurrentQuestionAnswers();
        switch (view.getId()) {
            case R.id.bFirstAnswer:
                return answers[0];
            case R.id.bSecondAnswer:
                return answers[1];
            case R.id.bThirdAnswer:
                return answers[2];
            case R.id.bFourthAnswer:
                return answers[3];
        }
        return null;
    }

    private void restartQuestionary() {
        QuestionManager.restartQuestionary();
        showQuestion();
    }

    @Override
    public void onBackPressed() {
        restartQuestionary();
    }
}
