package com.prova.matchme.GestioneTornei.Controller;


import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AllenaView;
import com.prova.matchme.Autenticazione.Interfacce.MainView;
import com.prova.matchme.CustomStage;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.Torneo;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneTornei.Interfacce.*;
import com.prova.matchme.Utils;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TorneiCtrl {

	private Utente utente;
	private Stage stage;
	private VisualizzaDettagliMioTorneo boundaryMio;
	private VisualizzaDettagliTuttiITornei boundaryTutti;
	private IscrizioneSquadraView boundarySquadra;
	private SearchUserView boundarySearchUser;
	private int numeroSquadraCorrente = 1;

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
			boundaryTutti = new VisualizzaDettagliTuttiITornei(DBMSView.queryGetTuttITornei(),this, stage);
			return boundaryTutti;
		});
	}

	public void MieiTornei(Stage st) {
		st.close();
		Utils.cambiaInterfaccia("FXML/VisualizzaIMieiTornei.fxml", stage, c -> {
			boundaryMio =  new VisualizzaDettagliMioTorneo(DBMSView.queryGetImieiTornei(utente),this);
			return boundaryMio;
		});

	}

	public void TorneoSelezionatoMio(Torneo torneo) {

		boundaryMio.showDetails(DBMSView.queryGetTorneo(torneo));
	}
	public void TorneoSelezionatoTutti(Torneo torneo) {

		boundaryTutti.showDetails(DBMSView.queryGetTorneo(torneo));
	}

	public void IscriviSelezionato(Torneo torneo, Stage st) {
		if (this.CheckNumeroSquadre(torneo)) {
			st.close();
			Utils.cambiaInterfaccia("FXML/IscrizioneSquadraTorneo.fxml", stage, c -> {
				boundarySquadra = new IscrizioneSquadraView(this, utente, stage, torneo);
				return boundarySquadra;
			});
		} else {
			Utils.creaPannelloErrore("Il torneo selezionato è pieno");
		}
	}

		public void AggiungiPartecipanti (TorneiCtrl torneiCtrl, Stage st, Torneo torneo) {
			st.close();
			Utils.cambiaInterfaccia("FXML/SearchUserView.fxml", stage, c -> {
				boundarySearchUser =  new SearchUserView(this, torneo);
				return boundarySearchUser;
			});
		}

		public boolean CheckSquadra (Torneo torneo) {
		//se il numero di giocatori è minore del numero massimo per squadra torna true sennò false
		return DBMSView.queryGetNumeroPartecipantiSquadreTorneo(torneo,numeroSquadraCorrente ) < torneo.getN_Giocatori_squadra();

		}

		public void PassData (String nome, String cognome) {
			ArrayList<Utente> giocatori_cercati = DBMSView.queryGetGiocatoriRicercati(nome, cognome);
			System.out.println(giocatori_cercati);
			boundarySearchUser.MostraLista(giocatori_cercati);
		}

		public void AggiungiASquadra () {

		}

		public void IscriviSquadraCliccato () {

		}

		public void CheckLivello () {

		}

		public void CloseWarningView () {

		}

		public void passCancellazione () {

		}

		public void CloseConfirmView () {

		}

		public boolean CheckNumeroSquadre (Torneo torneo){
			//se true il numero di squadre è minore del massimo
			return DBMSView.queryGetNumeroSquadreTorneo(torneo) < torneo.getN_Squadre();

		}

		public void toMain () {
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
