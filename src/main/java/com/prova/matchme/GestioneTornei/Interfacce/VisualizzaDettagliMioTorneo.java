package com.prova.matchme.GestioneTornei.Interfacce;

import com.prova.matchme.Entity.Torneo;
import com.prova.matchme.GestioneTornei.Controller.TorneiCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class VisualizzaDettagliMioTorneo {
	private Torneo selectedTorneo;
	private ArrayList<Torneo> listaTornei;
	@FXML
	private ListView<Torneo> ListaMieTornei;
	private TorneiCtrl controller;

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
		});
	}
	public void ClickCancellaSquadra() {

	}

	@FXML
	public void back() {
		this.controller.toMain();
	}

}
