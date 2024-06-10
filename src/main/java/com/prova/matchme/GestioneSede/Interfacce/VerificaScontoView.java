package com.prova.matchme.GestioneSede.Interfacce;


import com.prova.matchme.GestioneSede.Controller.AmministrazioneSedeCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VerificaScontoView {


	private AmministrazioneSedeCtrl amministrazioneSedeCtrl;
	private Stage stage;
	public VerificaScontoView(AmministrazioneSedeCtrl amministrazioneSedeCtrl,Stage s){
		this.stage=s;
		this.amministrazioneSedeCtrl=amministrazioneSedeCtrl;
	}

	@FXML
	public TextField codice;


	@FXML
	public void ClickVerifica() {
		amministrazioneSedeCtrl.passCodice(codice.getText());
		stage.close();
	}

}
