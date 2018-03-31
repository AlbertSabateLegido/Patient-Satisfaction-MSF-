package frontieres.sans.medecins.patientsatisfaction;

import java.util.ArrayList;
import java.util.List;


public class QuestionManager {

    static List<Question> questionList;
    static Integer currentIdQuestion;

    public static String EMOTICONS     = "emoticons";
    public static String TWO_BUTTONS   = "two_buttons";
    public static String THREE_BUTTONS = "three_buttons";
    public static String FOUR_BUTTONS  = "four_buttons";

    static void createQuestions() {
        questionList = new ArrayList<Question>();
        String  question;
        String[] answers;

        //initial question
        question = "Are you a patient or a doctor?";
        answers = new String[] {"patient", "doctor"};
        questionList.add(new Question(0, TWO_BUTTONS,question,answers,null));

        currentIdQuestion = 0;
    }

    static String getCurrentQuestionType() {
        return questionList.get(currentIdQuestion).getQuestionType();
    }

    static String getCurrentQuestionText() {
        return questionList.get(currentIdQuestion).getQuestionText();
    }

    public static String[] getCurrentQuestionAnswers() {
        return questionList.get(currentIdQuestion).getAnswersText();
    }
}
