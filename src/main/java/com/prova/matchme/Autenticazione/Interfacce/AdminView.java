package com.prova.matchme.Autenticazione.Interfacce;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.GestioneAllenamenti.Controller.GestioneAllCtrl;
import com.prova.matchme.GestioneProfilo.Controller.ProfiloCtrl;
import com.prova.matchme.GestioneSede.Controller.AmministrazioneSedeCtrl;
import com.prova.matchme.GestioneSede.Controller.GestionePartiteSedeCtrl;
import com.prova.matchme.GestioneTornei.Controller.AmministrazioneTorneiCtrl;
import com.prova.matchme.Threads.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminView {

	private AuthCtrl authCtrl;
	private AmministrazioneTorneiCtrl amminCtrl;
	private Gestore g;
	private Stage s;
	private static int firstTime=0;
	private static ContrAbb thread;
	private static VerPartite threadver;
	public static ContrAll threadca;
	public static FidTess threadft;
	private static CreaCalendario threadcal;

	public AdminView(AuthCtrl authCtrl, Gestore g,Stage s){
		this.authCtrl=authCtrl;
		this.g=g;
		this.s=s;
		if(firstTime==0){
			firstTime++;
			thread=new ContrAbb(g);
			thread.start();
			threadver=new VerPartite(g);
			threadver.start();
			threadca=new ContrAll(g);
			threadca.start();
			threadft=new FidTess();
			threadft.start();
			threadcal=new CreaCalendario(g);
			threadcal.start();

		}
	}

	public AdminView(AmministrazioneTorneiCtrl amminCtrl, Gestore g, Stage s){
		this.amminCtrl = amminCtrl;
		this.g = g;
		this.s = s;
	}

	@FXML
	public void ClickLogout() {
		firstTime=0;
		this.authCtrl.toConfirm();
		thread.onstop();
		threadver.onstop();
		threadft.onstop();
		threadca.onstop();
		threadcal.onstop();
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
	@FXML
	public void clickVerificaSconto() {
		AmministrazioneSedeCtrl a=new AmministrazioneSedeCtrl(s,g);
		a.toverificaSconto();
	}
    @FXML
	public void clickAbilitaAllenatore() {
		AmministrazioneSedeCtrl a=new AmministrazioneSedeCtrl(s,g);
		a.toabilita();
	}
    @FXML
	public void clickAggiungiAbbonamento() {
		AmministrazioneSedeCtrl a=new AmministrazioneSedeCtrl(s,g);
		a.tocreateabb();

	}
	@FXML
	public void ClickVisualizzaPartiteSede() {
		GestionePartiteSedeCtrl gs =new GestionePartiteSedeCtrl(s, g);
		gs.toVisualize();
	}
    @FXML
	public void clickCreaPartita() {
		GestionePartiteSedeCtrl gs =new GestionePartiteSedeCtrl(s, g);
		gs.tocreapartita();
	}

	@FXML
	public void clickTorneiSede() {
		AmministrazioneTorneiCtrl atc = new AmministrazioneTorneiCtrl(s,g);
		atc.mostraTorneiSede();
	}

	public void clickVisualizzaAllenamenti(){
		new GestioneAllCtrl(g, s);
	}


	public void ShowBnd() {

	}

}
