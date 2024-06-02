package com.prova.matchme.Autenticazione.Interfacce;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginView {

	public AnchorPane Ancorpane;
	public TextField usernamefield;
	public PasswordField passwordfield;

	public LoginView(Stage s){
		this.authctrl=new AuthCtrl(s);
	}

	private final AuthCtrl authctrl;
	@FXML
	public void ClickAccedi() {
		this.authctrl.controllaCredenziali(usernamefield.getText().trim(),passwordfield.getText().trim());
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
	private Button registerbutton;


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

		if(Main.sistema==1){
			registerbutton.setVisible(false);
		}
	}


}
