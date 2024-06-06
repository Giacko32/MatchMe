package com.prova.matchme.GestioneProfilo.Interfacce;


import com.prova.matchme.GestioneProfilo.Controller.ProfiloCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifyPasswordView {

	public Stage s;
	public ProfiloCtrl profiloCtrl;
	public ModifyPasswordView(ProfiloCtrl profiloCtrl,Stage s){
		this.s=s;
		this.profiloCtrl=profiloCtrl;
	}

	@FXML
	public TextField oldpassword;
	public TextField newpassword;

	@FXML
	public void ClickCambiaPassword() {
		this.profiloCtrl.PassPassword(oldpassword.getText(),newpassword.getText());
		s.close();
	}

}
