package com.prova.matchme.GestioneTornei.Interfacce;


import com.prova.matchme.Entity.Torneo;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneTornei.Controller.TorneiCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class IscrizioneSquadraView {

	private TorneiCtrl torneiCtrl;
	private Utente utente;
	private Stage stage;
	private Torneo torneo;
	private int nSquadra;
	private float media;
	@FXML
	private ListView<Utente> listaGiocatoriSquadra;
	@FXML
	private TextField numeroSquadra;
	@FXML
	private TextField livelloMedio;



	public IscrizioneSquadraView(TorneiCtrl torneiCtrl, Utente utente, Stage stage, Torneo torneo, int nSquadra) {
		this.torneiCtrl = torneiCtrl;
		this.utente = utente;
		this.stage = stage;
		this.torneo = torneo;
		this.nSquadra = nSquadra;
	}



	public void ClickAggiungiPartecipanti() {
		torneiCtrl.AggiungiPartecipanti(torneiCtrl, stage, torneo);

	}



	public void ClickIscriviSquadra() {
		torneiCtrl.IscriviSquadraCliccato(torneo, media);
	}

	@FXML
	public void updateListaGiocatori(ArrayList<Utente> giocatori) {
		ObservableList<Utente> utenteList = FXCollections.observableArrayList(giocatori);
		listaGiocatoriSquadra.setItems(utenteList);
		//Calcoliamo la media e assegnamo il numero di squadra corrente
		media = 0;
		float current_sum = 0;
		for (Utente u : giocatori){
			current_sum+=u.getLivello();
		}
		media = current_sum/giocatori.size();
		livelloMedio.setText(String.valueOf(media));
		numeroSquadra.setText(String.valueOf(this.nSquadra));
	}

	public void back() {
		this.torneiCtrl.toMain();
	}

}
