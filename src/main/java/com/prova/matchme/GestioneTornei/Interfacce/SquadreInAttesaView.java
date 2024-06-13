package com.prova.matchme.GestioneTornei.Interfacce;


import com.prova.matchme.Entity.Torneo;
import com.prova.matchme.GestioneTornei.Controller.AmministrazioneTorneiCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SquadreInAttesaView {

	private AmministrazioneTorneiCtrl amminCtrl;
	private Stage stage;
	private Torneo torneo;
	private ArrayList<String> squadreAttesa;
	@FXML
	private ListView<String> listaSquadreAttesa;
	private String selectedSquadra;

	public SquadreInAttesaView(AmministrazioneTorneiCtrl amminCtrl, Stage stage, Torneo torneo, ArrayList<String> squadreAttesa) {
		this.amminCtrl = amminCtrl;
		this.stage = stage;
		this.torneo = torneo;
		this.squadreAttesa = squadreAttesa;
	}

	@FXML
	public void initialize(){
		ObservableList<String> squadrelist = FXCollections.observableArrayList(squadreAttesa);
		listaSquadreAttesa.setItems(squadrelist);
		listaSquadreAttesa.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
			selectedSquadra = newvalue;

		});
	}

	public void SelectSquadra() {

	}

	public void MostraSquadra() {

	}

	public void clickAccetta() {

	}

	public void EliminaSquadra() {

	}

	public void clickRifiuta() {

	}

	public void ShowBnd() {

	}

	@FXML
	public void back() {
		this.amminCtrl.toMain();
	}

}
