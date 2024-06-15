package com.prova.matchme.GestionePartita.Interfacce;

import com.prova.matchme.Entity.Partita;
import com.prova.matchme.Entity.PartitaDetails;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestionePartita.Controller.PartitaCtrl;
import jakarta.mail.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class BonusView {
	private PartitaCtrl partitaCtrl;
	private ArrayList<Partita> listaPartite;
	private Partita selectedPartita;
	private Utente selectedUtente;
	private PartitaDetails selectedPartitaDetails;
	@FXML
	private ListView<Utente> Squadra1List;
	@FXML
	private ListView<Utente> Squadra2List;
	@FXML
	private ListView<Partita> lista;
	@FXML
	private Button bonusButton;

	public BonusView(PartitaCtrl partitaCtrl, ArrayList<Partita> partite){
		this.partitaCtrl = partitaCtrl;
		this.listaPartite = partite;
	}

	@FXML
	public void initialize(){
		ObservableList<Partita> listapartite = FXCollections.observableArrayList(listaPartite);
		lista.setItems(listapartite);
		lista.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldvalue, newvalue) -> {
			this.selectedPartita = newvalue;
			selectPartita();
		}));
	}

	public void selectPartita() {
		Squadra1List.setDisable(false);
		Squadra2List.setDisable(false);
		selectedPartitaDetails = partitaCtrl.passPartita(selectedPartita, this);
		ObservableList<Utente> squadra1 = FXCollections.observableArrayList(selectedPartitaDetails.squadra1);
		Squadra1List.setItems(squadra1);
		ObservableList<Utente> squadra2 = FXCollections.observableArrayList(selectedPartitaDetails.squadra2);
		Squadra2List.setItems(squadra2);
		Squadra1List.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldvalue, newvalue) -> {
			this.selectedUtente = newvalue;
			bonusButton.setDisable(false);
		}));
		Squadra2List.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldvalue, newvalue) -> {
			this.selectedUtente = newvalue;
			bonusButton.setDisable(false);
		}));
	}

	public void assegnaBonus(){
		partitaCtrl.passGiocatoreBonus(selectedUtente, 0.25F, this);
	}

	public void ShowBnd() {
		Squadra1List.setItems(FXCollections.emptyObservableList());
		Squadra2List.setItems(FXCollections.emptyObservableList());
		Squadra1List.setDisable(true);
		Squadra2List.setDisable(true);
		bonusButton.setDisable(true);
	}

	public void goBack(){
		partitaCtrl.toMain();
	}

}
