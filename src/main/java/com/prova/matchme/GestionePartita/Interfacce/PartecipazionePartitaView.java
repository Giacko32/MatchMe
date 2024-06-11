package com.prova.matchme.GestionePartita.Interfacce;


import com.prova.matchme.Entity.PartitaDetails;
import com.prova.matchme.GestionePartita.Controller.PartitaCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PartecipazionePartitaView {

    private Stage stage;
    private PartitaCtrl partitaCtrl;
    private PartitaDetails partitaDetails;
    @FXML
    public Button buttonS1;
    public Button buttonS2;

    public PartecipazionePartitaView(Stage stg, PartitaCtrl partitaCtrl, PartitaDetails partitaDetails) {
        this.stage = stg;
        this.partitaCtrl = partitaCtrl;
        this.partitaDetails = partitaDetails;
    }

    @FXML
    public void initialize() {
        if (partitaDetails.campo.getSport().equals("Calcio a 5")) {
            if (partitaDetails.squadra1.size() == 5) {
                buttonS1.setDisable(true);
            }
            if (partitaDetails.squadra2.size() == 5) {
                buttonS2.setDisable(true);
            }
        }
        if (partitaDetails.campo.getSport().equals("Tennis singolo") || partitaDetails.campo.getSport().equals("Padel singolo")) {
            if (partitaDetails.squadra1.size() == 1) {
                buttonS1.setDisable(true);
            }
            if (partitaDetails.squadra2.size() == 1) {
                buttonS2.setDisable(true);
            }
        }
        if (partitaDetails.campo.getSport().equals("Tennis doppio") || partitaDetails.campo.getSport().equals("Padel doppio")) {
            if (partitaDetails.squadra1.size() == 2) {
                buttonS1.setDisable(true);
            }
            if (partitaDetails.squadra2.size() == 2) {
                buttonS2.setDisable(true);
            }
        }
    }

    public void selectSquadra1() {
        partitaCtrl.passSquadra(1, partitaDetails, stage);
    }

    public void selectSquadra2() {
        partitaCtrl.passSquadra(2, partitaDetails, stage);
    }

}
