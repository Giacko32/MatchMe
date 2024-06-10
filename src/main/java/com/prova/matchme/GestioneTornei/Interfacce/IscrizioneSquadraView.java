package com.prova.matchme.GestioneTornei.Interfacce;


import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneTornei.Controller.TorneiCtrl;

public class IscrizioneSquadraView {

	private TorneiCtrl torneiCtrl;
	private Utente utente;

	public IscrizioneSquadraView(TorneiCtrl torneiCtrl, Utente utente) {
		this.torneiCtrl = torneiCtrl;
		this.utente = utente;

	}
	public void InsertNomeSquadra() {

	}

	public void ClickAggiungiPartecipanti() {

	}

	public void PassGiocatore() {

	}

	public void ClickIscriviSquadra() {

	}

	public void back() {
		this.torneiCtrl.toMain();
	}

}
