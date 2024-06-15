package com.prova.matchme.GestioneSede.Controller;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AdminView;
import com.prova.matchme.CustomStage;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.*;
import com.prova.matchme.GestioneSede.Interfacce.*;
import com.prova.matchme.Utils;
import com.prova.matchme.shared.ChoiceView;
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

    public GestionePartiteSedeCtrl(Gestore g) {
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


    public void PassSquadraVincitrice(int squadra) {
        float livellomedio = 0.0F;
        for (int i = 0; i < partitaDetails.squadra1.size(); i++) {
            livellomedio += partitaDetails.squadra1.get(i).getLivello();
        }
        for (int i = 0; i < partitaDetails.squadra2.size(); i++) {
            livellomedio += partitaDetails.squadra2.get(i).getLivello();
        }
        livellomedio = livellomedio / (partitaDetails.squadra1.size() + partitaDetails.squadra2.size());
        CalcolaPunteggio(livellomedio, squadra);
    }

    public void CalcolaPunteggio(float livellomedio, int squadra) {
        Float punt = 0.2F;
        ArrayList<Float> punteggio = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < partitaDetails.squadra1.size(); i++) {
            ids.add(partitaDetails.squadra1.get(i).getId());
            float temp = (partitaDetails.squadra1.get(i).getLivello() - (livellomedio)) * punt / (livellomedio);
            if (squadra == 1) {
                if (!(temp > 0)) {
                    temp += 2 * temp;
                }
                if (temp == 0) {
                    temp = punt;
                }
            } else {
                if ((temp > 0)) {
                    temp -= 2 * temp;
                }
                if (temp == 0) {
                    temp -= punt;
                }
            }
            punteggio.add(temp);
            System.out.println(punteggio.get(i));
        }
        for (int i = 0; i < partitaDetails.squadra2.size(); i++) {
            ids.add(partitaDetails.squadra2.get(i).getId());
            float temp = (partitaDetails.squadra2.get(i).getLivello() - livellomedio) * punt / livellomedio;
            if (squadra == 2) {
                if (!(temp > 0)) {
                    temp += 2 * temp;
                }
            } else {
                if ((temp > 0)) {
                    temp -= 2 * temp;
                }
            }
            punteggio.add(temp);
        }
        DBMSView.querySendPunteggi(ids, punteggio);
        DBMSView.queryPartitaGiocata(partitaselected.getId(), String.valueOf(squadra), partitaselected.getDataOra());
        Utils.cambiaInterfaccia("FXML/Visualizza Partite sede.fxml", s, c -> {
            controller = new VisualizzaDettagliPartitaSedeView(this, DBMSView.queryGetPartiteSede(g.getSede(), LocalDate.now()));
            return controller;
        });
    }

    public void AssegnaRisultatoCliccato() {
        LocalDateTime current = LocalDateTime.now();
        if (current.isAfter(partitaselected.getDataOra())) {
            CustomStage s = new CustomStage("Scegli la squadra");
            Utils.cambiaInterfaccia("FXML/DialogAssegnaRisultato.fxml", s, c -> {
                return new ChoiceView(this, s);
            }, 350, 150);
        } else {
            CustomStage s = new CustomStage("Scegli la squadra");
            Utils.cambiaInterfaccia("FXML/Dialog PartitaNonGiocata.fxml", s, c -> {
                return new WarningView(s);
            }, 350, 250);
        }
    }

    public void PassDate(LocalDate data) {
        ArrayList<Campo> listacampi = DBMSView.queryGetCampiLiberi2(g.getSede(), data);
        LocalDateTime now = LocalDateTime.now();
        listacampi.removeIf(campo -> campo.getOrario().isBefore(now));
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
                controllerigv = new InvitaGiocatoriView(this, listagiocatoricompatibili, partitaDetails);
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

    public void PassDati2(String parametro) {
        ArrayList<Utente> listagiocatori = DBMSView.queryGetUtenti(parametro);
        listagiocatori.removeAll(partitaDetails.squadra1);
        listagiocatori.removeAll(partitaDetails.squadra2);
        controllerigv.SetLista(listagiocatori);
    }


    public void rinviaClicked() {
        Utils.cambiaInterfaccia("FXML/Rinvia Partita.fxml", s, c -> {
            controllerrv = new RinviaPartitaView(this);
            return controllerrv;
        });
    }

    public void PassData2(LocalDate date) {
        if (date != null) {
            ArrayList<Campo> campi = DBMSView.queryGetDisponibilitaCampoData(partitaDetails.campo.getId_campo(),date);
            LocalDateTime now = LocalDateTime.now();
            campi.removeIf(campo -> campo.getOrario().isBefore(now));

            controllerrv.showLista(campi);
        }
    }

    public void PassPartitaNewData(LocalDateTime dateTime) {
        DBMSView.queryRinviaPartita(partitaselected.getId(), dateTime);
        Utils.cambiaInterfaccia("FXML/Visualizza Partite sede.fxml", s, c -> {
            controller = new VisualizzaDettagliPartitaSedeView(this, DBMSView.queryGetPartiteSede(g.getSede(), LocalDate.now()));
            return controller;
        });

    }

    public void verifica() {
        ArrayList<Partita> listaPartita = DBMSView.queryGetAllPartiteSede(g.getSede());
        ArrayList<Partita> listapiu1ora = getPartitePiu1ora(listaPartita);
        ArrayList<Partita> listameno1ora = getPartite1ora(listaPartita);
        System.out.println(listapiu1ora);
        System.out.println(listameno1ora);

        for (Partita partita : listapiu1ora) {
            String sport = DBMSView.queryGetSport(partita.getRef_campo());
            int npartcurrent = DBMSView.querygetpartecipanti(partita.getId()).size();
            ArrayList<UtentePart> utenteParts=DBMSView.querygetpartecipanti(partita.getId());
            int numpartsport = 0;
            switch (sport) {
                case "Calcio a 5": {
                    numpartsport = 10;
                    break;
                }
                case "Tennis singolo", "Padel singolo": {
                    numpartsport = 2;
                    break;
                }
                case "Tennis doppio", "Padel doppio": {
                    numpartsport = 4;
                    break;
                }
            }
            if (npartcurrent < numpartsport) {
                System.out.println("Facendo qualcosa");
                ArrayList<Utente> listacomp = DBMSView.queryGetGiocatoriCompatibili(partita.getVincoli());
                listacomp.removeAll(utenteParts);
                System.out.println(listacomp);
                String notifica = "Sei stato invitato alla partita di " + sport + " " + partita.getId();
                for (Utente u : listacomp) {
                    DBMSView.sendNotify2(notifica, u.getId(), 0);
                }
            }
        }
        for(Partita partita:listameno1ora){
            String sport = DBMSView.queryGetSport(partita.getRef_campo());
            int npartcurrent = DBMSView.querygetpartecipanti(partita.getId()).size();
            ArrayList<UtentePart> utenteParts=DBMSView.querygetpartecipanti(partita.getId());
            int numpartsport = 0;
            switch (sport) {
                case "Calcio a 5": {
                    numpartsport = 10;
                    break;
                }
                case "Tennis singolo", "Padel singolo": {
                    numpartsport = 2;
                    break;
                }
                case "Tennis doppio", "Padel doppio": {
                    numpartsport = 4;
                    break;
                }
            }
            if (npartcurrent < numpartsport) {
                String notifica="Ci dispiace ma la partita di "+sport+" con id " +partita.getId()+" non ha il numero minimo di partecipanti quindi sarà eliminata";
                assert utenteParts != null;
                DBMSView.sendNotify(notifica,utenteParts,1);
                DBMSView.queryCancellaPartita(partita.getId());
            }
        }
    }


    public ArrayList<Partita> getPartitePiu1ora(ArrayList<Partita> partite) {
        LocalDateTime current = LocalDateTime.now();
        ArrayList<Partita> partiterit = new ArrayList<>();
        for (int i = 0; i < partite.size(); i++) {
            if (partite.get(i).getDataOra().isAfter(current.plusHours(1))) {
                partiterit.add(partite.get(i));
            }
        }
        return partiterit;
    }

    public ArrayList<Partita> getPartite1ora(ArrayList<Partita> partite) {
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime oneHourLater = current.plusHours(1);
        ArrayList<Partita> partiteEntroUnOra = new ArrayList<>();
        for (Partita partita : partite) {
            LocalDateTime dataOraPartita = partita.getDataOra();
            if (dataOraPartita.isAfter(current) && dataOraPartita.isBefore(oneHourLater)) {
                partiteEntroUnOra.add(partita);
            }
        }
        return partiteEntroUnOra;
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
