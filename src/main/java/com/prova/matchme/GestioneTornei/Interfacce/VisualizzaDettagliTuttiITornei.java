package com.prova.matchme.GestioneTornei.Interfacce;


import com.prova.matchme.Entity.Torneo;
import com.prova.matchme.GestioneTornei.Controller.TorneiCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class VisualizzaDettagliTuttiITornei {

	private Torneo selectedTorneo;
	private ArrayList<Torneo> listaTornei;
	@FXML
	private ListView<Torneo> ListaTuttiTornei;
	private TorneiCtrl controller;
	private Stage stage;

	@FXML
	private TextField sport;
	@FXML
	private TextField maxSquadre;
	@FXML
	private TextField maxGiocatoriSquadre;
	@FXML
	private Button buttonIscriviSquadra;

	public VisualizzaDettagliTuttiITornei(ArrayList<Torneo> lista, TorneiCtrl controller, Stage stage){

		this.listaTornei = lista;
		this.controller = controller;
		this.stage = stage;
	}

	@FXML
	public void initialize(){
		ObservableList<Torneo> torneolist = FXCollections.observableArrayList(listaTornei);
		ListaTuttiTornei.setItems(torneolist);
		ListaTuttiTornei.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
			selectedTorneo = newvalue;
			this.SelectTorneo();
		});
	}
	public void ClickIscriviSquadra() {
		controller.IscriviSelezionato(selectedTorneo, stage);
	}
	public void SelectTorneo(){
		controller.TorneoSelezionatoTutti(selectedTorneo);
	}

	public void showDetails(Torneo torneo){
		sport.setDisable(false);
		maxSquadre.setDisable(false);
		maxGiocatoriSquadre.setDisable(false);
		sport.setText(torneo.getSport());
		maxSquadre.setText(String.valueOf(torneo.getN_Squadre()));
		maxGiocatoriSquadre.setText(String.valueOf(torneo.getN_Giocatori_squadra()));
		buttonIscriviSquadra.setDisable(false);
	}

	@FXML
	public void back() {
		this.controller.toMain();
	}




}
