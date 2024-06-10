package com.prova.matchme.GestioneSede.Controller;


import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AdminView;
import com.prova.matchme.CustomStage;
import com.prova.matchme.DBMSView;
import com.prova.matchme.EmailSender;
import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneSede.Interfacce.*;
import com.prova.matchme.Utils;
import com.prova.matchme.shared.ConfirmView;
import com.prova.matchme.shared.WarningView;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AmministrazioneSedeCtrl {

	private Stage s;
	private Gestore g;
	private SearchUtentiView controllersuv;
	private Utente utenteselected;
	private StringBuilder codice;
	private SearchNonTesseratoView controllersntv;
	private String datascadenza;
	private LocalDate date;

	public AmministrazioneSedeCtrl(Stage s,Gestore g){
		this.s=s;
		this.g=g;
	}

	public void toAdmin(){
		Utils.cambiaInterfaccia("FXML/Admin-view.fxml", s, c -> {
			return new AdminView(new AuthCtrl(s), g,s);
		});
	}

	public void toabilita(){
		Utils.cambiaInterfaccia("FXML/AbilitaAllenatore.fxml",s,c->{
			controllersuv=new SearchUtentiView(this);
			return controllersuv;
		});
	}

	public void tocreateabb(){
		Utils.cambiaInterfaccia("FXML/Aggiungi Abbonamento.fxml",s,c->{
			controllersntv=new SearchNonTesseratoView(this);
			return controllersntv;
		});
	}
	public void toverificaSconto(){
		CustomStage s=new CustomStage("VERIFICA SCONTO");
		Utils.cambiaInterfaccia("FXML/Verifica Codice Sconto.fxml",s,c->{
			return new VerificaScontoView(this,s);
		},380,170);
	}

	public void passCodice(String codice) {
       if(!codice.isEmpty()){
		   utenteselected=DBMSView.queryGetGiocatoreSconto(codice);
		   if(checkGiocatoreSconto(utenteselected)){
			   CustomStage s=new CustomStage("DETTAGLI");
			   Utils.cambiaInterfaccia("FXML/DialogDettagliSconto.fxml",s,c->{
				   return new DettagliGiocatoreSconto(s,this,utenteselected);
			   },385,400);
		   }else{
			   CustomStage s=new CustomStage("ATTENZIONE");
			   Utils.cambiaInterfaccia("FXML/DialogScontoErrato.fxml",s,c->{
				   return new WarningView(s);
			   },350,200);
			   toAdmin();
		   }
	   }
	}
	public boolean checkGiocatoreSconto(Utente u) {
		if(u==null){
			return false;
		}else {
			return true;
		}
	}
	public void passSconto() {
		DBMSView.queryScontoApplicato(utenteselected.getId());
		toAdmin();
	}

	public void passSearchField(String parametri) {
		if(!parametri.equals("")){
			ArrayList<Utente> listautenti=DBMSView.queryGetUtenti(parametri);
			controllersuv.mostraListaTesserati(listautenti);
		}
	}

	public void passSearchParameter(String parametri) {
		if(!parametri.equals("")){
			ArrayList<Utente> listanontesserati=DBMSView.querySearchNonTesserati(parametri);
			controllersntv.mostraListaNonTesserati(listanontesserati);
		}
	}

	public void passNewAllenatore(Utente u) {
		if(u!=null){
			utenteselected=u;
			CustomStage s=new CustomStage("ATTENZIONE");
			Utils.cambiaInterfaccia("FXML/DialogConfermaAbilitazione.fxml", s, c -> {
				return new ConfirmView(this, s);
			}, 350, 160);
		}
	}

	public void confirmAbilitazione() {
		DBMSView.queryAttivaAllenatore(utenteselected.getId());
		this.toAdmin();
	}

	public void PassNonTesserato(Utente u) {
		utenteselected=u;
		Utils.cambiaInterfaccia("FXML/Conferma Nuovo Abbonamento.fxml", s, c -> {
			return new AbbonamentoView(this,u);
		});


	}
	public void PassDurata(int durata) {
		GeneraCodice();
		CalcolaDataFine(durata);
		InviaMail();
		DBMSView.querySetAbbonamento(codice.toString(),date, utenteselected.getId());
		toAdmin();
	}

	public void GeneraCodice() {
		codice=new StringBuilder();
		char[] lettere= "abcdefghijkmlmnopqrstuvwxyz1234567890".toCharArray();
		for(int i=0;i<7;i++){
			codice.append(lettere[((int)(Math.random()*lettere.length))]);
		}
	}

	public void CalcolaDataFine(int durata) {
		LocalDate today = LocalDate.now();
		int mesiDaSommare = durata;
		date = today.plusMonths(mesiDaSommare);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		datascadenza = date.format(formatter);
	}

	public void InviaMail() {
		EmailSender.sendNewAbbonamento(codice.toString(),utenteselected.getEmail(),datascadenza);
	}

	public boolean checkTime() {
		return false;
	}

	public void CheckNumeroAbbonamenti() {

	}

}
