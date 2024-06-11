package com.prova.matchme.GestioneSede.Interfacce;


import com.prova.matchme.Entity.PartitaDetails;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneSede.Controller.GestionePartiteSedeCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class InvitaGiocatoriView {


	private GestionePartiteSedeCtrl gestionePartiteSedeCtrl;
	private ArrayList<Utente> listautenti;
	private PartitaDetails partitaDetails;
	public InvitaGiocatoriView(GestionePartiteSedeCtrl gestionePartiteSedeCtrl, ArrayList<Utente> listautenti, PartitaDetails p){
		this.gestionePartiteSedeCtrl=gestionePartiteSedeCtrl;
		this.listautenti=listautenti;
		this.partitaDetails=p;
	}

	@FXML
	public ListView<Utente> listaUtenti;
	public Button squadra;
	public TextField parametro;


	@FXML
	public void initialize(){
		ObservableList<Utente> items= FXCollections.observableArrayList(listautenti);
		listaUtenti.setItems(items);
	}
	@FXML
	public void ClickInvitaGiocatore() {
		gestionePartiteSedeCtrl.PassDati(listaUtenti.getSelectionModel().getSelectedItem(),1);
	}

    @FXML
	public void ClickSearch() {
		gestionePartiteSedeCtrl.PassDati2(parametro.getText());
	}
    @FXML
	public void SetLista(ArrayList<Utente> lista) {
		ObservableList<Utente> items= FXCollections.observableArrayList(lista);
		listaUtenti.setItems(items);
	}

	@FXML
	public void back(){
		gestionePartiteSedeCtrl.toAdmin();
	}

}
