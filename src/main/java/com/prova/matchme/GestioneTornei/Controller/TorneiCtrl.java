package com.prova.matchme.GestioneTornei.Controller;


import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AllenaView;
import com.prova.matchme.Autenticazione.Interfacce.MainView;
import com.prova.matchme.CustomStage;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.Torneo;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.Entity.UtentePart;
import com.prova.matchme.GestioneTornei.Interfacce.*;
import com.prova.matchme.Utils;
import com.prova.matchme.shared.ConfirmView;
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
	private ArrayList<Utente> utentiSquadra;
	private boolean giocatori_aggiunti = false;
	private ConfirmView boundaryConfirm;

	public TorneiCtrl(Utente utente, Stage stage) {
		this.utente = utente;
		this.stage = stage;
		this.utentiSquadra = new ArrayList<>();
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
				boundarySquadra = new IscrizioneSquadraView(this, utente, stage, torneo,numeroSquadraCorrente);
				return boundarySquadra;
			});
		} else {
			Utils.creaPannelloErrore("Il torneo selezionato è pieno");
		}
	}

		public void AggiungiPartecipanti (TorneiCtrl torneiCtrl, Stage st, Torneo torneo) {
		if(giocatori_aggiunti == false){
			st.close();
			Utils.cambiaInterfaccia("FXML/SearchUserView.fxml", stage, c -> {
				boundarySearchUser =  new SearchUserView(this, torneo,stage);
				return boundarySearchUser;
			});
		}else{
			Utils.creaPannelloErrore("Giocatori già aggiunti");
		}
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

		public void AggiungiASquadra (Utente utente, Torneo torneo, Stage st) {
				if(this.CheckSquadra(torneo)){
					// la squadra ancora non è piena
					if(!utentiSquadra.contains(utente)){
						utentiSquadra.add(utente);
						System.out.println(utentiSquadra);
						if(utentiSquadra.size() == torneo.getN_Giocatori_squadra()){
							//la squadra è piena possiamo aggiornare la lista
							st.close();
							Utils.cambiaInterfaccia("FXML/IscrizioneSquadraTorneo.fxml", stage, c -> {
								boundarySquadra = new IscrizioneSquadraView(this, utente, stage, torneo,numeroSquadraCorrente);

								return boundarySquadra;
							});
							boundarySquadra.updateListaGiocatori(utentiSquadra);
							giocatori_aggiunti = true;
						}else {
							Utils.creaPannelloErrore("Giocatore aggiunto");
						}
					}else{
						Utils.creaPannelloErrore("Giocatore già aggiunto");
					}

				}else{
					//la squadra è piena
					Utils.creaPannelloErrore("La squadra ha raggiunto il numero massimo di partecipanti");
				}
		}

		public void IscriviSquadraCliccato (Torneo torneo, float media) {
			if(CheckLivello(torneo, media)){
				//La squadra soddisfa i vincoli
				DBMSView.queryPutSquadraTorneo(torneo,numeroSquadraCorrente,utentiSquadra);
				Utils.creaPannelloErrore("Squadra iscritta");
				//mandiamo la notifica ai componenti
				ArrayList<UtentePart> utentiPart = new ArrayList<>();
				for (Utente u : utentiSquadra){
					utentiPart.add(new UtentePart(u,0));
				}
				String notifica = "Sei stato aggiunto al torneo " + torneo.toString() + " nella squadra" + numeroSquadraCorrente;
				DBMSView.sendNotify(notifica, utentiPart,1);
				numeroSquadraCorrente++;
				this.toMain();
			}else{
				//la squadra non soddisfa i vincoli
				DBMSView.queryPutSquadraInAttesa(torneo, numeroSquadraCorrente, utentiSquadra);
				Utils.creaPannelloErrore("Non rispetta i vincoli");
				this.toMain();
			}
		}

		public boolean CheckLivello (Torneo torneo,float media ) {
			String[] parts = torneo.getVincoli().split(",");

			// Estrae i valori minimo e massimo dalla stringa
			float minimo = Float.parseFloat(parts[0].trim());
			float massimo = Float.parseFloat(parts[1].trim());

			// Confronta il valore con i valori minimo e massimo
			return media >= minimo && media <= massimo;
		}

		public void CloseWarningView () {

		}

		public void passCancellazione(Torneo torneo) {
			CustomStage st = new CustomStage("Attenzione");
			Utils.cambiaInterfaccia("FXML/DialogConferma.fxml", st, c -> {
				boundaryConfirm = new ConfirmView(this, st, utente , torneo);

				return boundaryConfirm;
			});
		}

		public void CloseConfirmView (Utente utente, Torneo torneo) {
			//cancelliamo la squadra e mandiamo la notifica
			int numeroSquadraEliminato = DBMSView.getNumeroSquadraUtenteTorneo(torneo, utente);
			DBMSView.queryDeleteSquadraTorneo(torneo, utente, numeroSquadraEliminato);
			//squadra eliminata
			//rimuoviamo il torneo dalla listview
			boundaryMio.rimuoviTorneo(torneo);


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
