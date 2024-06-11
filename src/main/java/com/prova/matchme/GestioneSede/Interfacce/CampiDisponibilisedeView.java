package com.prova.matchme.GestioneSede.Interfacce;


import com.prova.matchme.Entity.Campo;
import com.prova.matchme.GestioneSede.Controller.GestionePartiteSedeCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class CampiDisponibilisedeView {

	private GestionePartiteSedeCtrl gestionePartiteSedeCtrl;
	private ArrayList<Campo> listacampi;

	public CampiDisponibilisedeView(GestionePartiteSedeCtrl gestionePartiteSedeCtrl,ArrayList<Campo> listacampi){
		this.gestionePartiteSedeCtrl=gestionePartiteSedeCtrl;
		this.listacampi=listacampi;
	}

	@FXML
	public ListView<Campo> listaCampi;
	public Button crea;

	@FXML
	public void initialize(){
		ObservableList<Campo> items= FXCollections.observableArrayList(listacampi);
		listaCampi.setItems(items);
		listaCampi.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
			crea.setDisable(false);
		});
	}
	@FXML
	public void back(){
		gestionePartiteSedeCtrl.toAdmin();
	}
	@FXML
	public void ClickCreaPartita() {
		gestionePartiteSedeCtrl.PassCampo(listaCampi.getSelectionModel().getSelectedItem());
	}

}
