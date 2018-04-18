package frontieres.sans.medecins.patientsatisfaction.Backend;

/**
 * Created by Home on 18/04/2018.
 */

public class PATIENT_ANSWER {
    int ID ;
    String ANSWER ;


    public PATIENT_ANSWER (int id , String answer){
        ID = id ;
        ANSWER = answer ;
    }

    public String Get_AnswerTtext (){return ANSWER ;}

    public int Get_AnswerID (){return ID ;}
}
