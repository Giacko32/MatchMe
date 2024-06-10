package com.prova.matchme.Autenticazione.Interfacce;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.GestioneProfilo.Controller.ProfiloCtrl;
import com.prova.matchme.GestioneSede.Controller.AmministrazioneSedeCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminView {

	private AuthCtrl authCtrl;
	private Gestore g;
	private Stage s;
	public AdminView(AuthCtrl authCtrl, Gestore g,Stage s){
		this.authCtrl=authCtrl;
		this.g=g;
		this.s=s;
	}
	@FXML
	public void ClickLogout() {
		this.authCtrl.toConfirm();
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

	@FXML
	public void ClickProfile() {
		new ProfiloCtrl(this.s,null,g);
	}
	public void clickVerificaSconto() {

	}
    @FXML
	public void clickAbilitaAllenatore() {
		AmministrazioneSedeCtrl a=new AmministrazioneSedeCtrl(s,g);
		a.toabilita();
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
