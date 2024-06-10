package com.prova.matchme.GestionePartita.Interfacce;


import com.prova.matchme.Entity.PartitaDetails;
import com.prova.matchme.GestionePartita.Controller.PartitaCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AggiungiOspiteView {

	private PartitaCtrl partitaCtrl;
	private PartitaDetails partitaDetails;
	private Stage stg;
	@FXML
	private Button squadra1button;
	@FXML
	private Button squadra2button;

	public AggiungiOspiteView(PartitaCtrl partitaCtrl, PartitaDetails partitaDetails, Stage stg){
		this.partitaCtrl = partitaCtrl;
		this.partitaDetails = partitaDetails;
		this.stg = stg;
	}

	@FXML
	public void initialize(){
		switch (partitaDetails.campo.getSport()) {
			case "Calcio a 5" -> {
				if (partitaDetails.squadra1.size() == 5) {
					squadra2button.setDisable(false);
				} else if (partitaDetails.squadra2.size() == 5) {
					squadra1button.setDisable(false);
				} else {
					squadra1button.setDisable(false);
					squadra2button.setDisable(false);
				}
			}
			case "Tennis singolo", "Padel singolo" -> {
				if (partitaDetails.squadra1.size() == 1) {
					squadra2button.setDisable(false);
				} else if (partitaDetails.squadra2.size() == 1) {
					squadra1button.setDisable(false);
				}
			}
			case "Tennis doppio", "Padel doppio" -> {
				if (partitaDetails.squadra1.size() == 2) {
					squadra2button.setDisable(false);
				} else if (partitaDetails.squadra2.size() == 2) {
					squadra1button.setDisable(false);
				} else {
					squadra1button.setDisable(false);
					squadra2button.setDisable(false);
				}
			}
		}
	}

	public void selectSquadraOspite1() {
		partitaCtrl.passSquadraOspite(1, partitaDetails, stg);
	}

	public void selectSquadraOspite2() {
		partitaCtrl.passSquadraOspite(2, partitaDetails, stg);
	}

}
