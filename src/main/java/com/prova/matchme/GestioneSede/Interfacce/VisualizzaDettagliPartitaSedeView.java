package com.prova.matchme.GestioneSede.Interfacce;


import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.Entity.Partita;
import com.prova.matchme.Entity.PartitaDetails;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneSede.Controller.GestionePartiteSedeCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.intellij.lang.annotations.Flow;

import java.util.ArrayList;

public class VisualizzaDettagliPartitaSedeView {


	private GestionePartiteSedeCtrl gestionePartiteSedeCtrl;
	private ArrayList<Partita> listapartite;
	private Partita partitaselected;
	public VisualizzaDettagliPartitaSedeView(GestionePartiteSedeCtrl gestionePartiteSedeCtrl,ArrayList<Partita> listapartite){
		this.gestionePartiteSedeCtrl=gestionePartiteSedeCtrl;
		this.listapartite=listapartite;
	}

	@FXML
	public ListView<Partita> listaPartite;


	@FXML
	public void initialize(){
		ObservableList<Partita> items= FXCollections.observableArrayList(listapartite);
		listaPartite.setItems(items);
		listaPartite.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
			gestionePartiteSedeCtrl.PassPartita(newvalue);
		});

	}
	@FXML
	private ListView<Utente> Squadra1List;
	@FXML
	private ListView<Utente> Squadra2List;
	@FXML
	private TextField SedeField;
	@FXML
	private TextField CampoField;
	@FXML
	private TextField DataOraField;
	@FXML
	private TextField SportField;
	@FXML
	public Button invita;
	public Button assegna;
	public Button rinvia;


	@FXML
    public void showDetails(PartitaDetails partitaDetails){
		SedeField.setDisable(false);
		CampoField.setDisable(false);
		SportField.setDisable(false);
		DataOraField.setDisable(false);
		Squadra1List.setDisable(false);
		Squadra2List.setDisable(false);
		SedeField.setText(partitaDetails.sede.toString());
		CampoField.setText(partitaDetails.campo.getNomecampo());
		SportField.setText(partitaDetails.campo.getSport());
		DataOraField.setText(partitaDetails.campo.getOrarioString());
		ObservableList<Utente> squadra1 = FXCollections.observableArrayList(partitaDetails.squadra1);
		Squadra1List.setItems(squadra1);
		ObservableList<Utente> squadra2 = FXCollections.observableArrayList(partitaDetails.squadra2);
		Squadra2List.setItems(squadra2);
		invita.setDisable(false);
		rinvia.setDisable(false);
		assegna.setDisable(false);
	}


	@FXML
	public void back(){
		gestionePartiteSedeCtrl.toAdmin();
	}

	@FXML
	public void ClickInvitaGiocatore() {
       gestionePartiteSedeCtrl.InvitaGiocatoreCliccato();
	}

	public void ClickAssegnaRisultato() {

	}
    @FXML
	public void ClickRinviaPartita() {
        gestionePartiteSedeCtrl.rinviaClicked();
	}


}
