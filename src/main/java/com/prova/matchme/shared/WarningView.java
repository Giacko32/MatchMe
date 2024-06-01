package com.prova.matchme.shared;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;


public class WarningView {

	@FXML
	private Label testo;

	public WarningView(String avviso){
		this.avviso=avviso;
	}
	private String avviso;

	@FXML
	public void initialize() {
		testo.setText(avviso);
	}

	public void ClickOk() {

	}

}
