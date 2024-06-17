package com.prova.matchme.GestioneTornei.Interfacce;

import com.prova.matchme.GestioneTornei.Controller.TorneiCtrl;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class SelezioneTorneiView {

    private TorneiCtrl torneiCtrl;
    private Stage stage;

    public SelezioneTorneiView(TorneiCtrl tc, Stage stage){
        this.torneiCtrl = tc;
        this.stage = stage;
    }
    @FXML
    public void ClickIMieiTornei() {
        torneiCtrl.MieiTornei();
        stage.close();
    }
    @FXML
    public void ClickTuttiITornei() {
        torneiCtrl.TuttiItornei();
        stage.close();
    }


}
