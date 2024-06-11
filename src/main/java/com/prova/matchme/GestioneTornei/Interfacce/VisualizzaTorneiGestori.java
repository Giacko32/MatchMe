package com.prova.matchme.GestioneTornei.Interfacce;


import com.prova.matchme.Entity.Torneo;
import com.prova.matchme.GestioneTornei.Controller.AmministrazioneTorneiCtrl;
import javafx.stage.Stage;

import java.util.ArrayList;

public class VisualizzaTorneiGestori {

	private ArrayList<Torneo> tornei;
	private Stage stage;
	private AmministrazioneTorneiCtrl amminCtrl;

	public VisualizzaTorneiGestori(ArrayList<Torneo> tornei, Stage stage, AmministrazioneTorneiCtrl amminCtrl){
		this.tornei = tornei;
		this.stage = stage;
		this.amminCtrl = amminCtrl;
	}

	public void clickModificaTorneo() {

	}

	public void clickCancellaTorneo() {

	}

	public void clickSquadreAttesa() {

	}

	public void showDettagliTorneo() {

	}

}
