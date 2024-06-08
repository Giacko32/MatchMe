package com.prova.matchme.shared;

import com.prova.matchme.Entity.Notifica;
import com.prova.matchme.GestioneNotifiche.Controller.NotifyCtrl;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ChoiceView {


	private NotifyCtrl notifyCtrl;
	private Stage s;

	public ChoiceView(NotifyCtrl notifyCtrl,Stage s){
		this.s=s;
		this.notifyCtrl=notifyCtrl;
	}

    @FXML
	public void clickAccetta() {
		this.notifyCtrl.inviaEsito(true);

	}

    @FXML
	public void clickRifiuta() {
		this.notifyCtrl.inviaEsito(false);
	}

	public void SelectSquadra() {

	}

	public void ClickTuttiItornei() {

	}

	public void ClickIMieiTornei() {

	}

}
