package com.prova.matchme.GestioneTornei.Interfacce;


import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneTornei.Controller.TorneiCtrl;
import javafx.stage.Stage;

public class IscrizioneSquadraView {

	private TorneiCtrl torneiCtrl;
	private Utente utente;
	private Stage stage;


	public IscrizioneSquadraView(TorneiCtrl torneiCtrl, Utente utente, Stage stage) {
		this.torneiCtrl = torneiCtrl;
		this.utente = utente;
		this.stage = stage;

	}
	public void InsertNomeSquadra() {

	}

	public void ClickAggiungiPartecipanti() {
		torneiCtrl.AggiungiPartecipanti(torneiCtrl, stage);

	}



	public void ClickIscriviSquadra() {

	}

	public void back() {
		this.torneiCtrl.toMain();
	}

}
