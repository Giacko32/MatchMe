package com.prova.matchme.GestioneTornei.Controller;



import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AdminView;
import com.prova.matchme.CustomStage;
import com.prova.matchme.shared.DBMSView;
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
	private CreaNuovoTorneoView boundaryCreaTorneo;
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
			boundaryCreaTorneo = new CreaNuovoTorneoView(stage, this);
			return boundaryCreaTorneo;
		});
	}

	public void PassData(String sport, int livelloMin,int livelloMax, int numeroSquadre, String dataInizio, String dataFine, int numeroGiocatoriPerSquadra) {
		//query creeazione torneo
		if(java.sql.Date.valueOf(dataInizio).toLocalDate().isAfter(LocalDate.now().plusDays(3)) && java.sql.Date.valueOf(dataFine).toLocalDate().isAfter(java.sql.Date.valueOf(dataInizio).toLocalDate())){
			DBMSView.queryCreateTorneo(sport, livelloMin, livelloMax, numeroSquadre, dataInizio, dataFine, gestore.getSede(), numeroGiocatoriPerSquadra);
			ArrayList<Integer> lista_id = DBMSView.queryGetTuttiGiocatori();
			//notifica
			String notifica = "E' stato creato un nuovo torneo nella sede " + gestore.getSede() + " corri a iscriverti!!";
			for(Integer id : lista_id) {
				DBMSView.sendNotify2(notifica,id,1);
			}
		}else{
			Utils.creaPannelloErrore("La data inserita è sbagliata");
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
		this.mostraTorneiSede(stage);
	}

	public void PassDataModifica(Torneo torneo, LocalDate data_inizo, LocalDate data_fine) {
		if(data_inizo.isAfter(LocalDate.now().plusDays(3)) && data_fine.isAfter(data_inizo)) {
			DBMSView.queryUpdateTorneo(torneo, data_inizo, data_fine);
			Utils.creaPannelloErrore("Torneo modificato");
			this.mostraTorneiSede(stage);
		}else{
			Utils.creaPannelloErrore("Data errata");
			this.mostraTorneiSede(stage);
		}
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
					if(!DBMSView.queryCheckCalendario(torneo)){
						this.AccoppiaSquadre(squadre, torneo);
					}

				}else{
					String notifica = "Il torneo non ha raggiunto il numero di squdare necessario";
					ArrayList<Integer> partecipanti = DBMSView.queryGetPartecipantiTorneo(torneo);
					for(int k : partecipanti){
						DBMSView.sendNotify2(notifica, k, 1);
					}
					DBMSView.queryDeleteTorneo(torneo);
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
		int numeroGiornate = torneo.getData_fine().compareTo(torneo.getData_inizio());
		System.out.println(numeroGiornate);
		System.out.println(numeroPartite);
		int partitePerGiornata = numeroPartite/numeroGiornate;
		if(partitePerGiornata == 0){
			partitePerGiornata = 1;
		}
		int y = 1;
		for(int j = partite.size() ; j < numeroPartite; j+=2){

			partite.add(new PartiteTorneo(j*2+1,j*2+2,("vincitore: " + y), ("vincotore: " + (y+1))));
			y++;
		}

		LocalDate data = torneo.getData_inizio();
		try{
			while(numeroPartite > 0){
				for(int k = 0; k < partitePerGiornata ; k++){
					ArrayList<Campo> campi = DBMSView.queryGetCampiLiberi(new Sede(torneo.getRef_Sede()),torneo.getSport(),data);
					System.out.println(campi.size());
					Campo c = campi.remove(0);
					PartiteTorneo p = new PartiteTorneo();
					if(partite.size() > 0){
						 p = partite.remove(0);
					}

					p.setCampo(c);

					DBMSView.queryCreaPartitaCalendario(p,torneo);
					numeroPartite--;
				}

			}
		}catch(Exception e){
			e.printStackTrace();
		}


		ArrayList<Integer> partecipanti = DBMSView.queryGetPartecipantiTorneo(torneo);
		String notifica = "E' stato creato il calendario per il torneo " + torneo.getId() + " per lo sport " + torneo.getSport();
		for(int k : partecipanti){
			DBMSView.sendNotify2(notifica, k, 1);
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
