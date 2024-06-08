package com.prova.matchme.GestionePartita.Interfacce;

import com.prova.matchme.GestionePartita.Controller.PartitaCtrl;
import jakarta.mail.Part;
import javafx.stage.Stage;

public class SelectTipoPartiteView {

	private PartitaCtrl partitaCtrl;
	private Stage stage;

	public SelectTipoPartiteView(PartitaCtrl pc, Stage stage){
		this.partitaCtrl = pc;
		this.stage = stage;
	}

	public void ClickLeTuePartite() {
		partitaCtrl.passTipoPartita(true, stage);
	}

	public void ClickTutteLePartite() {
		partitaCtrl.passTipoPartita(false, stage);
	}

}
