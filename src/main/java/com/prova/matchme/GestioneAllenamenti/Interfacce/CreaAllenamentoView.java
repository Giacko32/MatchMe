package com.prova.matchme.GestioneAllenamenti.Interfacce;


import com.prova.matchme.Entity.Campo;
import com.prova.matchme.GestioneAllenamenti.Controller.CreaAllCtrl;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class CreaAllenamentoView {

	private CreaAllCtrl creaAllCtrl;
	private ArrayList<Campo> orari;
	private Campo campoSelected;
	@FXML
	public DatePicker datePicker;
	public ChoiceBox<String> sportChoice;
	public Spinner<Integer> partecipantiSpinner;
	public ChoiceBox<Campo> orariChoice;
	public Button buttonCrea;

	public CreaAllenamentoView(CreaAllCtrl creaAllCtrl){
		this.creaAllCtrl = creaAllCtrl;
	}

	@FXML
	public void initialize(){
		sportChoice.getItems().addAll("Calcio a 5", "Tennis singolo", "Tennis doppio", "Padel singolo", "Padel doppio");
		SpinnerValueFactory<Integer> n_partecipanti =
				new SpinnerValueFactory.IntegerSpinnerValueFactory(0,5,0);
		partecipantiSpinner.setValueFactory(n_partecipanti);
	}

	public void SelectParametri() {

	}

	public void ClickVerifica() {
		orariChoice.getItems().clear();
		buttonCrea.setDisable(true);
		orariChoice.getItems().clear();
		orari = creaAllCtrl.VerificaClicked(datePicker.getValue(), sportChoice.getValue());
		if(orari != null) {
			orariChoice.setDisable(false);
			for (Campo campo : orari) {
				orariChoice.getItems().add(campo);
			}
			orariChoice.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldvalue, newvalue) -> {
				this.campoSelected = newvalue;
				buttonCrea.setDisable(false);
			}));
		}
	}



	public void ClickCrea() {
		creaAllCtrl.Creaclicked(campoSelected, partecipantiSpinner.getValue());
		this.goBack();
	}

	public void goBack(){
		creaAllCtrl.toMain();
	}

}
