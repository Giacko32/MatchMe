package com.prova.matchme.GestioneSede.Controller;

import com.prova.matchme.CustomStage;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.GestioneSede.Interfacce.SelectDataView;
import com.prova.matchme.GestioneSede.Interfacce.VisualizzaDettagliPartitaSedeView;
import com.prova.matchme.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;

public class GestionePartiteSedeCtrl {


	private Stage s;
	private Gestore g;
	public GestionePartiteSedeCtrl(Stage s,Gestore g){
		this.s=s;
		this.g=g;
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

	public void PassDate() {

	}

	public void PassCampo() {

	}

	public void PassVincoli() {

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

	public void PassPartita() {

	}

	public void PassVerificaPartite(LocalDate data) {
		Utils.cambiaInterfaccia("FXML/Visualizza Partite sede.fxml",s,c->{
			return new VisualizzaDettagliPartitaSedeView(this,DBMSView.queryGetPartiteSede(g.getSede(),data));
		});
	}

}
