package com.prova.matchme.GestioneTornei.Interfacce;

import com.prova.matchme.Entity.Sede;
import com.prova.matchme.Entity.Torneo;
import com.prova.matchme.GestioneTornei.Controller.TorneiCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class VisualizzaDettagliMioTorneo {
	private Torneo selectedTorneo;
	private ArrayList<Torneo> listaTornei;
	@FXML
	private ListView<Torneo> ListaMieTornei;
	private TorneiCtrl controller;

	@FXML
	private TextField sport;
	@FXML
	private TextField maxSquadre;
	@FXML
	private TextField maxGiocatoriSquadre;
	@FXML
	private Button buttonCancellaSquadra;


	public VisualizzaDettagliMioTorneo(ArrayList<Torneo> lista, TorneiCtrl controller){

		this.listaTornei = lista;
		this.controller = controller;
	}

	@FXML
	public void initialize(){
		ObservableList<Torneo> torneolist = FXCollections.observableArrayList(listaTornei);
		ListaMieTornei.setItems(torneolist);
		ListaMieTornei.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
			selectedTorneo = newvalue;
			this.SelectTorneo();
		});
	}
	public void ClickCancellaSquadra() {
		controller.passCancellazione(selectedTorneo);

	}

	public void SelectTorneo(){
		controller.TorneoSelezionatoMio(selectedTorneo);
	}

	public void showDetails(Torneo torneo){
		sport.setDisable(false);
		maxSquadre.setDisable(false);
		maxGiocatoriSquadre.setDisable(false);
		sport.setText(torneo.getSport());
		maxSquadre.setText(String.valueOf(torneo.getN_Squadre()));
		maxGiocatoriSquadre.setText(String.valueOf(torneo.getN_Giocatori_squadra()));
		buttonCancellaSquadra.setDisable(false);
	}

	@FXML
	public void back() {
		this.controller.toMain();
	}

}
