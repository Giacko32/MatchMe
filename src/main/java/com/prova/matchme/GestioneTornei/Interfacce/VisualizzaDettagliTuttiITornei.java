package com.prova.matchme.GestioneTornei.Interfacce;


import com.prova.matchme.Entity.Torneo;
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

	public VisualizzaDettagliTuttiITornei(ArrayList<Torneo> lista){
		this.listaTornei = lista;
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

}
