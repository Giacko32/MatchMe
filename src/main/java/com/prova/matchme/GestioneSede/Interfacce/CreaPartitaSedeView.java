package com.prova.matchme.GestioneSede.Interfacce;


import com.prova.matchme.GestioneSede.Controller.GestionePartiteSedeCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class CreaPartitaSedeView {

    private GestionePartiteSedeCtrl gestionePartiteSedeCtrl;
    private Stage s;

    public CreaPartitaSedeView(GestionePartiteSedeCtrl gestionePartiteSedeCtrl, Stage s) {
        this.s = s;
        this.gestionePartiteSedeCtrl = gestionePartiteSedeCtrl;
    }

    @FXML
    public DatePicker data;

    @FXML
    public void ClickCercaCampi() {
        if (data.getValue() != null) {
            gestionePartiteSedeCtrl.PassDate(data.getValue());
            s.close();
        }
    }

}
