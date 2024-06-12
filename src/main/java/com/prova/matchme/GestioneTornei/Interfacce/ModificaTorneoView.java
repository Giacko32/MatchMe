package com.prova.matchme.GestioneTornei.Interfacce;


import com.prova.matchme.Entity.Torneo;
import com.prova.matchme.GestioneTornei.Controller.AmministrazioneTorneiCtrl;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ModificaTorneoView {

	private Stage stage;
	private AmministrazioneTorneiCtrl amminCtrl;
	private Torneo torneo;


	public ModificaTorneoView(Stage stage, AmministrazioneTorneiCtrl amminCtrl, Torneo torneo) {
		this.stage = stage;
		this.amminCtrl = amminCtrl;
		this.torneo = torneo;
	}

	public void ModifyData() {

	}

	public void clickConferma() {

	}

	@FXML
	public void back() {
		this.amminCtrl.toMain();
	}

}
