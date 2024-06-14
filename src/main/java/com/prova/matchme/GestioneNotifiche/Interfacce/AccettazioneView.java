package com.prova.matchme.GestioneNotifiche.Interfacce;

import com.prova.matchme.Entity.Utente;
import com.prova.matchme.Entity.UtentePart;
import com.prova.matchme.GestioneNotifiche.Controller.NotifyCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AccettazioneView {


	private Stage s;
	private Utente u;
	private NotifyCtrl notifyCtrl;
	public AccettazioneView(Stage s, Utente u, NotifyCtrl notifyCtrl){
		this.notifyCtrl=notifyCtrl;
		this.u=u;
		this.s=s;
	}

	@FXML
	public Label nome;
	public Label livello;
	public Label id;
	public Label cognome;

	@FXML
	public void initialize(){
		nome.setText("Nome: "+u.getNome());
		cognome.setText("Cognome: "+u.getCognome());
		id.setText("Id: "+u.getId());
		livello.setText("Livello: "+u.getLivello());
	}
	@FXML
	public void ClickAccetta() {
		notifyCtrl.passAccetta();
		s.close();
	}
    @FXML
	public void ClickRifiuta() {
		notifyCtrl.passRifiuta();
		s.close();

	}
	@FXML
	public void back(){
		s.close();
	}

}
