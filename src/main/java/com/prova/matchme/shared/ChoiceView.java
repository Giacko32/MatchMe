package com.prova.matchme.shared;

import com.prova.matchme.Entity.Notifica;
import com.prova.matchme.GestioneNotifiche.Controller.NotifyCtrl;
import com.prova.matchme.GestioneSede.Controller.GestionePartiteSedeCtrl;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ChoiceView {


    private NotifyCtrl notifyCtrl;
    private Stage s;
    private GestionePartiteSedeCtrl gestionePartiteSedeCtrl;

    public ChoiceView(NotifyCtrl notifyCtrl, Stage s) {
        this.s = s;
        this.notifyCtrl = notifyCtrl;
    }

    public ChoiceView(GestionePartiteSedeCtrl gestionePartiteSedeCtrl, Stage s) {
        this.s = s;
        this.gestionePartiteSedeCtrl = gestionePartiteSedeCtrl;
    }

    @FXML
    public void clickAccetta() {
        if (notifyCtrl != null) {
            this.notifyCtrl.inviaEsito(true);
            s.close();
        }
        if(gestionePartiteSedeCtrl!=null){
            gestionePartiteSedeCtrl.PassSquadraVincitrice(1);
            s.close();
        }


    }

    @FXML
    public void clickRifiuta() {
        if (notifyCtrl != null) {
            this.notifyCtrl.inviaEsito(false);
            s.close();
        }
        if(gestionePartiteSedeCtrl!=null){
            gestionePartiteSedeCtrl.PassSquadraVincitrice(2);
            s.close();
        }
    }


}
