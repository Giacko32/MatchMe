package com.prova.matchme.Autenticazione.Interfacce;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import javafx.fxml.FXML;

public class LoginView {

	public LoginView(AuthCtrl authCtrl){
		this.authctrl=authCtrl;
	}



	private AuthCtrl authctrl;
	public void ClickAccedi() {

	}

	@FXML
	public void ClickRegistrati() {
		this.authctrl.toRegistra();
	}
	@FXML
	public void ClickRecovery() {
		this.authctrl.toRecovery();
	}

	public void inserisciCredenziali() {

	}

	public void ShowBnd() {

	}

}
