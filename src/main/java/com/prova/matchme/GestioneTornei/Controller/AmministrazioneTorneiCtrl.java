package com.prova.matchme.GestioneTornei.Controller;



import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AdminView;
import com.prova.matchme.CustomStage;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.*;
import com.prova.matchme.GestioneTornei.Interfacce.*;
import com.prova.matchme.Utils;
import com.prova.matchme.shared.ConfirmView;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

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
	public AmministrazioneTorneiCtrl(Gestore gestore) {
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

	public void AccettaCliccato(Torneo torneo, String squadra) {
		if(CheckSquadre(torneo)){
			//il torneo ha posti liberi
			String[] parts = squadra.split(" nome squadra: ");

			String[] numeroSquadraPart = parts[0].split("Numero squadra: ");

			int numeroSquadra = Integer.parseInt(numeroSquadraPart[1].trim());
			String nomeSquadra = parts[1].trim();
			//squadra aggiunta nel torneo
			ArrayList<Utente> utentiSquadra = DBMSView.queryGetSquadra(numeroSquadra,nomeSquadra,torneo);
			DBMSView.queryPutSquadraTorneo(torneo,utentiSquadra , nomeSquadra);
			//Mandiamo la notifica
			Utils.creaPannelloErrore("Squadra iscritta");
			//mandiamo la notifica ai componenti
			ArrayList<UtentePart> utentiPart = new ArrayList<>();
			for (Utente u : utentiSquadra){
				utentiPart.add(new UtentePart(u,0));
			}
			String notifica = "Sei stato aggiunto al torneo " + torneo.toString() + " nella squadra " + nomeSquadra+ "\n tramite accettazione del gestore";
			DBMSView.sendNotify(notifica, utentiPart,1);
			DBMSView.queryRimuoviSquadraInAttesa(torneo,numeroSquadra,nomeSquadra);
		}else{
			//il torneo non ha posti liberi
			Utils.creaPannelloErrore("Torneo pieno");
		}
	}

	public void RifiutaCliccato(Torneo torneo, String squadra) {
		String[] parts = squadra.split(" nome squadra: ");

		String[] numeroSquadraPart = parts[0].split("Numero squadra: ");

		int numeroSquadra = Integer.parseInt(numeroSquadraPart[1].trim());
		String nomeSquadra = parts[1].trim();
		//squadra aggiunta nel torneo
		ArrayList<Utente> utentiSquadra = DBMSView.queryGetSquadra(numeroSquadra,nomeSquadra,torneo);
		//mandiamo la notifica ai componenti
		ArrayList<UtentePart> utentiPart = new ArrayList<>();
		for (Utente u : utentiSquadra){
			utentiPart.add(new UtentePart(u,0));
		}
		String notifica = "Squadra "+ nomeSquadra +  " rifiutata per il torneo " + torneo.toString();
		DBMSView.sendNotify(notifica, utentiPart,1);
		DBMSView.queryRimuoviSquadraInAttesa(torneo,numeroSquadra,nomeSquadra);
	}

	public boolean CheckSquadre(Torneo torneo) {
		//se true il numero di squadre è minore del massimo
		return DBMSView.queryGetNumeroSquadreTorneo(torneo) < torneo.getN_Squadre();
	}

	public boolean CheckSquadre2(Torneo torneo) {
		//se true il numero di squadre è minore del massimo
		return DBMSView.queryGetNumeroSquadreTorneo(torneo) == torneo.getN_Squadre();
	}


	public void CreaCalendario(){
		ArrayList<Torneo> tornei = DBMSView.queryGetTorneiSede(gestore);
		for(Torneo torneo : tornei){
			if(torneo.getData_inizio().isEqual(LocalDate.now().plusDays(3))){
				if(CheckSquadre2(torneo)){
					ArrayList<String> squadre = DBMSView.queryGetSquadre(torneo);
					this.AccoppiaSquadre(squadre, torneo);
				}else{

				}
			}

		}
	}

	public void AccoppiaSquadre(ArrayList<String> squadre, Torneo torneo){
		ArrayList<Integer> id = new ArrayList<>();
		ArrayList<String> nomi = new ArrayList<>();
		ArrayList<PartiteTorneo> partite = new ArrayList<>();

		for(String squadra : squadre){
			String[] parts = squadra.split(" nome squadra: ");

			String[] numeroSquadraPart = parts[0].split("Numero squadra: ");

			int numeroSquadra = Integer.parseInt(numeroSquadraPart[1].trim());
			id.add(numeroSquadra);
			nomi.add(parts[1].trim());
		}
		Random rand  = new Random();
		for(int i = 0; i< torneo.getN_Squadre()/2; i++){
			int index1 = rand.nextInt(id.size());
			int id1 = id.remove(index1);
			int index2 = rand.nextInt(id.size());
			int id2 = id.remove(index2);
			String nome1 = nomi.remove(index1);
			String nome2 = nomi.remove(index2);
			partite.add(new PartiteTorneo(id1,id2,nome1,nome2));
		}
		System.out.println(partite.size());
		int numeroPartite = partite.size();
		int i = partite.size();
		while(i > 1){
			i/=2;
			numeroPartite+=i;
		}
		System.out.println(numeroPartite);
		for(PartiteTorneo p : partite){
			ArrayList<Campo> campi = DBMSView.queryGetCampiLiberi(new Sede(torneo.getRef_Sede()),torneo.getSport(),torneo.getData_inizio());
		}


	}


	public void toMain () {
		if (gestore != null) {
			Utils.cambiaInterfaccia("FXML/Admin-view.fxml", stage, c -> {
					return new AdminView(new AuthCtrl(stage), gestore, stage);
				});
		}
	}

}
