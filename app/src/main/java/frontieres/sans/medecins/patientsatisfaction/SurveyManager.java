package frontieres.sans.medecins.patientsatisfaction;

import java.util.ArrayList;
import java.util.List;


public class SurveyManager {

    static List<String> survey;
    static List<Question> questionList;
    static Integer currentIdQuestion;

    //public static String SMILEY        = "smiley";
    public static String SMILEY_RATING = "smiley_rating";
    public static String TWO_BUTTONS   = "two_buttons";
    public static String THREE_BUTTONS = "three_buttons";
    public static String FOUR_BUTTONS  = "four_buttons";

    public static void createQuestions() {
        questionList = new ArrayList<>();
        survey = new ArrayList<>();
        String  question;
        String[] answers;

        //initial question
        question = "Are you a patient or a doctor?";
        answers = new String[] {"patient", "doctor"};
        questionList.add(new Question(0, TWO_BUTTONS,question,answers, new Integer[] {1,2}));

        question = "Are you satisfied";
        questionList.add(new Question(1,SMILEY_RATING,question,null,null));

        question = "What is your specialty";
        answers = new String[] {"surgery","oculist","traumatologist","dermatologist"};
        questionList.add(new Question(2,FOUR_BUTTONS,question,answers,null));

        currentIdQuestion = 0;
    }

    public static String getCurrentQuestionType() {
        return questionList.get(currentIdQuestion).getQuestionType();
    }

    public static String getCurrentQuestionText() {
        return questionList.get(currentIdQuestion).getQuestionText();
    }

    public static String[] getCurrentQuestionAnswers() {
        return questionList.get(currentIdQuestion).getAnswersText();
    }

    public static void nextQuestion(String answer) {
        survey.add(questionList.get(currentIdQuestion).getQuestionText());
        survey.add(answer);

        Integer[] nextId = questionList.get(currentIdQuestion).getNextId();
        if(nextId == null) {
            currentIdQuestion = null;
            return;
        }
        if(nextId.length == 1) {
            currentIdQuestion = nextId[0];
            return;
        }
        String[] answers = questionList.get(currentIdQuestion).getAnswersText();
        for(int i = 0; i < answers.length; ++i) {
            if(answer.equals(answers[i])) {
                currentIdQuestion = nextId[i];
                return;
            }
        }
    }

    public static void restartQuestionary() {
        currentIdQuestion = 0;
        survey = new ArrayList<>();
    }

    public static List<String> getSurvey() {
        return survey;
    }
}
