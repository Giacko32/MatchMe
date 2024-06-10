package com.prova.matchme.GestioneSede.Interfacce;

import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneSede.Controller.AmministrazioneSedeCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class SearchNonTesseratoView {

	private AmministrazioneSedeCtrl amministrazioneSedeCtrl;

	public SearchNonTesseratoView(AmministrazioneSedeCtrl amministrazioneSedeCtrl){
		this.amministrazioneSedeCtrl=amministrazioneSedeCtrl;
	}
	@FXML
	public ListView<Utente> listanontesserati;
	public Button aggiungi;
	public TextField parametri;

	@FXML
	public void back(){
		amministrazioneSedeCtrl.toAdmin();
	}


	@FXML
	public void ClickCerca() {
		this.amministrazioneSedeCtrl.passSearchParameter(parametri.getText());
	}
    @FXML
	public void mostraListaNonTesserati(ArrayList<Utente> lista) {
		ObservableList<Utente> items = FXCollections.observableArrayList(lista);
		listanontesserati.setItems(items);
		aggiungi.setDisable(false);
	}


	@FXML
	public void ClickAggiungiAbbonamento() {
		this.amministrazioneSedeCtrl.PassNonTesserato(listanontesserati.getSelectionModel().getSelectedItem());
	}

}
