package com.prova.matchme.GestionePartita.Interfacce;

import com.prova.matchme.Entity.Partita;
import com.prova.matchme.Entity.PartitaDetails;
import com.prova.matchme.Entity.Sede;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestionePartita.Controller.PartitaCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class DetailsTuttePartiteView {

    private PartitaCtrl partitaCtrl;
    private ArrayList<Partita> partite;
    private Partita selectedPartita;
    private PartitaDetails selectedPartitaDetails;
    private Utente utente;
    @FXML
    private ListView<Partita> listaPartite;
    @FXML
    private ListView<Utente> Squadra1List;
    @FXML
    private ListView<Utente> Squadra2List;
    @FXML
    private TextField SedeField;
    @FXML
    private TextField CampoField;
    @FXML
    private TextField DataOraField;
    @FXML
    private TextField SportField;
    @FXML
    private Button iscrivitiButton;

    public DetailsTuttePartiteView(PartitaCtrl controller, ArrayList<Partita> listaPartite, Utente u) {
        this.partitaCtrl = controller;
        this.partite = listaPartite;
        this.utente = u;
    }

    @FXML
    public void initialize() {
        ObservableList<Partita> lista = FXCollections.observableArrayList(partite);
        listaPartite.setItems(lista);
        listaPartite.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldvalue, newvalue) -> {
            this.selectedPartita = newvalue;
            selectPartita();
        }));
    }

    public void selectPartita() {
        partitaCtrl.SelectedPartita(selectedPartita, false);
    }

    public void ClickPartecipa() {
        partitaCtrl.PartecipaClicked(selectedPartitaDetails);
    }

    public void ShowBnd(PartitaDetails partitaDetails) {
        this.selectedPartitaDetails = partitaDetails;
        if(selectedPartitaDetails != null) {
            SedeField.setDisable(false);
            CampoField.setDisable(false);
            SportField.setDisable(false);
            DataOraField.setDisable(false);
            Squadra1List.setDisable(false);
            Squadra2List.setDisable(false);
            if(partitaDetails.squadra1.contains(utente) || partitaDetails.squadra2.contains(utente)) {
                iscrivitiButton.setDisable(true);
            } else {
                iscrivitiButton.setDisable(false);
            }
            SedeField.setText(partitaDetails.sede.toString());
            CampoField.setText(partitaDetails.campo.getNomecampo());
            SportField.setText(partitaDetails.campo.getSport());
            DataOraField.setText(partitaDetails.campo.getOrarioString());
            ObservableList<Utente> squadra1 = FXCollections.observableArrayList(partitaDetails.squadra1);
            Squadra1List.setItems(squadra1);
            ObservableList<Utente> squadra2 = FXCollections.observableArrayList(partitaDetails.squadra2);
            Squadra2List.setItems(squadra2);
        } else {
            SedeField.setDisable(true);
            SedeField.clear();
            CampoField.setDisable(true);
            CampoField.clear();
            SportField.setDisable(true);
            SportField.clear();
            DataOraField.setDisable(true);
            DataOraField.clear();
            iscrivitiButton.setDisable(true);
            Squadra1List.setDisable(true);
            Squadra2List.setDisable(true);
            ObservableList<Utente> squadra1 = FXCollections.observableArrayList(FXCollections.emptyObservableList());
            Squadra1List.setItems(squadra1);
            ObservableList<Utente> squadra2 = FXCollections.observableArrayList(FXCollections.emptyObservableList());
            Squadra2List.setItems(squadra2);
        }
    }

    @FXML
    public void back() {
        this.partitaCtrl.toMain();
    }

}
