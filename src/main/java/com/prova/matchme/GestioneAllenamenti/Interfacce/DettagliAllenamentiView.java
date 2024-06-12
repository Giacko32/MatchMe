package com.prova.matchme.GestioneAllenamenti.Interfacce;


import com.prova.matchme.Entity.Allenamento;
import com.prova.matchme.Entity.Partita;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneAllenamenti.Controller.GestioneAllCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class DettagliAllenamentiView {

	private GestioneAllCtrl gestioneAllCtrl;
	private ArrayList<Partita> partitaArrayList;
	private Partita selectedAllenamento;
	private Allenamento allenamentoDetails;
	private Utente utente;
	@FXML
	public ListView<Partita> listaAllenamenti;
	@FXML
	private ListView<Utente> PartecipantiLista;
	@FXML
	private TextField SedeField;
	@FXML
	private TextField CampoField;
	@FXML
	private TextField DataOraField;
	@FXML
	private TextField SportField;
	@FXML
	private TextField AllenatoreField;
	@FXML
	private Button prenotaButton;

	public DettagliAllenamentiView(GestioneAllCtrl gestioneAllCtrl, ArrayList<Partita> listaPartite, Utente utente){
		this.gestioneAllCtrl = gestioneAllCtrl;
		this.partitaArrayList = listaPartite;
		this.utente = utente;
	}

	@FXML
	public void initialize(){
		ObservableList<Partita> lista = FXCollections.observableArrayList(partitaArrayList);
		listaAllenamenti.setItems(lista);
		listaAllenamenti.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldvalue, newvalue) -> {
			this.selectedAllenamento = newvalue;
			selectAllenamento();
		}));

	}

	public void selectAllenamento(){
		SedeField.setDisable(false);
		CampoField.setDisable(false);
		DataOraField.setDisable(false);
		SportField.setDisable(false);
		AllenatoreField.setDisable(false);
		PartecipantiLista.setDisable(false);
		allenamentoDetails = gestioneAllCtrl.AllenamentoClicked(selectedAllenamento);
        prenotaButton.setDisable(allenamentoDetails.partecipanti.contains(utente));
		SedeField.setText(allenamentoDetails.sede.toString());
		CampoField.setText(allenamentoDetails.campo.getNomecampo());
		DataOraField.setText(allenamentoDetails.campo.getOrarioString());
		SportField.setText(allenamentoDetails.campo.getSport());
		AllenatoreField.setText(allenamentoDetails.allenatore.toString());
		ObservableList<Utente> listapartecipanti = FXCollections.observableArrayList(allenamentoDetails.partecipanti);
		PartecipantiLista.setItems(listapartecipanti);
	}

	public void PrenotaClicked() {
		gestioneAllCtrl.PrenotaCliccato(allenamentoDetails);
	}

	public void goBack(){
		gestioneAllCtrl.toMain();
	}

}
