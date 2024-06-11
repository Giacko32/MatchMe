package com.prova.matchme.GestioneSede.Controller;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AdminView;
import com.prova.matchme.CustomStage;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.*;
import com.prova.matchme.GestioneSede.Interfacce.*;
import com.prova.matchme.Utils;
import com.prova.matchme.shared.WarningView;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GestionePartiteSedeCtrl {


    private Stage s;
    private Gestore g;
    private VisualizzaDettagliPartitaSedeView controller;
    private Campo camposelected;
    private Partita partitaselected;
    private PartitaDetails partitaDetails;
    private InvitaGiocatoriView controllerigv;
    private RinviaPartitaView controllerrv;

    public GestionePartiteSedeCtrl(Stage s, Gestore g) {
        this.s = s;
        this.g = g;
    }

    public void toAdmin() {
        Utils.cambiaInterfaccia("FXML/Admin-view.fxml", s, c -> {
            return new AdminView(new AuthCtrl(s), g, s);
        });
    }

    public void tocreapartita() {
        CustomStage s = new CustomStage("Seleziona una data");
        Utils.cambiaInterfaccia("FXML/DialogCreaPartitaSede.fxml", s, c -> {
            return new CreaPartitaSedeView(this, s);
        }, 340, 180);

    }

    public void toVisualize() {
        CustomStage s = new CustomStage("Seleziona data");
        Utils.cambiaInterfaccia("FXML/DialogSelezionaData.fxml", s, c -> {
            return new SelectDataView(s, this, LocalDate.now());
        }, 340, 180);
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
        ArrayList<Campo> listacampi = DBMSView.queryGetCampiLiberi2(g.getSede(), data);
        Utils.cambiaInterfaccia("FXML/Crea Partita sede 1.fxml", s, c -> {
            return new CampiDisponibilisedeView(this, listacampi);
        });
    }

    public void PassCampo(Campo c) {
        camposelected = c;
        Utils.cambiaInterfaccia("FXML/Crea Partita sede 2.fxml", s, co -> {
            return new InsertVincoliView(this, s);
        });
    }

    public void PassVincoli(String vincoli) {
        Partita p = new Partita(0, camposelected.getId_campo(), camposelected.getOrario(), "ppub", vincoli);
        DBMSView.queryCreaPartita(p, null);
        toAdmin();
    }

    public boolean CheckCapienza(PartitaDetails p) {
        return switch (partitaDetails.campo.getSport()) {
            case "Calcio a 5" -> (partitaDetails.squadra1.size() + partitaDetails.squadra2.size()) < 10;
            case "Tennis singolo", "Padel singolo" ->
                    (partitaDetails.squadra1.size() + partitaDetails.squadra2.size()) < 2;
            case "Tennis doppio", "Padel doppio" ->
                    (partitaDetails.squadra1.size() + partitaDetails.squadra2.size()) < 4;
            default -> false;
        };
    }
    public void InvitaGiocatoreCliccato() {
        if (CheckCapienza(partitaDetails)) {
            ArrayList<Utente> listagiocatoricompatibili = DBMSView.queryGetGiocatoriCompatibili(partitaselected.getVincoli());
            listagiocatoricompatibili.removeAll(partitaDetails.squadra1);
            listagiocatoricompatibili.removeAll(partitaDetails.squadra2);
            Utils.cambiaInterfaccia("FXML/Invita Giocatore Gestore.fxml", s, c -> {
                controllerigv=new InvitaGiocatoriView(this, listagiocatoricompatibili, partitaDetails);
                return controllerigv;
            });
        } else {
            CustomStage stage = new CustomStage("ATTENZIONE");
            Utils.cambiaInterfaccia("FXML/Dialog Num Max.fxml", stage, c -> {
                return new WarningView(stage);
            }, 400, 150);
        }
    }

    public void PassDati(Utente u, int squadra) {
        String notifica = "Sei stato invitato alla partita di " + partitaDetails.campo.getSport() + " " + partitaselected.getId();
        DBMSView.sendNotify2(notifica, u.getId(), 0);
        toAdmin();
    }

    public void PassDati2(String parametro){
        ArrayList<Utente> listagiocatori= DBMSView.queryGetUtenti(parametro);
        listagiocatori.removeAll(partitaDetails.squadra1);
        listagiocatori.removeAll(partitaDetails.squadra2);
        controllerigv.SetLista(listagiocatori);
    }


    public void rinviaClicked(){
        Utils.cambiaInterfaccia("FXML/Rinvia Partita.fxml",s,c->{
            controllerrv=new RinviaPartitaView(this);
            return controllerrv;
        });
    }

    public void PassData2(LocalDate date){
        if(date!=null){
             controllerrv.showLista(DBMSView.queryGetDisponibilitaCampoData(partitaDetails.campo.getId_campo(),date));
        }
    }

    public void PassPartitaNewData(LocalDateTime dateTime) {
      DBMSView.queryRinviaPartita(partitaselected.getId(),dateTime);
        Utils.cambiaInterfaccia("FXML/Visualizza Partite sede.fxml", s, c -> {
            controller = new VisualizzaDettagliPartitaSedeView(this, DBMSView.queryGetPartiteSede(g.getSede(), LocalDate.now()));
            return controller;
        });

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
        partitaselected = p;
        partitaDetails = DBMSView.queryGetCampoSedePartita(p);
        controller.showDetails(partitaDetails);
    }

    public void PassVerificaPartite(LocalDate data) {
        Utils.cambiaInterfaccia("FXML/Visualizza Partite sede.fxml", s, c -> {
            controller = new VisualizzaDettagliPartitaSedeView(this, DBMSView.queryGetPartiteSede(g.getSede(), data));
            return controller;
        });
    }

}
