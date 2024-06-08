package com.prova.matchme.GestionePartita.Interfacce;


import com.prova.matchme.Entity.Campo;
import com.prova.matchme.Entity.Partita;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class DetailsMiaPartitaView {

	private Partita selectedPartita;
	private ArrayList<Partita> listaPartite;
	@FXML
	private ListView<Partita> ListaMiePartite;

	public DetailsMiaPartitaView(ArrayList<Partita> lista){
		this.listaPartite = lista;
	}

	@FXML
	public void initialize(){
		ObservableList<Partita> partitelist = FXCollections.observableArrayList(listaPartite);
		ListaMiePartite.setItems(partitelist);
		ListaMiePartite.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
			selectedPartita = newvalue;
		});
	}

	public void ClickAggiungiGiocatori() {

	}

	public void ClickInvitaGiocatori() {

	}

	public void ClickAggiungiOspite() {

	}

	public void ClickCancellaPrenotazione() {

	}

	public void ShowBnd() {

	}

}
