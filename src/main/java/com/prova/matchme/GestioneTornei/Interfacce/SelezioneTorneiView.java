package com.prova.matchme.GestioneTornei.Interfacce;

import com.prova.matchme.GestioneTornei.Controller.TorneiCtrl;
import javafx.stage.Stage;

public class SelezioneTorneiView {

    private TorneiCtrl torneiCtrl;
    private Stage stage;

    public SelezioneTorneiView(TorneiCtrl tc, Stage stage){
        this.torneiCtrl = tc;
        this.stage = stage;
    }

    public void ClickIMieiTornei() {
        torneiCtrl.MieiTornei();
        stage.close();
    }

    public void ClickTuttiITornei() {
        torneiCtrl.TuttiItornei();
        stage.close();
    }


}
