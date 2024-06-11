package com.prova.matchme.GestioneTornei.Interfacce;


import com.prova.matchme.Entity.Torneo;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneTornei.Controller.TorneiCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


import java.util.ArrayList;

public class SearchUserView {


	private TorneiCtrl torneiCtrl;
	private Torneo torneo;

	@FXML
	private TextField nomeGiocatore;
	@FXML
	private TextField cognomeGiocatore;

	@FXML
	private ListView<Utente> utentiCercati;
	private Utente selectedUtente;

	public SearchUserView(TorneiCtrl torneiCtrl, Torneo torneo) {

		this.torneiCtrl = torneiCtrl;
		this.torneo = torneo;
	}

	public void InsertData() {

	}

	@FXML
	public void ClickCerca() {
		System.out.println("Pulsante Cerca cliccato.");
		torneiCtrl.PassData(nomeGiocatore.getText(), cognomeGiocatore.getText());
	}

	public void MostraLista(ArrayList<Utente> listaGiocatori) {
		ObservableList<Utente> utenteList = FXCollections.observableArrayList(listaGiocatori);
		System.out.println("Aggiornamento lista con " + utenteList.size() + " giocatori.");
		utentiCercati.setItems(utenteList);
		utentiCercati.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
			selectedUtente = newvalue;
			this.SelectGiocatore();
		});
	}

	public void SelectGiocatore() {

	}

	public void ClickAggiungi() {

	}

	public void back() {
		this.torneiCtrl.toMain();
	}

}
