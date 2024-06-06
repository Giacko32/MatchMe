package com.prova.matchme.shared;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ConfirmView {

	private AuthCtrl authCtrl;
	private Stage s;
	public ConfirmView(AuthCtrl authCtrl, Stage s){
		this.authCtrl=authCtrl;
		this.s=s;
	}
	@FXML
	public void ClickConferma() {
		if(authCtrl!=null){
			this.authCtrl.toLogin();
			s.close();
		}

	}

}
