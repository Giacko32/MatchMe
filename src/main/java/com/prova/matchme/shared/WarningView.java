package com.prova.matchme.shared;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.stage.Stage;


public class WarningView {

	@FXML
	private Label testo;

	public WarningView(String avviso, Stage s){
		this.avviso=avviso;
		this.s=s;
	}
	public WarningView(Stage s){
		this.s=s;
	}
	private String avviso;
	private Stage s;

	@FXML
	public void initialize() {
		if (testo!=null){testo.setText(avviso);}
	}
    @FXML
	public void ClickOk() {
		s.close();
	}

}
