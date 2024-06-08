package com.prova.matchme.GestioneNotifiche.Interfacce;


import com.prova.matchme.Entity.Chat;
import com.prova.matchme.Entity.Notifica;
import com.prova.matchme.GestioneNotifiche.Controller.NotifyCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;

public class NotifyView {


    private Stage stage;
    private ArrayList<Notifica> listanotifiche;
    private NotifyCtrl notifyCtrl;

    public NotifyView(Stage s, ArrayList<Notifica> listanotifiche, NotifyCtrl notifyCtrl) {
        this.stage = s;
        this.listanotifiche = listanotifiche;
        this.notifyCtrl = notifyCtrl;
    }


    @FXML
    public ListView<Notifica> lista;
    public Button cancella;

    @FXML
    public void initialize() {
        ObservableList<Notifica> items = FXCollections.observableArrayList(listanotifiche);
        lista.setCellFactory(param -> new NotifyListCell());
        lista.setItems(items);
        lista.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cancella.setDisable(false);
                if (newValue.getTipo() == 0) {
                    this.notifyCtrl.rispostaInvito(newValue);
                }
                if(newValue.getTipo()== 2){
                    this.notifyCtrl.rispostaAccettazione(newValue);
                }
            }
        });
    }

    @FXML
    public void clickDelete() {
        notifyCtrl.cancellaNotifica(lista.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void rimuoviNotificaCancellata(Notifica notifica) {
        listanotifiche.remove(notifica);
        ObservableList<Notifica> items = FXCollections.observableArrayList(listanotifiche);
        lista.setCellFactory(param -> new NotifyListCell());
        lista.setItems(items);
        lista.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getTipo() == 0) {
                this.notifyCtrl.rispostaInvito(newValue);
            }
        });
    }

    public void selectNotificaAccettazione() {

    }

    @FXML
    public void back(){
        this.notifyCtrl.toMain();
    }
    public void ShowBnd() {

    }

}
