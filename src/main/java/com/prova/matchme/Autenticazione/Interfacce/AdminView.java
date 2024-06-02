package com.prova.matchme.Autenticazione.Interfacce;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Entity.Gestore;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class AdminView {

	private AuthCtrl authCtrl;
	private Gestore g;
	public AdminView(AuthCtrl authCtrl, Gestore g){
		this.authCtrl=authCtrl;
		this.g=g;
	}
	@FXML
	public void ClickLogout() {
		this.authCtrl.toLogin();
	}


	@FXML
	private Label nome;
	@FXML
	private AnchorPane Ancorpane;

	@FXML
	public void initialize() {
		// Load the image
		Image backgroundImage = new Image(getClass().getResource("/com/prova/matchme/images/background.png").toExternalForm());

		// Create a BackgroundImage
		BackgroundImage bgImage = new BackgroundImage(
				backgroundImage,
				BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
		);

		// Set the background to the AnchorPane
		Ancorpane.setBackground(new Background(bgImage));

		nome.setText(g.toString());

	}

	public void clickLogout() {

	}

	public void clickVerificaSconto() {

	}

	public void clickAbilitaAllenatore() {

	}

	public void clickAggiungiAbbonamento() {

	}

	public void ClickVisualizzaPartiteSede() {

	}

	public void clickCreaPartita() {

	}

	public void clickTorneiSede() {

	}

	public void ShowBnd() {

	}

}
