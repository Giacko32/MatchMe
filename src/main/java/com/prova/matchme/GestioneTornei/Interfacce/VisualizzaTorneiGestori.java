package com.prova.matchme.GestioneTornei.Interfacce;


import com.prova.matchme.Entity.Torneo;
import com.prova.matchme.GestioneTornei.Controller.AmministrazioneTorneiCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class VisualizzaTorneiGestori {

	private ArrayList<Torneo> tornei;
	private Stage stage;
	private AmministrazioneTorneiCtrl amminCtrl;

	private Torneo selectedTorneo;
	@FXML
	private ListView<Torneo> listaTorneiSede;

	@FXML
	private TextField sport;
	@FXML
	private TextField maxSquadre;
	@FXML
	private TextField maxGiocatoriSquadre;
	@FXML
	private Button buttonSquadreAttesa;
	@FXML
	private Button buttonCancellaTorneo;
	@FXML
	private Button buttonModificaTorneo;

	public VisualizzaTorneiGestori(ArrayList<Torneo> tornei, Stage stage, AmministrazioneTorneiCtrl amminCtrl){
		this.tornei = tornei;
		this.stage = stage;
		this.amminCtrl = amminCtrl;
	}

	@FXML
	public void initialize(){
		ObservableList<Torneo> torneolist = FXCollections.observableArrayList(tornei);
		listaTorneiSede.setItems(torneolist);
		listaTorneiSede.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
			selectedTorneo = newvalue;
			amminCtrl.TorneoSelezionato(selectedTorneo);
		});
	}

	public void clickNuovoTorneo(){
		amminCtrl.NuovoTorneo(stage);
	}

	public void clickModificaTorneo() {
		amminCtrl.ModificaTorneo(selectedTorneo, stage);
	}

	public void clickCancellaTorneo() {
		amminCtrl.CancellaTorneo(selectedTorneo);
	}

	public void clickSquadreAttesa() {

	}

	public void showDettagliTorneo(Torneo torneo) {
		sport.setDisable(false);
		maxSquadre.setDisable(false);
		maxGiocatoriSquadre.setDisable(false);
		sport.setText(torneo.getSport());
		maxSquadre.setText(String.valueOf(torneo.getN_Squadre()));
		maxGiocatoriSquadre.setText(String.valueOf(torneo.getN_Giocatori_squadra()));
		buttonCancellaTorneo.setDisable(false);
		buttonSquadreAttesa.setDisable(false);
		buttonModificaTorneo.setDisable(false);


	}

	@FXML
	public void back() {
		this.amminCtrl.toMain();
	}

}
