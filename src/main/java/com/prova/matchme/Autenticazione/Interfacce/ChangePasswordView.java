package com.prova.matchme.Autenticazione.Interfacce;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

public class ChangePasswordView {
	private AuthCtrl authCtrl;
	private Stage s;
	public ChangePasswordView(AuthCtrl authCtrl, Stage s){
		this.authCtrl=authCtrl;
		this.s=s;
	}
	@FXML
	private TextField nuovapass;
	@FXML
	private TextField confirm;
	public void ClickChangePassword() {
		this.authCtrl.passPassword(nuovapass.getText().trim(), confirm.getText().trim(),s);
	}


	public void ShowBnd() {

	}

}
