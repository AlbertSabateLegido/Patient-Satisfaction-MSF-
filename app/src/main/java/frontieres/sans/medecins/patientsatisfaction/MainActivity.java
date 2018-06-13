package frontieres.sans.medecins.patientsatisfaction;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.hsalf.smilerating.SmileRating;

import java.util.ArrayList;
import java.util.List;

import frontieres.sans.medecins.patientsatisfaction.Backend.AsyncTask.GetOrganisationUnitsAsyncTask;
import frontieres.sans.medecins.patientsatisfaction.Backend.AsyncTask.StoreSurveyEventAsyncTask;
import frontieres.sans.medecins.patientsatisfaction.Backend.DatabaseManager;
import frontieres.sans.medecins.patientsatisfaction.Backend.DatabaseManagerImpl;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,SmileRating.OnRatingSelectedListener{

    DatabaseManager databaseManager;
    public long startTime ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseManager = new DatabaseManagerImpl(this);
        SurveyManager.createQuestions();
        showQuestion();

    }

    @Override
    public void onBackPressed() {
        restartQuestionary();
    }

    @Override
    public void onClick(View view) {
        String answer = getAnswer(view);
        grafics() ;
       // SurveyManager.nextQuestion(answer);
      //  showQuestion();
        startTime = System.currentTimeMillis();


    }

    @Override
    public void onRatingSelected(int level, boolean reselected) {
        SurveyManager.nextQuestion(Integer.toString(level));
        showQuestion();
    }

    private void showQuestion() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if(SurveyManager.currentIdQuestion == null){
            ft.replace(R.id.container,new EndFragment());
            ft.commit();

            TextView tvQuestion = findViewById(R.id.question);
            tvQuestion.setText("Thanks");
            long difference = System.currentTimeMillis() - startTime;
          //  databaseManager.SurveyCompleted (difference) ;

            SurveyManager.setSurveyDate();
            databaseManager.storeSurvey(SurveyManager.getSurvey());
            int u = databaseManager.num_answers( "patient") ;
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("QuestionType", SurveyManager.getCurrentQuestionType());

        MainFragment fragment = new MainFragment();
        fragment.setArguments(bundle);

        ft.replace(R.id.container, fragment);
        ft.addToBackStack(null);
        ft.commit();

        TextView tvQuestion = findViewById(R.id.question);
        tvQuestion.setText(SurveyManager.getCurrentQuestionText());

    }

    private String getAnswer(View view) {
        String[] answers = SurveyManager.getCurrentQuestionAnswers();
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
        SurveyManager.restartSurvey();
        showQuestion();
    }

    public void grafics () {
        Intent intent = new Intent(this, GraficsActivity.class);
        startActivity(intent);

   }

}
