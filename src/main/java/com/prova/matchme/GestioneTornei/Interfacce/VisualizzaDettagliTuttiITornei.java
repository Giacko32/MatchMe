package com.prova.matchme.GestioneTornei.Interfacce;


import com.prova.matchme.Entity.Torneo;
import com.prova.matchme.GestioneTornei.Controller.TorneiCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class VisualizzaDettagliTuttiITornei {

	private Torneo selectedTorneo;
	private ArrayList<Torneo> listaTornei;
	@FXML
	private ListView<Torneo> ListaTuttiTornei;
	private TorneiCtrl controller;

	public VisualizzaDettagliTuttiITornei(ArrayList<Torneo> lista, TorneiCtrl controller){

		this.listaTornei = lista;
		this.controller = controller;
	}

	@FXML
	public void initialize(){
		ObservableList<Torneo> torneolist = FXCollections.observableArrayList(listaTornei);
		ListaTuttiTornei.setItems(torneolist);
		ListaTuttiTornei.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
			selectedTorneo = newvalue;
		});
	}
	public void ClickIscriviSquadra() {

	}

	@FXML
	public void back() {
		this.controller.toMain();
	}

}
