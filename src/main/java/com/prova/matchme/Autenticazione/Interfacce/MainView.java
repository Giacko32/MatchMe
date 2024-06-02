package com.prova.matchme.Autenticazione.Interfacce;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class MainView {



	private AuthCtrl authCtrl;
	private Utente u;

	public MainView(AuthCtrl authCtrl,Utente u){
		this.authCtrl=authCtrl;
		this.u=u;
	}
	@FXML
	private AnchorPane Ancorpane;

	@FXML
	private Label nome;

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

		nome.setText(u.toString());

	}

	public void clickChat() {

	}
    @FXML
	public void ClickLogout() {
		this.authCtrl.toLogin();
	}

	public void ClickProfile() {

	}

	public void ClickStorico() {

	}

	public void ClickNotifiche() {

	}

	public void ClickVisualizzaCampiLiberi() {

	}

	public void ClickVisualizzaPartite() {

	}

	public void ClickTornei() {

	}

	public void ClickAllenamenti() {

	}

}
