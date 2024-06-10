package com.prova.matchme.GestionePartita.Controller;


import com.mysql.cj.util.Util;
import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AdminView;
import com.prova.matchme.Autenticazione.Interfacce.AllenaView;
import com.prova.matchme.Autenticazione.Interfacce.MainView;
import com.prova.matchme.CustomStage;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.*;
import com.prova.matchme.GestionePartita.Interfacce.*;
import com.prova.matchme.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PartitaCtrl {

    private Utente u;
    private Stage s;
    private DettagliCampoView boundary;
    DetailsMiaPartitaView boundary1;
    private LocalDateTime selectedDataOra;

    public PartitaCtrl(Utente u, Stage s) {
        this.u = u;
        this.s = s;
        Stage stageBoundary = new CustomStage("Seleziona sede, sport e data");
        Utils.cambiaInterfaccia("FXML/DialogSelezionaSedeSportData.fxml", stageBoundary, c -> {
            return new SelezionaSedeSportDataView(DBMSView.queryGetSedi(), this, stageBoundary);
        }, 330, 220);
    }

    public PartitaCtrl(Utente u, Stage stage, boolean Partite) {
        this.u = u;
        this.s = stage;
        Stage stageDialog = new CustomStage("Seleziona partite");
        Utils.cambiaInterfaccia("FXML/SelezioneVisualizzaPartite.fxml", stageDialog, c -> {
            return new SelectTipoPartiteView(this, stageDialog);
        }, 350, 170);
    }

    public void passPartita(Partita partita) {
        DBMSView.queryCreaPartita(partita, u);
        this.toMain();
    }

    public void passGiocatoreBonus(int id_player, int bonusValue) {

    }

    public Object getPartite1ora() {
        return null;
    }

    public Object getPartitePiu1ora() {
        return null;
    }

    public void passSedeSportData(Sede sede, String sport, LocalDateTime data, Stage stage, int ste) {
        if (ste != 1) {
            stage.close();
        }
        Utils.cambiaInterfaccia("FXML/VisualizzaCampiLiberi.fxml", s, c -> {
            boundary = new DettagliCampoView(DBMSView.queryGetCampiLiberi(sede, sport, data.toLocalDate()), this);
            return boundary;
        });
    }

    public void passTipoPartita(boolean Mie, Stage st) {
        st.close();
        if (Mie) {
            Utils.cambiaInterfaccia("FXML/VisualizzaLeMiePartite.fxml", s, c -> {
                boundary1 = new DetailsMiaPartitaView(DBMSView.queryGetPartiteUtente(u), this);
                return boundary1;
            });
        } else {
            Utils.cambiaInterfaccia("FXML/Visualizza partite.fxml", s, c -> new DetailsTuttePartiteView(this));
        }
    }

    public Object getTime() {
        return null;
    }

    public void passSedeSport() {

    }

    public boolean checkNumeroGiocatori(PartitaDetails partitaDetails) {
        return switch (partitaDetails.campo.getSport()) {
            case "Calcio a 5" -> (partitaDetails.squadra1.size() + partitaDetails.squadra2.size()) < 10;
            case "Tennis singolo", "Padel singolo" ->
                    (partitaDetails.squadra1.size() + partitaDetails.squadra2.size()) < 2;
            case "Tennis doppio", "Padel doppio" ->
                    (partitaDetails.squadra1.size() + partitaDetails.squadra2.size()) < 4;
            default -> false;
        };
    }

    public void deleteWarning() {

    }

    public void passGiocatoreSquadra(Utente utente, int n_squadra, PartitaDetails partita, Stage stage) {
        if (DBMSView.queryGiocatoreOccupato(utente.getId(), partita.campo.getOrario())) {
            DBMSView.queryAddGiocatore(partita.partita.getId(), utente.getId(), n_squadra);
            ArrayList<UtentePart> destNotify = new ArrayList<>();
            for (Utente user : partita.squadra1) {
                destNotify.add(new UtentePart(user, 1));
            }
            for (Utente user : partita.squadra2) {
                destNotify.add(new UtentePart(user, 2));
            }
            DBMSView.sendNotify(utente.getNome() + " " + utente.getCognome() + " si è unito alla tua partita del " + partita.campo.getOrarioString() + " nella sede " + partita.sede.getNome_sede(), destNotify, 1);
            stage.close();
            boundary1.ShowBnd();
        } else {
            Utils.creaPannelloErrore("Il giocatore è impegnato\n in questa fascia oraria");
        }
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

    public void SelectedCampo(Campo campo) {
        boundary.showDetails(DBMSView.queryGetDetailsCampo(campo));
    }

    public void SelectedPartita(Partita partita) {
        PartitaDetails dettagli = DBMSView.queryGetCampoSedePartita(partita);
        boundary1.ShowDetails(dettagli);
    }

    public void AggiungiClicked(PartitaDetails partitaDetails) {
        String squadreNonPiene = null;
        if (!checkNumeroGiocatori(partitaDetails)) {
            Utils.creaPannelloErrore("La partita è piena");
        } else {
            ArrayList<Utente> listaSuggeriti = DBMSView.queryGetGiocatoriSuggeriti(u);
            listaSuggeriti.removeAll(partitaDetails.squadra1);
            listaSuggeriti.removeAll(partitaDetails.squadra2);
            squadreNonPiene = "";
            switch (partitaDetails.campo.getSport()) {
                case "Calcio a 5" -> {
                    if (partitaDetails.squadra1.size() == 5) {
                        squadreNonPiene = "2";
                    } else if (partitaDetails.squadra2.size() == 5) {
                        squadreNonPiene = "1";
                    } else {
                        squadreNonPiene = "entrambe";
                    }
                }
                case "Tennis singolo", "Padel singolo" -> {
                    if (partitaDetails.squadra1.size() == 1) {
                        squadreNonPiene = "2";
                    } else if (partitaDetails.squadra2.size() == 1) {
                        squadreNonPiene = "1";
                    }
                }
                case "Tennis doppio", "Padel doppio" -> {
                    if (partitaDetails.squadra1.size() == 2) {
                        squadreNonPiene = "2";
                    } else if (partitaDetails.squadra2.size() == 2) {
                        squadreNonPiene = "1";
                    } else {
                        squadreNonPiene = "entrambe";
                    }
                }
            }
            String finalSquadreNonPiene = squadreNonPiene;
            CustomStage cs = new CustomStage("Aggiungi Giocatori");
            Utils.cambiaInterfaccia("FXML/Aggiungigiocatori.fxml", cs, c -> {
                return new SuggestedPlayerView(listaSuggeriti, finalSquadreNonPiene, this, partitaDetails, cs);
            }, 500, 330);
        }

    }

    public void AggiungiOspiteClicked() {

    }

    public void CancelClicked() {

    }

    public void InvitaClicked() {

    }

    public void prenotaPartita(Campo campo, Sede sede) {
        Utils.cambiaInterfaccia("FXML/Prenotazione2.fxml", s, c -> {
            return new PrenotazionePartitaView(u, this, campo, sede, s);
        });
    }

    public void PartecipaClicked(Campo campo, Sede sede) {

    }

    public void toMain() {
        if (u != null) {
            if (u.getTipo().equals("al")) {
                Utils.cambiaInterfaccia("FXML/Allena-view.fxml", s, c -> {
                    return new AllenaView(new AuthCtrl(s), u, s);
                });
            } else {
                Utils.cambiaInterfaccia("FXML/Main-view.fxml", s, c -> {
                    return new MainView(new AuthCtrl(s), u, s);
                });
            }
        }
    }


}
