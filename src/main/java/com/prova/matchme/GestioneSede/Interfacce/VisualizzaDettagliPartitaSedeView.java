package com.prova.matchme.GestioneSede.Interfacce;


import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.Entity.Partita;
import com.prova.matchme.GestioneSede.Controller.GestionePartiteSedeCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.intellij.lang.annotations.Flow;

import java.util.ArrayList;

public class VisualizzaDettagliPartitaSedeView {


	private GestionePartiteSedeCtrl gestionePartiteSedeCtrl;
	private ArrayList<Partita> listapartite;
	public VisualizzaDettagliPartitaSedeView(GestionePartiteSedeCtrl gestionePartiteSedeCtrl,ArrayList<Partita> listapartite){
		this.gestionePartiteSedeCtrl=gestionePartiteSedeCtrl;
		this.listapartite=listapartite;
	}

	@FXML
	public ListView<Partita> listaPartite;


	@FXML
	public void initialize(){
		ObservableList<Partita> items= FXCollections.observableArrayList(listapartite);
		listaPartite.setItems(items);
	}

	public void ClickInvitaGiocatore() {

	}

	public void ClickAssegnaRisultato() {

	}

	public void ClickRinviaPartita() {

	}


}
