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

public class DettagliMioAllenamentoView {

	private GestioneAllCtrl gestioneAllCtrl;
	private ArrayList<Partita> partitaArrayList;
	private Partita selectedAllenamento;
	private Allenamento allenamentoDetails;
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
	private Button cancellaButton;

	public DettagliMioAllenamentoView(GestioneAllCtrl gestioneAllCtrl, ArrayList<Partita> partitaArrayList){
		this.gestioneAllCtrl = gestioneAllCtrl;
		this.partitaArrayList = partitaArrayList;
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
		try{
			SedeField.setDisable(false);
			CampoField.setDisable(false);
			DataOraField.setDisable(false);
			SportField.setDisable(false);
			AllenatoreField.setDisable(false);
			PartecipantiLista.setDisable(false);
			cancellaButton.setDisable(false);
			allenamentoDetails = gestioneAllCtrl.AllenamentoClicked(selectedAllenamento);
			SedeField.setText(allenamentoDetails.sede.toString());
			CampoField.setText(allenamentoDetails.campo.getNomecampo());
			DataOraField.setText(allenamentoDetails.campo.getOrarioString());
			SportField.setText(allenamentoDetails.campo.getSport());
			AllenatoreField.setText(allenamentoDetails.allenatore.toString());
			ObservableList<Utente> listapartecipanti = FXCollections.observableArrayList(allenamentoDetails.partecipanti);
			PartecipantiLista.setItems(listapartecipanti);
		}catch(Exception e){}

	}

	public void CancelClicked() {
		gestioneAllCtrl.CancellaClicked(allenamentoDetails);
	}

	public void showBnd(){
		SedeField.setDisable(true);
		CampoField.setDisable(true);
		DataOraField.setDisable(true);
		SportField.setDisable(true);
		AllenatoreField.setDisable(true);
		PartecipantiLista.setDisable(true);
		cancellaButton.setDisable(true);
		SedeField.clear();
		CampoField.clear();
		DataOraField.clear();
		SportField.clear();
		AllenatoreField.clear();
		PartecipantiLista.setItems(FXCollections.emptyObservableList());
		partitaArrayList.remove(selectedAllenamento);
		ObservableList<Partita> lista = FXCollections.observableArrayList(partitaArrayList);
		listaAllenamenti.setItems(lista);
	}

	public void goBack(){
		gestioneAllCtrl.toMain();
	}

}
