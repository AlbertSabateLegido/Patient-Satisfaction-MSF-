package frontieres.sans.medecins.patientsatisfaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Survey {

    private Integer surveyId;
    private List<String> answeredQuestions;
    private Date date;

    public Survey() {
        surveyId = UUID.randomUUID().hashCode();
        answeredQuestions = new ArrayList<>();
        date = new Date();
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    public List<String> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void add(String question,String answer) {
        answeredQuestions.add(question);
        answeredQuestions.add(answer);
    }

    public void restartSurvey() {
        surveyId = UUID.randomUUID().hashCode();
        answeredQuestions = new ArrayList<>();
        date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
