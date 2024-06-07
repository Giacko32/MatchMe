package com.prova.matchme.GestionePartita.Interfacce;


import com.prova.matchme.Entity.Campo;
import com.prova.matchme.GestionePartita.Controller.PartitaCtrl;
import jakarta.mail.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class DettagliCampoView {

	private ArrayList<Campo> listaLiberi;
	private PartitaCtrl partitaCtrl;

	@FXML
	private ListView<Campo> listaCampiLiberi;


	public DettagliCampoView(ArrayList<Campo> list, PartitaCtrl pc){
		this.listaLiberi = list;
		this.partitaCtrl = pc;
	}

	@FXML
	public void initialize(){
		ObservableList<Campo> listaCampi = FXCollections.observableArrayList(listaLiberi);
		listaCampiLiberi.setItems(listaCampi);
	}

	public void ClickPrenota() {

	}

	@FXML
	public void back() {
		this.partitaCtrl.toMain();
	}

}
