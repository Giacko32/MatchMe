package com.prova.matchme.GestioneSede.Interfacce;

import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneSede.Controller.AmministrazioneSedeCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;

public class DettagliGiocatoreSconto {


	private Stage s;
	private AmministrazioneSedeCtrl amministrazioneSedeCtrl;
	private Utente u;
	public DettagliGiocatoreSconto(Stage s, AmministrazioneSedeCtrl amministrazioneSedeCtrl, Utente u){
		this.s=s;
		this.amministrazioneSedeCtrl=amministrazioneSedeCtrl;
		this.u=u;
	}

	@FXML
	public TextField nome;
	public TextField cognome;
	public TextField id;
	public Label sconto;

	@FXML
	public void initialize(){
		nome.setText(u.getNome());
		cognome.setText(u.getCognome());
		id.setText(String.valueOf(u.getId()));
		sconto.setText("Sconto: "+u.getEta()+"%");
	}


	@FXML
	public void clickApplicaSconto() {
		amministrazioneSedeCtrl.passSconto();
		s.close();
	}

}
