package com.prova.matchme.GestioneProfilo.Interfacce;


import com.prova.matchme.GestioneProfilo.Controller.ProfiloCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CodiceAbbonamentoView {

	private Stage s;
	private ProfiloCtrl profiloCtrl;


	public CodiceAbbonamentoView(ProfiloCtrl profiloCtrl,Stage s){
		this.s=s;
		this.profiloCtrl=profiloCtrl;
	}

	@FXML
	public TextField codice;

	@FXML
	public void ClickConferma() {
		this.profiloCtrl.Passcode(codice.getText());
		s.close();
	}



}
