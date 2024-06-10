package com.prova.matchme.GestioneSede.Interfacce;


import com.prova.matchme.Entity.Chat;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneSede.Controller.AmministrazioneSedeCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.swing.plaf.basic.BasicButtonUI;
import java.util.ArrayList;

public class SearchUtentiView {

	@FXML
	public ListView<Utente> listautenti;
	public TextField testo;
	public Button abilita;

	private AmministrazioneSedeCtrl amministrazioneSedeCtrl;

	public SearchUtentiView(AmministrazioneSedeCtrl amministrazioneSedeCtrl){
		this.amministrazioneSedeCtrl=amministrazioneSedeCtrl;
	}



	@FXML
	public void ClickCerca() {
		amministrazioneSedeCtrl.passSearchField(testo.getText());
	}

	@FXML
	public void mostraListaTesserati(ArrayList<Utente> lista) {
		ObservableList<Utente> items = FXCollections.observableArrayList(lista);
		listautenti.setItems(items);
		abilita.setDisable(false);

	}

	@FXML
	public void ClickAbilita() {
		this.amministrazioneSedeCtrl.passNewAllenatore(listautenti.getSelectionModel().getSelectedItem());
	}

	@FXML
	public void back(){
		amministrazioneSedeCtrl.toAdmin();
	}

}
