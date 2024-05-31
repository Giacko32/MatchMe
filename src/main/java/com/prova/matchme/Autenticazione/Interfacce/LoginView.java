package com.prova.matchme.Autenticazione.Interfacce;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class LoginView {

	public AnchorPane Ancorpane;

	public LoginView(AuthCtrl authCtrl){
		this.authctrl=authCtrl;
	}

	private final AuthCtrl authctrl;
	@FXML
	public void ClickAccedi() {
		this.authctrl.toMain();
	}

	@FXML
	public void ClickRegistrati() {
		this.authctrl.toRegistra();
	}
	@FXML
	public void ClickRecovery() {
		this.authctrl.toRecovery();
	}



	@FXML
	public void initialize() {
		// Load the image
		Image backgroundImage = new Image(getClass().getResource("/com/prova/matchme/images/backgroundlogin.png").toExternalForm());

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
	}

	public void inserisciCredenziali() {

	}

	public void ShowBnd() {

	}

}
