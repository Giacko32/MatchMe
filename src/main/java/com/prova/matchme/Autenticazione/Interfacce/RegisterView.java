package com.prova.matchme.Autenticazione.Interfacce;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class RegisterView {


	private AuthCtrl authCtrl;
	public RegisterView(AuthCtrl authCtrl){
		this.authCtrl=authCtrl;
	}

	@FXML
	private AnchorPane anchorPane;

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
		anchorPane.setBackground(new Background(bgImage));
	}

	@FXML
	public void backlogin(){
		this.authCtrl.toLogin();
	}


	private Object usernamefield;

	private Object nomefield;

	private Object passwordfield;

	public void ClickRegistra() {

	}

	public void inserisciDati() {

	}

	public void ShowBnd() {

	}

}
