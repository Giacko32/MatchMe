package com.prova.matchme.GestioneTornei.Controller;



import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AdminView;
import com.prova.matchme.CustomStage;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.Entity.Torneo;
import com.prova.matchme.GestioneTornei.Interfacce.*;
import com.prova.matchme.Utils;
import com.prova.matchme.shared.ConfirmView;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class AmministrazioneTorneiCtrl {

	private Stage stage;
	private Gestore gestore;
	private VisualizzaTorneiGestori boundaryGestori;
	private Torneo torneo;
	private CreaTorneoView boundaryCreaTorneo;
	private ConfirmView boundaryConfirm;
	private ModificaTorneoView boundaryModificaTorneo;
	private SquadreInAttesaView boundarySquadreInAttesa;



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

	public void PassData(String sport, int livelloMin,int livelloMax, int numeroSquadre, String dataInizio, String dataFine, int numeroGiocatoriPerSquadra) {
		//query creeazione torneo
		DBMSView.queryCreateTorneo(sport, livelloMin, livelloMax, numeroSquadre, dataInizio, dataFine, gestore.getSede(), numeroGiocatoriPerSquadra);
		ArrayList<Integer> lista_id = DBMSView.queryGetTuttiGiocatori();
		//notifica
		String notifica = "E' stato creato un nuovo torneo nella sede " + gestore.getSede() + " corri a iscriverti!!";
		for(Integer id : lista_id) {
			DBMSView.sendNotify2(notifica,id,1);
		}
	}

	public void ModificaTorneo(Torneo torneo, Stage stage) {
		stage.close();
		Utils.cambiaInterfaccia("FXML/Modifica Torneo.fxml", stage, c -> {
			boundaryModificaTorneo = new ModificaTorneoView(stage, this, torneo);
			return boundaryModificaTorneo;
		});
	}

	public void CancellaTorneo(Torneo torneo) {
		CustomStage st = new CustomStage("Attenzione");
		Utils.cambiaInterfaccia("FXML/DialogConferma.fxml", st, c -> {
			boundaryConfirm = new ConfirmView(this, st,  torneo);
			return boundaryConfirm;
		} ,350,170);
	}

	public void CloseConfirmView(Torneo torneo) {
		//eliminiamo il torneo
		DBMSView.queryDeleteTorneo(torneo);
		//squadra eliminata
		//rimuoviamo il torneo dalla listview
		this.toMain();
	}

	public void PassDataModifica(Torneo torneo, LocalDate data_inizo, LocalDate data_fine) {
		DBMSView.queryUpdateTorneo(torneo, data_inizo, data_fine);
		Utils.creaPannelloErrore("Torneo modificato");
		this.toMain();
	}

	public void SquadreInAttesa(Torneo torneo, Stage stage) {
		stage.close();
		Utils.cambiaInterfaccia("FXML/AccettazioneSquadreTorneo.fxml", stage, c -> {
			boundarySquadreInAttesa = new SquadreInAttesaView(this, stage, torneo, DBMSView.queryGetSquadreInAttesa(torneo));
			return boundarySquadreInAttesa;
		});
	}

	public void PassSquadra(String squadra, Torneo torneo) {
		String[] parts = squadra.split(" nome squadra: ");

		String[] numeroSquadraPart = parts[0].split("Numero squadra: ");

		int numeroSquadra = Integer.parseInt(numeroSquadraPart[1].trim());
		String nomeSquadra = parts[1].trim();
		boundarySquadreInAttesa.MostraSquadra(DBMSView.queryGetSquadra(numeroSquadra,nomeSquadra, torneo));
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
					return new AdminView(new AuthCtrl(stage), gestore, stage);
				});
		}
	}

}
