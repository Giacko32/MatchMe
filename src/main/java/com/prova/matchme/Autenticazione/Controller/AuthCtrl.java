package com.prova.matchme.Autenticazione.Controller;

import com.prova.matchme.Autenticazione.Interfacce.LoginView;
import com.prova.matchme.Autenticazione.Interfacce.MainView;
import com.prova.matchme.Autenticazione.Interfacce.RecoveryView;
import com.prova.matchme.Autenticazione.Interfacce.RegisterView;
import com.prova.matchme.CustomStage;
import com.prova.matchme.Utils;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AuthCtrl {
	Stage s;
    public AuthCtrl(Stage stage){
		s=stage;
		Utils.cambiaInterfaccia("FXML/login-view.fxml", stage, c -> {
			return new LoginView(this);
		});
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

	public void toRegistra(){
		Utils.cambiaInterfaccia("FXML/Register-view.fxml", s, c -> {
			return new RegisterView(this);
		});
	}
	public void toLogin(){
		Utils.cambiaInterfaccia("FXML/login-view.fxml", s, c -> {
			return new LoginView(this);
		});
	}

	public void toRecovery(){
		Utils.cambiaInterfaccia("FXML/RecuperaPassword.fxml", new CustomStage("Recupero password"), c -> {
			return new RecoveryView(this);
		},450,200);
	}

	public void toMain(){
		Utils.cambiaInterfaccia("FXML/Main-view.fxml", s, c -> {
			return new MainView(this);
		});
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
