package com.prova.matchme.GestioneTornei.Interfacce;

import com.prova.matchme.GestioneTornei.Controller.AmministrazioneTorneiCtrl;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class CreaTorneoView {

    private AmministrazioneTorneiCtrl amminCtrl;
    private Stage stage;

    public CreaTorneoView(Stage stage, AmministrazioneTorneiCtrl amminCtrl ) {
        this.stage = stage;
        this.amminCtrl = amminCtrl;
    }

    @FXML
    public void back() {
        this.amminCtrl.toMain();
    }

}
