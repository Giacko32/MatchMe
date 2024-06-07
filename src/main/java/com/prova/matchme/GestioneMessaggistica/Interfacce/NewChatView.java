package com.prova.matchme.GestioneMessaggistica.Interfacce;

import com.prova.matchme.Entity.Chat;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneMessaggistica.Controller.ChatCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class NewChatView {

	private ChatCtrl chatCtrl;
	public NewChatView(ChatCtrl chatCtrl){
		this.chatCtrl=chatCtrl;
	}

	@FXML
	public void back(){
		this.chatCtrl.toMain();
	}

	@FXML
	public TextField nome;
	public TextField cognome;
	public Button cerca;
	public Button crea;
	public ListView<Utente> listautenti;

	@FXML
	public void clickCerca() {
		this.chatCtrl.searchUser(nome.getText(),cognome.getText());
	}
    @FXML
	public void updateLista(ArrayList<Utente> listautent) {
		ObservableList<Utente> items = FXCollections.observableArrayList(listautent);
		listautenti.setItems(items);
		crea.setDisable(false);
	}

    @FXML
	public void clickCreaChat() {
		Utente selectedItem = listautenti.getSelectionModel().getSelectedItem();
		this.chatCtrl.createNewChat(selectedItem);
	}

}
