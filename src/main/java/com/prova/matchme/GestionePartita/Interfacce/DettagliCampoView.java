package com.prova.matchme.GestionePartita.Interfacce;


import com.prova.matchme.Entity.Campo;
import com.prova.matchme.Entity.Sede;
import com.prova.matchme.GestionePartita.Controller.PartitaCtrl;
import jakarta.mail.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class DettagliCampoView {

	private ArrayList<Campo> listaLiberi;
	private PartitaCtrl partitaCtrl;
	private Campo selectedCampo;

	@FXML
	private ListView<Campo> listaCampiLiberi;
	@FXML
	private TextField nomeCampo;
	@FXML
	private TextField sportCampo;
	@FXML
	private TextField nomeSede;
	@FXML
	private TextField indirizzoSede;



	public DettagliCampoView(ArrayList<Campo> list, PartitaCtrl pc){
		this.listaLiberi = list;
		this.partitaCtrl = pc;
	}

	@FXML
	public void initialize(){
		ObservableList<Campo> listaCampi = FXCollections.observableArrayList(listaLiberi);
		listaCampiLiberi.setItems(listaCampi);
		listaCampiLiberi.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
			selectedCampo = newvalue;
		});
	}

	public void ClickPrenota() {

	}

	@FXML
	public void selectCampoOra(){
		partitaCtrl.SelectedCampo(selectedCampo);
	}

	public void showDetails(Sede sede){

	}


	@FXML
	public void back() {
		this.partitaCtrl.toMain();
	}

}
