package com.prova.matchme.GestionePartita.Interfacce;


import com.prova.matchme.Entity.Campo;
import com.prova.matchme.Entity.Partita;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestionePartita.Controller.PartitaCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class DetailsMiaPartitaView {

	private Partita selectedPartita;
	private ArrayList<Partita> listaPartite;
	@FXML
	private ListView<Partita> ListaMiePartite;
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
	private Button AggiungiGiocatoreButton;
	@FXML
	private Button InvitaGiocatoreButton;
	@FXML
	private Button AggiungiOspiteButton;
	@FXML
	private Button CancellaPrenotazioneButton;
	private PartitaCtrl partitaCtrl;

	public DetailsMiaPartitaView(ArrayList<Partita> lista, PartitaCtrl controller){

		this.listaPartite = lista;
		this.partitaCtrl = controller;
	}

	@FXML
	public void initialize(){
		ObservableList<Partita> partitelist = FXCollections.observableArrayList(listaPartite);
		ListaMiePartite.setItems(partitelist);
		ListaMiePartite.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
			selectedPartita = newvalue;
			this.selectPartita();
		});
	}

	public void selectPartita(){
		partitaCtrl.SelectedPartita(selectedPartita);
	}

	public void ShowDetails(ArrayList<Object> sedecampo){
		SedeField.setDisable(false);
		CampoField.setDisable(false);
		SportField.setDisable(false);
		DataOraField.setDisable(false);
		AggiungiGiocatoreButton.setDisable(false);
		AggiungiOspiteButton.setDisable(false);
		CancellaPrenotazioneButton.setDisable(false);
		InvitaGiocatoreButton.setDisable(false);

	}

	public void ClickAggiungiGiocatori() {

	}

	public void ClickInvitaGiocatori() {

	}

	public void ClickAggiungiOspite() {

	}

	public void ClickCancellaPrenotazione() {

	}

	public void ShowBnd() {

	}

	@FXML
	public void back() {
		this.partitaCtrl.toMain();
	}

}
