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
        questionList.add(new Question(0, TWO_BUTTONS,question,answers, new Integer[] {1,2}));

        question = "Are you satisfied";
        answers = new String[] {"Absolutly","Not at all","No"};
        questionList.add(new Question(1,THREE_BUTTONS,question,answers,null));

        question = "What is your specialty";
        answers = new String[] {"surgery","oculist","traumatologist","dermatologist"};
        questionList.add(new Question(2,FOUR_BUTTONS,question,answers,null));

        currentIdQuestion = 0;
    }

    static String getCurrentQuestionType() {
        return questionList.get(currentIdQuestion).getQuestionType();
    }

    static String getCurrentQuestionText() {
        return questionList.get(currentIdQuestion).getQuestionText();
    }

    static String[] getCurrentQuestionAnswers() {
        return questionList.get(currentIdQuestion).getAnswersText();
    }

    static void nextQuestion(String answer) {
        Integer[] nextId = questionList.get(currentIdQuestion).getNextId();
        if(nextId == null) {
            currentIdQuestion = null;
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
}
