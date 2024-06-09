package com.prova.matchme.GestionePartita.Interfacce;

import com.prova.matchme.GestionePartita.Controller.PartitaCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class DetailsTuttePartiteView {

	private PartitaCtrl controller;

	public DetailsTuttePartiteView(PartitaCtrl controller) {
		this.controller = controller;
	}
    @FXML
	public void initialize(){

	}

	public void ClickPartecipa() {

	}


	public void ShowBnd() {

	}

	@FXML
	public void back() {
		this.controller.toMain();
	}

}
