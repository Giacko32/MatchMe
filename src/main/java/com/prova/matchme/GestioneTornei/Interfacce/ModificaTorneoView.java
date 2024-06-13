package com.prova.matchme.GestioneTornei.Interfacce;


import com.prova.matchme.Entity.Torneo;
import com.prova.matchme.GestioneTornei.Controller.AmministrazioneTorneiCtrl;
import com.prova.matchme.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

public class ModificaTorneoView {

	private Stage stage;
	private AmministrazioneTorneiCtrl amminCtrl;
	private Torneo torneo;

	@FXML
	private ComboBox<String> sportComboBox;

	@FXML
	private Spinner<Integer> livelloMinSpinner;

	@FXML
	private Spinner<Integer> livelloMaxSpinner;

	@FXML
	private Spinner<Integer> numeroSquadreSpinner;

	@FXML
	private DatePicker dataInizioPicker;

	@FXML
	private DatePicker dataFinePicker;


	public ModificaTorneoView(Stage stage, AmministrazioneTorneiCtrl amminCtrl, Torneo torneo) {
		this.stage = stage;
		this.amminCtrl = amminCtrl;
		this.torneo = torneo;
	}
	@FXML
	public void initialize(){
		Integer livelloMin = Integer.parseInt(torneo.getVincoli().split(",")[0]);
		Integer livelloMax = Integer.parseInt(torneo.getVincoli().split(",")[1]);
		sportComboBox.setValue(this.torneo.getSport());
		livelloMinSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(livelloMin, livelloMin, livelloMin));
		livelloMaxSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(livelloMax, livelloMax, livelloMax));
		numeroSquadreSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(torneo.getN_Squadre(), torneo.getN_Squadre(), torneo.getN_Squadre()));
		dataInizioPicker.setValue(torneo.getData_inizio());
		dataFinePicker.setValue(torneo.getData_fine());



	}

	public void ModifyData() {

	}

	public void clickConferma() {
		if(torneo.getData_inizio() == dataInizioPicker.getValue() && torneo.getData_fine() == dataFinePicker.getValue()) {
			Utils.creaPannelloErrore("Non hai effettuato modifiche");
		}else{
			amminCtrl.PassDataModifica(torneo, dataInizioPicker.getValue(), dataFinePicker.getValue());
		}

	}

	@FXML
	public void back() {
		this.amminCtrl.toMain();
	}

}
