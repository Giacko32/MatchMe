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
import com.prova.matchme.shared.ConfirmView;
import jakarta.mail.Part;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class PartitaCtrl {

    private Utente u;
    private Stage s;
    private DettagliCampoView boundary;
    DetailsMiaPartitaView boundary1;
    DetailsTuttePartiteView boundary2;
    private Stage stageDialog;
    private LocalDateTime selectedDataOra;
    private PartitaDetails partitaToCancel;

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
        stageDialog = new CustomStage("Seleziona partite");
        Utils.cambiaInterfaccia("FXML/SelezioneVisualizzaPartite.fxml", stageDialog, c -> {
            return new SelectTipoPartiteView(this, stageDialog);
        }, 350, 170);
    }

    public PartitaCtrl(Utente u, Stage stage, int Bonus) {
        this.u = u;
        this.s = stage;
        Utils.cambiaInterfaccia("FXML/Assegna Bonus.fxml", s, c -> {
            return new BonusView(this, DBMSView.queryGetPartiteAllenatore(u.getId()));
        });

    }

    public void passPartita(Partita partita) {
        if (partita != null) {
            DBMSView.queryCreaPartita(partita, u);
            this.toMain();
        } else {
            Utils.creaPannelloErrore("Età o livello errati");
        }
    }

    public void passGiocatoreBonus(Utente dest, float bonusValue, BonusView bonusView) {
        if (dest.equals(u)) {
            Utils.creaPannelloErrore("Non puoi assegnare\nun bonus a te stesso");
        } else {
            DBMSView.queryAddBonusPlayer(dest, bonusValue);
            ArrayList<UtentePart> destinatario = new ArrayList<>();
            destinatario.add(new UtentePart(dest, 1));
            DBMSView.sendNotify("L'allenatore " + u.getNome() + " " + u.getCognome() + " ti ha assegnato un bonus", destinatario, 1);
            Utils.creaPannelloErrore("Bonus assegnato");
        }
        bonusView.ShowBnd();
    }

    public Object getPartite1ora() {
        return null;
    }

    public Object getPartitePiu1ora() {
        return null;
    }

    public void passSedeSportData(Sede sede, String sport, LocalDate data, Stage stage, int ste) {
        if (sede == null || sport == null || data == null) {
            Utils.creaPannelloErrore("Ci sono dei campi vuoti");
        } else {
            if (data.isAfter(LocalDate.now()) || data.isEqual(LocalDate.now())) {
                if (ste != 1) {
                    stage.close();
                }
                Utils.cambiaInterfaccia("FXML/VisualizzaCampiLiberi.fxml", s, c -> {
                    boundary = new DettagliCampoView(DBMSView.queryGetCampiLiberi(sede, sport, data), this);
                    return boundary;
                });
            } else {
                Utils.creaPannelloErrore("La data non è corretta");
            }
        }

    }

    public void passTipoPartita(boolean Mie, Stage st) {
        st.close();
        if (Mie) {
            Utils.cambiaInterfaccia("FXML/VisualizzaLeMiePartite.fxml", s, c -> {
                boundary1 = new DetailsMiaPartitaView(DBMSView.queryGetPartiteUtente(u), this);
                return boundary1;
            });
        } else {
            Utils.cambiaInterfaccia("FXML/DialogSelectSedeSport.fxml", stageDialog, c -> new SelectSedeSportView(DBMSView.queryGetSedi(), this, stageDialog), 350, 170);
        }
    }

    public Object getTime() {
        return null;
    }

    public void passSedeSport(Sede sede, String sport) {
        stageDialog.close();
        Utils.cambiaInterfaccia("FXML/Visualizza partite.fxml", s, c -> {
            boundary2 = new DetailsTuttePartiteView(this, DBMSView.queryGetTuttePartite(sede, sport), u);
            return boundary2;
        });
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

    public void deleteWarning(PartitaDetails partitaDetails) {
        ArrayList<UtentePart> destNotify = new ArrayList<>();
        for (Utente user : partitaDetails.squadra1) {
            destNotify.add(new UtentePart(user, 1));
        }
        for (Utente user : partitaDetails.squadra2) {
            destNotify.add(new UtentePart(user, 2));
        }
        DBMSView.sendNotify(u.getNome() + " " + u.getCognome() + " con id " + u.getId() + " chiede di partecipare nella partita del " + partitaDetails.campo.getOrarioString() + " nella sede " + partitaDetails.sede.getNome_sede() + " di " + partitaDetails.campo.getSport() + " " + partitaDetails.partita.getId(), destNotify, 2);
        DBMSView.querySetRichiestaAccettazione(u.getId(), partitaDetails.partita.getId(), partitaDetails.squadra1.size() + partitaDetails.squadra2.size());
        boundary2.ShowBnd(partitaDetails);
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

    public void passRicercaGiocatore(String textSearched, SuggestedPlayerView bnd, PartitaDetails partdet) {
        ArrayList<Utente> trovati = DBMSView.queryGetListaGiocatori(textSearched);
        trovati.removeAll(partdet.squadra1);
        trovati.removeAll(partdet.squadra2);
        bnd.mostraLista(trovati);
    }

    public void passInvito(Utente utente, PartitaDetails partitaDetails, Stage stg) {
        ArrayList<UtentePart> destinatario = new ArrayList<>();
        destinatario.add(new UtentePart(utente, 0));
        DBMSView.sendNotify("Sei stato invitato ad una partita il " + partitaDetails.campo.getOrarioString() + " nella sede " + partitaDetails.sede.getNome_sede() + " per giocare a " + partitaDetails.campo.getSport() + " " + partitaDetails.partita.getId(), destinatario, 0);
        stg.close();
        boundary1.ShowBnd();
    }

    public void passSquadraOspite(int n_squadra, PartitaDetails partitaDetails, Stage stg) {
        DBMSView.queryAddOspite(partitaDetails, n_squadra);
        stg.close();
        boundary1.ShowBnd();
    }

    public void passConferma() {
        DBMSView.queryCancellaPrenotazione(partitaToCancel.partita.getId(), u.getId());
        ArrayList<UtentePart> destinatari = new ArrayList<>();
        partitaToCancel.squadra1.remove(u);
        partitaToCancel.squadra2.remove(u);
        for (Utente utente : partitaToCancel.squadra1) {
            destinatari.add(new UtentePart(u, 1));
        }
        for (Utente utente : partitaToCancel.squadra2) {
            destinatari.add(new UtentePart(u, 2));
        }
        destinatari.remove(u);
        DBMSView.sendNotify(u.getNome() + " " + u.getCognome() + " ha abbandonato la partita del " + partitaToCancel.campo.getOrarioString() + " nella sede " + partitaToCancel.sede.getNome_sede(), destinatari, 1);
        boundary1.removePartita(partitaToCancel);
    }

    public boolean checkVincoli(String vincoli) {
        String[] VincoliSplitted = vincoli.split(";");
        if (!VincoliSplitted[0].equals(u.getSessoVincoli())) {
            return false;
        }
        if (u.getLivello().intValue() < Integer.parseInt(VincoliSplitted[1]) || u.getLivello().intValue() > Integer.parseInt(VincoliSplitted[2])) {
            return false;
        }
        return Integer.parseInt(u.getEta()) >= Integer.parseInt(VincoliSplitted[3]) && Integer.parseInt(u.getEta()) <= Integer.parseInt(VincoliSplitted[4]);
    }

    public void passSquadra(int n_squadra, PartitaDetails partitaDetails, Stage stage) {
        DBMSView.queryAddGiocatore(partitaDetails.partita.getId(), u.getId(), n_squadra);
        ArrayList<UtentePart> destinatari = new ArrayList<>();
        for (Utente utente : partitaDetails.squadra1) {
            destinatari.add(new UtentePart(utente, 1));
        }
        for (Utente utente : partitaDetails.squadra2) {
            destinatari.add(new UtentePart(utente, 2));
        }
        DBMSView.sendNotify(u.getNome() + " " + u.getCognome() + " adesso partecipa nella partita del " + partitaDetails.campo.getOrarioString() + " nella sede " + partitaDetails.sede.getNome_sede(), destinatari, 1);
        stage.close();
        boundary2.selectPartita();
    }

    public boolean VerificaDisponibilità() {
        return false;
    }

    public void SelectedCampo(Campo campo) {
        boundary.showDetails(DBMSView.queryGetDetailsCampo(campo));
    }

    public void SelectedPartita(Partita partita, boolean Mie) {
        if (Mie) {
            if (partita != null) {
                PartitaDetails dettagli = DBMSView.queryGetCampoSedePartita(partita);
                boundary1.ShowDetails(dettagli);
            } else {
                boundary1.ShowDetails(null);
            }
        } else {
            if (partita != null) {
                PartitaDetails dettagli = DBMSView.queryGetCampoSedePartita(partita);
                boundary2.ShowBnd(dettagli);
            } else {
                boundary1.ShowDetails(null);
            }
        }
    }

    public PartitaDetails passPartita(Partita partita, BonusView bonusView) {
        return DBMSView.queryGetCampoSedePartita(partita);
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
                return new SuggestedPlayerView(listaSuggeriti, finalSquadreNonPiene, this, partitaDetails, cs, false);
            }, 500, 330);
        }

    }

    public void AggiungiOspiteClicked(PartitaDetails partitaDetails) {
        if (!checkNumeroGiocatori(partitaDetails)) {
            Utils.creaPannelloErrore("La partita è piena");
        } else {
            CustomStage stg = new CustomStage("Inserisci ospite");
            Utils.cambiaInterfaccia("FXML/DialogAggiungiOspite.fxml", stg, c -> {
                return new AggiungiOspiteView(this, partitaDetails, stg);
            }, 350, 170);
        }
    }

    public void CancelClicked(PartitaDetails partitaDetails) {
        this.partitaToCancel = partitaDetails;
        Stage stg = new Stage();
        Utils.cambiaInterfaccia("FXML/DialogConferma.fxml", stg, c -> {
            return new ConfirmView(this, stg);
        }, 350, 170);
    }

    public void InvitaClicked(PartitaDetails partitaDetails) {
        if (!checkNumeroGiocatori(partitaDetails)) {
            Utils.creaPannelloErrore("La partita è piena");
        } else {
            ArrayList<Utente> listaSuggeriti = DBMSView.queryGetGiocatoriSuggeriti(u);
            listaSuggeriti.removeAll(partitaDetails.squadra1);
            listaSuggeriti.removeAll(partitaDetails.squadra2);
            String squadreNonPiene = "";
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
            CustomStage cs = new CustomStage("Invita Giocatore");
            Utils.cambiaInterfaccia("FXML/Aggiungigiocatori.fxml", cs, c -> {
                return new SuggestedPlayerView(listaSuggeriti, finalSquadreNonPiene, this, partitaDetails, cs, true);
            }, 500, 330);
        }
    }

    public void prenotaPartita(Campo campo, Sede sede) {
        Utils.cambiaInterfaccia("FXML/Prenotazione2.fxml", s, c -> {
            return new PrenotazionePartitaView(u, this, campo, sede, s);
        });
    }

    public void PartecipaClicked(PartitaDetails partitaDetails) {
        Stage stg = new CustomStage("Squadre");
        if (checkVincoli(partitaDetails.partita.getVincoli())) {
            Utils.cambiaInterfaccia("FXML/DialogSelezionaSquadraPartecipazione.fxml", stg, c -> {
                return new PartecipazionePartitaView(stg, this, partitaDetails);
            }, 350, 170);
        } else {
            Utils.cambiaInterfaccia("FXML/DialogCriteriInsufficenti.fxml", stg, c -> {
                return new ConfirmView(this, stg, partitaDetails);
            }, 350, 170);
        }
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
