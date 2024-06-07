package com.prova.matchme.GestionePartita.Controller;


import com.prova.matchme.CustomStage;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.Sede;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestionePartita.Interfacce.DetailsTuttePartiteView;
import com.prova.matchme.GestionePartita.Interfacce.SelezionaSedeSportDataView;
import com.prova.matchme.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;

public class PartitaCtrl {

    private Utente u;

    public PartitaCtrl(Utente u) {
        this.u = u;
        Stage stageBoundary = new CustomStage("Seleziona sede, sport e data");
        Utils.cambiaInterfaccia("FXML/DialogSelezionaSedeSportData.fxml", stageBoundary, c -> {
            return new SelezionaSedeSportDataView(DBMSView.queryGetSedi(), this, stageBoundary);
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

    public void passSedeSportData(Sede sede, String sport, LocalDate data, Stage stage) {
        stage.close();
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
