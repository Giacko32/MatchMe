package com.prova.matchme.GestionePartita.Interfacce;

import com.prova.matchme.Entity.Partita;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestionePartita.Controller.PartitaCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class DetailsTuttePartiteView {

    private PartitaCtrl partitaCtrl;
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

    public DetailsTuttePartiteView(PartitaCtrl controller) {
        this.partitaCtrl = controller;
    }

    @FXML
    public void initialize() {

    }

    public void ClickPartecipa() {

    }


    public void ShowBnd() {

    }

    @FXML
    public void back() {
        this.partitaCtrl.toMain();
    }

}
