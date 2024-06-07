package com.prova.matchme.GestionePartita.Controller;


import com.prova.matchme.CustomStage;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestionePartita.Interfacce.DetailsTuttePartiteView;
import com.prova.matchme.GestionePartita.Interfacce.SelezionaSedeSportDataView;
import com.prova.matchme.Utils;
import javafx.stage.Stage;

public class PartitaCtrl {

	Utente u;

	public PartitaCtrl(Utente u){
		this.u = u;
		Utils.cambiaInterfaccia("FXML/DialogSelezionaSedeSportData.fxml", new CustomStage("Seleziona sede, sport e data"), c ->{
			return new SelezionaSedeSportDataView();
		}, 330, 220);
	}

	public void passPartita() {

	}

	public void passGiocatoreBonus(int id_player, int bonusValue) {

	}

	public Object getPartite1ora() {
		return null;
	}

	public Object getPartitePiu1ora() {
		return null;
	}

	public void passSedeSportData() {

	}

	public void passTipoPartita() {

	}

	public Object getTime() {
		return null;
	}

	public void passSedeSport() {

	}

	public boolean checkNumeroGiocatori() {
		return false;
	}

	public void deleteWarning() {

	}

	public void passGiocatoreSquadra() {

	}

	public void passRicercaGiocatore() {

	}

	public void passInvito() {

	}

	public void passSquadraOspite() {

	}

	public void passConferma() {

	}

	public void checkVincoli() {

	}

	public void passSquadra() {

	}

	public boolean VerificaDisponibilità() {
		return false;
	}

	public void SelectedCampo() {

	}

	public void SelectedPartita() {

	}

	public void AggiungiClicked() {

	}

	public void AggiungiOspiteClicked() {

	}

	public void CancelClicked() {

	}

	public void InvitaClicked() {

	}

	public void PartecipaClicked() {

	}

}
