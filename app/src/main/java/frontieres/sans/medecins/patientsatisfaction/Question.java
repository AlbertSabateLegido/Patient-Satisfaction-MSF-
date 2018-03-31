package frontieres.sans.medecins.patientsatisfaction;

import java.util.ArrayList;

public class Question {

    Integer    id;
    String     qType;
    String     qText;
    String[]   aText;
    Integer[]  nextId;

    public Question(Integer id, String qType, String qText, String[] aText, Integer[] nextId) {
        this.id     = id;
        this.qType  = qType;
        this.qText  = qText;
        this.aText  = aText;
        this.nextId = nextId;
    }

    public String getQuestionType() {
        return qType;
    }

    public String getQuestionText() {
        return qText;
    }

    public String[] getAnswersText() {
        return aText;
    }

    public Integer[] getNextId() {
        return nextId;
    }
}
