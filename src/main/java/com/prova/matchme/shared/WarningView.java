package com.prova.matchme.shared;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class WarningView {

	@FXML
	private Label testo;

	public WarningView(String avviso, Stage s){
		this.avviso=avviso;
		this.s=s;
	}
	private String avviso;
	private Stage s;

	@FXML
	public void initialize() {
		testo.setText(avviso);
	}
    @FXML
	public void ClickOk() {
		s.close();
	}

}
