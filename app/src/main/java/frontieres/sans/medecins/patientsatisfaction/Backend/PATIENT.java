package frontieres.sans.medecins.patientsatisfaction.Backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home on 18/04/2018.
 */

public class PATIENT {
    int Pateint_Id ;
    List<PATIENT_ANSWER> Pateint_Answers ;
    database DataBase;

    public PATIENT (database db ) {
        DataBase = db ;
        Pateint_Id = db.Next_Id_Patient() ;
        Pateint_Answers = new ArrayList<>();
    }

    public void ADD_ANSWER (int num , String text) {
        Pateint_Answers.add(new PATIENT_ANSWER(num , text)) ;
    }

    public boolean ADD_DB () {

      for (int i = 0 ;Pateint_Answers.contains(i) ; i++ ){
            boolean bd_execute = DataBase.insertData1(Pateint_Id, Pateint_Answers.get(i).Get_AnswerID(), Pateint_Answers.get(i).Get_AnswerTtext());
          if (bd_execute == false) return false ;
      }
        return true ;
    }
}
