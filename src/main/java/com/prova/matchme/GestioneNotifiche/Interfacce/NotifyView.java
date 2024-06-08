package com.prova.matchme.GestioneNotifiche.Interfacce;


import com.prova.matchme.Entity.Chat;
import com.prova.matchme.Entity.Notifica;
import com.prova.matchme.GestioneNotifiche.Controller.NotifyCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;

public class NotifyView {


	private Stage stage;
	private ArrayList<Notifica> listanotifiche;
	private NotifyCtrl notifyCtrl;

	public NotifyView(Stage s, ArrayList<Notifica> listanotifiche, NotifyCtrl notifyCtrl){
		this.stage=s;
		this.listanotifiche=listanotifiche;
		this.notifyCtrl=notifyCtrl;
	}


	@FXML
	public ListView<Notifica> lista;

    @FXML
	public void initialize(){
		ObservableList<Notifica> items = FXCollections.observableArrayList(listanotifiche);
		lista.setCellFactory(param -> new NotifyListCell());
		lista.setItems(items);
		lista.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.getTipo()==0){
				this.notifyCtrl.rispostaInvito(newValue.toString());
			}
		});
	}

	public void clickDelete() {

	}

	public void rimuoviNotificaCancellata() {

	}

	public void selectNotificaAccettazione() {

	}

	public void ShowBnd() {

	}

}
