package com.prova.matchme.Autenticazione.Controller;

import com.prova.matchme.ApplicationMatchMe;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthCtrl {
    public AuthCtrl(Stage stage){
		FXMLLoader loader = new FXMLLoader(ApplicationMatchMe.class.getResource("FXML/login-view.fxml"));
		try {
			Scene scene = new Scene(loader.load());
			stage.setScene(scene);
			stage.show();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	private String username_inserito;

	private String password_inserita;

	private String password;

	private int tipo_utente;

	private String codice;

	private String mail;

	private String codice_inserito;

	public boolean ControllaFormatoDati() {
		return false;
	}

	public void CloseWarningView() {

	}

	public int ControllaTipo() {
		return 0;
	}

	public void SendDati() {

	}

	public boolean ControllaUsername() {
		return false;
	}

	public void PassMail() {

	}

	public String GeneraCodice() {
		return null;
	}

	public void InviaMail() {

	}

	public void PassCode() {

	}

	public boolean ControllaCodice() {
		return false;
	}

	public boolean ControllaMail() {
		return false;
	}

	public void PassPassword() {

	}

	public boolean ControllaPassword() {
		return false;
	}

	public void ConfermaCliccata() {

	}

}
