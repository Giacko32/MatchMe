package com.prova.matchme.GestioneTornei.Controller;


import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AdminView;
import com.prova.matchme.Autenticazione.Interfacce.AllenaView;
import com.prova.matchme.Autenticazione.Interfacce.MainView;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.Entity.Torneo;
import com.prova.matchme.GestioneTornei.Interfacce.CreaTorneoView;
import com.prova.matchme.GestioneTornei.Interfacce.VisualizzaDettagliTuttiITornei;
import com.prova.matchme.GestioneTornei.Interfacce.VisualizzaTorneiGestori;
import com.prova.matchme.Utils;
import javafx.stage.Stage;

public class AmministrazioneTorneiCtrl {

	private Stage stage;
	private Gestore gestore;
	private VisualizzaTorneiGestori boundaryGestori;
	private Torneo torneo;
	private CreaTorneoView boundaryCreaTorneo;

	public AmministrazioneTorneiCtrl(Stage stage, Gestore gestore) {
		this.stage = stage;
		this.gestore = gestore;
	}

	public void mostraTorneiSede(Stage stage){
		stage.close();
		Utils.cambiaInterfaccia("FXML/VisualizzaTorneiGestore.fxml", stage, c -> {
			boundaryGestori = new VisualizzaTorneiGestori(DBMSView.queryGetTorneiSede(gestore),stage, this);
			return boundaryGestori;
		});
	}

	public void TorneoSelezionato(Torneo torneo) {
		boundaryGestori.showDettagliTorneo(DBMSView.queryGetTorneo(torneo));
	}

	public void NuovoTorneo(Stage stage) {
		stage.close();
		Utils.cambiaInterfaccia("FXML/CreaTorneo.fxml", stage, c -> {
			boundaryCreaTorneo = new CreaTorneoView(stage, this);
			return boundaryCreaTorneo;
		});
	}

	public void PassData(String sport, int livello, int numeroSquadre, String dataInizio, String dataFine) {
		//query creeazione torneo
		DBMSView.queryCreateTorneo(sport, livello, numeroSquadre, dataInizio, dataFine, gestore.getSede());

	}

	public void ModificaTorneo() {

	}

	public void CancellaTorneo() {

	}

	public void CloseConfirmView() {

	}

	public void SquadreInAttesa() {

	}

	public void PassSquadra() {

	}

	public void AccettaCliccato() {

	}

	public void RifiutaCliccato() {

	}

	public void CheckSquadre() {

	}

	public void CloseWarningView() {

	}

	public void checkTime() {

	}

	public void AccoppiaSquadre() {

	}

	public void MatchPartitaCampo() {

	}

	public void checkNumeroTornei() {

	}

	public void checkNumeroPartite() {

	}

	public void toMain () {
		if (gestore != null) {
			Utils.cambiaInterfaccia("FXML/Admin-view.fxml", stage, c -> {
					return new AdminView(this, gestore, stage);
				});
		}
	}

}
