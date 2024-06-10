package com.prova.matchme.GestionePartita.Interfacce;


import com.prova.matchme.Entity.Campo;
import com.prova.matchme.Entity.Partita;
import com.prova.matchme.Entity.PartitaDetails;
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
	private PartitaDetails selectedPartitaDetails;
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

	public void ShowDetails(PartitaDetails partitaDetails){
		this.selectedPartitaDetails = partitaDetails;
		SedeField.setDisable(false);
		CampoField.setDisable(false);
		SportField.setDisable(false);
		DataOraField.setDisable(false);
		AggiungiGiocatoreButton.setDisable(false);
		AggiungiOspiteButton.setDisable(false);
		CancellaPrenotazioneButton.setDisable(false);
		InvitaGiocatoreButton.setDisable(false);
		Squadra1List.setDisable(false);
		Squadra2List.setDisable(false);
		SedeField.setText(partitaDetails.sede.toString());
		CampoField.setText(partitaDetails.campo.getNomecampo());
		SportField.setText(partitaDetails.campo.getSport());
		DataOraField.setText(partitaDetails.campo.getOrarioString());
		ObservableList<Utente> squadra1 = FXCollections.observableArrayList(partitaDetails.squadra1);
		Squadra1List.setItems(squadra1);
		ObservableList<Utente> squadra2 = FXCollections.observableArrayList(partitaDetails.squadra2);
		Squadra2List.setItems(squadra2);
	}

	public void ClickAggiungiGiocatori() {
		partitaCtrl.AggiungiClicked(selectedPartitaDetails);
	}

	public void ClickInvitaGiocatori() {

	}

	public void ClickAggiungiOspite() {
		partitaCtrl.AggiungiOspiteClicked(selectedPartitaDetails);
	}

	public void ClickCancellaPrenotazione() {

	}

	public void ShowBnd() {
		partitaCtrl.SelectedPartita(selectedPartita);
	}

	@FXML
	public void back() {
		this.partitaCtrl.toMain();
	}

}
