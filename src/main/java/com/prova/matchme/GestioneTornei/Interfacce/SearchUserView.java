package com.prova.matchme.GestioneTornei.Interfacce;


import com.prova.matchme.Entity.Torneo;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneTornei.Controller.TorneiCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import com.prova.matchme.Utils;
import javafx.stage.Stage;


import java.util.ArrayList;

public class SearchUserView {


	private TorneiCtrl torneiCtrl;
	private Torneo torneo;
	private Stage stage;

	@FXML
	private TextField nomeGiocatore;
	@FXML
	private TextField cognomeGiocatore;

	@FXML
	private ListView<Utente> utentiCercati;
	private Utente selectedUtente;
	@FXML
	private Button buttonAggiungi;

	public SearchUserView(TorneiCtrl torneiCtrl, Torneo torneo, Stage stage) {

		this.torneiCtrl = torneiCtrl;
		this.torneo = torneo;
		this.stage = stage;
	}

	public void InsertData() {

	}

	@FXML
	public void ClickCerca() {
		if(cognomeGiocatore.getText().equals("") ||nomeGiocatore.getText().equals("")) {
			Utils.creaPannelloErrore("Devi inserire entrambi i campi");
		}else{
			torneiCtrl.PassData(nomeGiocatore.getText(), cognomeGiocatore.getText());
		}

	}

	public void MostraLista(ArrayList<Utente> listaGiocatori) {
		ObservableList<Utente> utenteList = FXCollections.observableArrayList(listaGiocatori);
		utentiCercati.setItems(utenteList);
		utentiCercati.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
			selectedUtente = newvalue;
			this.SelectGiocatore();
		});
	}
	@FXML
	public void SelectGiocatore() {
		buttonAggiungi.setDisable(false);
	}

	public void ClickAggiungi() {
		torneiCtrl.AggiungiASquadra(selectedUtente, torneo,stage);
	}

	public void back() {
		this.torneiCtrl.toMain();
	}

}
