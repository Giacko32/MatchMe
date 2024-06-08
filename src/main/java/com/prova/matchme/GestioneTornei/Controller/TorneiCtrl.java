package com.prova.matchme.GestioneTornei.Controller;


import com.prova.matchme.CustomStage;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestionePartita.Interfacce.DetailsMiaPartitaView;
import com.prova.matchme.GestionePartita.Interfacce.SelezionaSedeSportDataView;
import com.prova.matchme.GestioneTornei.Interfacce.SelezioneTorneiView;
import com.prova.matchme.GestioneTornei.Interfacce.VisualizzaDettagliMioTorneo;
import com.prova.matchme.GestioneTornei.Interfacce.VisualizzaDettagliTuttiITornei;
import com.prova.matchme.Utils;
import javafx.stage.Stage;

public class TorneiCtrl {

	private Utente utente;
	private Stage stage;

	public TorneiCtrl(Utente utente, Stage stage) {
		this.utente = utente;
		this.stage = stage;
		Stage stageBoundary = new CustomStage("Seleziona la tipologia di tornei");
		Utils.cambiaInterfaccia("FXML/SelezioneTornei.fxml", stageBoundary, c -> {
			return new SelezioneTorneiView( this, stageBoundary);
		}, 350, 170);
	}

	public void TuttiItornei(Stage st) {
		st.close();
		Utils.cambiaInterfaccia("FXML/VisualizzaTornei.fxml", stage, c -> {
			return new VisualizzaDettagliTuttiITornei(DBMSView.queryGetTuttITornei());
		});
	}

	public void MieiTornei(Stage st) {
		st.close();
		Utils.cambiaInterfaccia("FXML/VisualizzaIMieiTornei.fxml", stage, c -> {
			return new VisualizzaDettagliMioTorneo(DBMSView.queryGetImieiTornei(utente));
		});
	}

	public void TorneoSelezionato() {

	}

	public void IscriviSelezionato() {

	}

	public void AggiungiPartecipanti() {

	}

	public void CheckSquadra() {

	}

	public void PassData() {

	}

	public void AggiungiASquadra() {

	}

	public void IscriviSquadraCliccato() {

	}

	public void CheckLivello() {

	}

	public void CloseWarningView() {

	}

	public void passCancellazione() {

	}

	public void CloseConfirmView() {

	}

	public void CheckNumeroSquadre() {

	}

}
