package com.prova.matchme.GestioneSede.Interfacce;

import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneSede.Controller.AmministrazioneSedeCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AbbonamentoView {


	private AmministrazioneSedeCtrl amministrazioneSedeCtrl;
	private Utente u;
	public AbbonamentoView(AmministrazioneSedeCtrl amministrazioneSedeCtrl,Utente u){
		this.amministrazioneSedeCtrl=amministrazioneSedeCtrl;
		this.u=u;
	}
	@FXML
	public TextField nome;
	public TextField cognome;
	public TextField id;
	@FXML
	public void initialize(){
		nome.setText(u.getNome());
		cognome.setText(u.getCognome());
		id.setText(String.valueOf(u.getId()));
	}

	@FXML
	public void back(){
		amministrazioneSedeCtrl.toAdmin();
	}

	@FXML
	public void click1(){
		amministrazioneSedeCtrl.PassDurata(1);
	}
	@FXML
	public void click3(){
		amministrazioneSedeCtrl.PassDurata(3);
	}
	@FXML
	public void click6(){
		amministrazioneSedeCtrl.PassDurata(6);
	}


}
