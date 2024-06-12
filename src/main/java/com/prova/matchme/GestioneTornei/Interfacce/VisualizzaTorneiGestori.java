package com.prova.matchme.GestioneTornei.Interfacce;


import com.prova.matchme.Entity.Torneo;
import com.prova.matchme.GestioneTornei.Controller.AmministrazioneTorneiCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;

public class VisualizzaTorneiGestori {

	private ArrayList<Torneo> tornei;
	private Stage stage;
	private AmministrazioneTorneiCtrl amminCtrl;

	private Torneo selectedTorneo;
	@FXML
	private ListView<Torneo> listaTorneiSede;

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
		});
	}

	public void clickModificaTorneo() {

	}

	public void clickCancellaTorneo() {

	}

	public void clickSquadreAttesa() {

	}

	public void showDettagliTorneo() {

	}

}
