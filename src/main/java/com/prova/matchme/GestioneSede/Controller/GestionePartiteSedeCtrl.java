package com.prova.matchme.GestioneSede.Controller;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AdminView;
import com.prova.matchme.CustomStage;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.Campo;
import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.Entity.Partita;
import com.prova.matchme.GestioneSede.Interfacce.*;
import com.prova.matchme.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class GestionePartiteSedeCtrl {


	private Stage s;
	private Gestore g;
	private VisualizzaDettagliPartitaSedeView controller;
	private Campo camposelected;
	public GestionePartiteSedeCtrl(Stage s,Gestore g){
		this.s=s;
		this.g=g;
	}
	public void toAdmin() {
		Utils.cambiaInterfaccia("FXML/Admin-view.fxml", s, c -> {
			return new AdminView(new AuthCtrl(s), g, s);
		});
	}

	public void tocreapartita(){
		CustomStage s=new CustomStage("Seleziona una data");
		Utils.cambiaInterfaccia("FXML/DialogCreaPartitaSede.fxml",s,c->{
			return new CreaPartitaSedeView(this,s);
		},340,180);

	}
	public void toVisualize(){
		CustomStage s=new CustomStage("Seleziona data");
		Utils.cambiaInterfaccia("FXML/DialogSelezionaData.fxml",s,c->{
			return new SelectDataView(s,this, LocalDate.now());
		},340,180);
	}

	public boolean CheckTime() {
		return false;
	}

	public void PassSquadraVincitrice() {

	}

	public void CalcolaPunteggio() {

	}

	public void AssegnaRisultatoCliccato() {

	}

	public void CloseWarningView() {

	}

	public void PassDate(LocalDate data) {
		ArrayList<Campo> listacampi=DBMSView.queryGetCampiLiberi2(g.getSede(),data);
		Utils.cambiaInterfaccia("FXML/Crea Partita sede 1.fxml",s,c->{
			return new CampiDisponibilisedeView(this,listacampi);
		});
	}

	public void PassCampo(Campo c) {
		camposelected=c;
		Utils.cambiaInterfaccia("FXML/Crea Partita sede 2.fxml",s,co->{
			return new InsertVincoliView(this,s);
		});
	}

	public void PassVincoli(String vincoli) {
		Partita p=new Partita(0,camposelected.getId_campo(),camposelected.getOrario(),"ppub",vincoli);
		DBMSView.queryCreaPartita(p,null);
		toAdmin();
	}

	public void CheckCapienza() {

	}

	public void InvitaGiocatoreCliccato() {

	}

	public void PassDati() {

	}

	public void PassPartitaNewData() {

	}

	public void getTime() {

	}

	public void getPartitePiu1ora() {

	}

	public void getPartite1ora() {

	}

	public void CheckNumeroPartite() {

	}

	public void PassPartita(Partita p) {
		controller.showDetails(DBMSView.queryGetCampoSedePartita(p));
	}

	public void PassVerificaPartite(LocalDate data) {
		Utils.cambiaInterfaccia("FXML/Visualizza Partite sede.fxml",s,c->{
			controller= new VisualizzaDettagliPartitaSedeView(this,DBMSView.queryGetPartiteSede(g.getSede(),data));
			return controller;
		});
	}

}
