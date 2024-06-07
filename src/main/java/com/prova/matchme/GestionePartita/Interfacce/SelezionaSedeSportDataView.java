package com.prova.matchme.GestionePartita.Interfacce;


import com.prova.matchme.Entity.Sede;
import com.prova.matchme.GestionePartita.Controller.PartitaCtrl;
import com.prova.matchme.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SelezionaSedeSportDataView {

    private ArrayList<Sede> listaSedi;
    private PartitaCtrl partitaCtrl;
    private Sede sedeSelected;
    private String sportSelected;
    private Stage stage;

    @FXML
    private ChoiceBox<Sede> scegliSede;

    @FXML
    private ChoiceBox<String> scegliSport;

    @FXML
    private DatePicker selezionaData;

    public SelezionaSedeSportDataView(ArrayList<Sede> sedi, PartitaCtrl partitactrl, Stage s) {
        this.listaSedi = sedi;
        this.partitaCtrl = partitactrl;
        this.stage = s;
    }

    @FXML
    public void initialize() {
        ObservableList<Sede> listaCBsedi = FXCollections.observableArrayList(listaSedi);
        scegliSede.setItems(listaCBsedi);
        ObservableList<String> listaCBSport = FXCollections.observableArrayList("Calcio a 5", "Padel singolo", "Padel doppio", "Tennis singolo", "Tennis doppio");
        scegliSport.setItems(listaCBSport);
        scegliSede.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldvalue, newvalue) -> {
            this.sedeSelected = newvalue;
        }));
        scegliSport.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldvalue, newvalue) -> {
            this.sportSelected = newvalue;
        }));
    }

    public void ClickCerca() {
        if (sedeSelected == null || sportSelected == null || selezionaData.getValue() == null) {
            Utils.creaPannelloErrore("Ci sono dei campi vuoti");
        } else {
            partitaCtrl.passSedeSportData(sedeSelected, sportSelected, selezionaData.getValue().atTime(0, 0), stage);
        }
    }

}
