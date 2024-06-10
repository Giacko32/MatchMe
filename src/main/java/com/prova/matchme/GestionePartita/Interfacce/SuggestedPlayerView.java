package com.prova.matchme.GestionePartita.Interfacce;


import com.prova.matchme.Entity.PartitaDetails;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestionePartita.Controller.PartitaCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SuggestedPlayerView {

    private ArrayList<Utente> SuggestedPlayer = new ArrayList<>();
    private String buttons;
    private Utente selectedUtente;
    private PartitaCtrl partitaCtrl;
    private PartitaDetails selectedPartita;
    private Stage stage;
    @FXML
    private ListView<Utente> ListaUtenti;
    @FXML
    private Button buttonS1;
    @FXML
    private Button buttonS2;
    @FXML
    private TextField search;

    @FXML
    public void initialize() {
        ObservableList<Utente> listaUtenti = FXCollections.observableArrayList(SuggestedPlayer);
        ListaUtenti.setItems(listaUtenti);
        ListaUtenti.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
            selectedUtente = newvalue;
        });
        switch (buttons) {
            case "1" -> buttonS1.setDisable(false);
            case "2" -> buttonS2.setDisable(false);
            case "entrambe" -> {
                buttonS1.setDisable(false);
                buttonS2.setDisable(false);
            }
        }
    }


    public SuggestedPlayerView(ArrayList<Utente> listaSuggeriti, String buttons, PartitaCtrl partitaCtrl, PartitaDetails pd, Stage s) {
        this.SuggestedPlayer = listaSuggeriti;
        this.buttons = buttons;
        this.partitaCtrl = partitaCtrl;
        this.selectedPartita = pd;
        this.stage = s;
    }

    public void selectSuggestedUser() {

    }

    public void selectSquadra1() {
        partitaCtrl.passGiocatoreSquadra(selectedUtente, 1, selectedPartita, stage);
    }

    public void selectSquadra2(){
        partitaCtrl.passGiocatoreSquadra(selectedUtente, 2, selectedPartita, stage);
    }


    public void insertDati() {

    }

    public void ClickCerca() {
        partitaCtrl.passRicercaGiocatore(search.getText(), this, selectedPartita);
    }

    public void mostraLista(ArrayList<Utente> listaCercati) {
        ObservableList<Utente> lista = FXCollections.observableArrayList(listaCercati);
        ListaUtenti.setItems(lista);
        ListaUtenti.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
            selectedUtente = newvalue;
        });
    }

    public void ClickInvita() {

    }

}
