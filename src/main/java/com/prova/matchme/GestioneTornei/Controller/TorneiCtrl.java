package com.prova.matchme.GestioneTornei.Controller;


import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AllenaView;
import com.prova.matchme.Autenticazione.Interfacce.MainView;
import com.prova.matchme.CustomStage;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.Torneo;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneTornei.Interfacce.SelezioneTorneiView;
import com.prova.matchme.GestioneTornei.Interfacce.VisualizzaDettagliMioTorneo;
import com.prova.matchme.GestioneTornei.Interfacce.VisualizzaDettagliTuttiITornei;
import com.prova.matchme.Utils;
import javafx.stage.Stage;

public class TorneiCtrl {

	private Utente utente;
	private Stage stage;
	private VisualizzaDettagliMioTorneo boundary;

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
			return new VisualizzaDettagliTuttiITornei(DBMSView.queryGetTuttITornei(),this);
		});
	}

	public void MieiTornei(Stage st) {
		st.close();
		Utils.cambiaInterfaccia("FXML/VisualizzaIMieiTornei.fxml", stage, c -> {
			return new VisualizzaDettagliMioTorneo(DBMSView.queryGetImieiTornei(utente),this);
		});
	}

	public void TorneoSelezionato(Torneo torneo) {
		boundary.showDetails(DBMSView.queryGetDetailsCampo(torneo));
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

	public void toMain() {
		if (utente != null) {
			if (utente.getTipo().equals("al")) {
				Utils.cambiaInterfaccia("FXML/Allena-view.fxml", stage, c -> {
					return new AllenaView(new AuthCtrl(stage), utente, stage);
				});
			} else {
				Utils.cambiaInterfaccia("FXML/Main-view.fxml", stage, c -> {
					return new MainView(new AuthCtrl(stage), utente, stage);
				});
			}
		}
	}

}
