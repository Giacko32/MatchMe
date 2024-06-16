package com.prova.matchme.GestioneAllenamenti.Controller;


import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AdminView;
import com.prova.matchme.Autenticazione.Interfacce.AllenaView;
import com.prova.matchme.Autenticazione.Interfacce.MainView;
import com.prova.matchme.CustomStage;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.*;
import com.prova.matchme.GestioneAllenamenti.Interfacce.DettagliAllenamentiView;
import com.prova.matchme.GestioneAllenamenti.Interfacce.DettagliMioAllenamentoView;
import com.prova.matchme.GestioneAllenamenti.Interfacce.GestioneAllView;
import com.prova.matchme.GestionePartita.Interfacce.SelectTipoPartiteView;
import com.prova.matchme.GestioneSede.Interfacce.SelectDataView;
import com.prova.matchme.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GestioneAllCtrl {

    private Utente utente;
    private Stage stage;
    private Gestore gestore;
    private CustomStage stageDialog = new CustomStage("Allenamenti");
    private DettagliMioAllenamentoView boundary;
    private DettagliAllenamentiView boundary1;

    public GestioneAllCtrl(Utente utente, Stage s) {
        this.utente = utente;
        this.stage = s;
        Utils.cambiaInterfaccia("FXML/SelezioneAllenamento.fxml", stageDialog, c -> {
            return new GestioneAllView(this);
        }, 350, 170);
    }

    public GestioneAllCtrl(Gestore gestore, Stage s) {
        this.gestore = gestore;
        this.stage = s;
        Utils.cambiaInterfaccia("FXML/DialogSelezionaData.fxml", stageDialog, c -> {
            return new SelectDataView(stageDialog, this, LocalDate.now());
        }, 350, 170);
    }

    public void PrenotaClicked() {
        //DBMSView.
        stageDialog.close();
        Utils.cambiaInterfaccia("FXML/Allenamenti.fxml", stage, c -> {
            boundary1 = new DettagliAllenamentiView(this, DBMSView.queryGetAllenamenti(), utente);
            return boundary1;
        });
    }

    public Allenamento AllenamentoClicked(Partita allenamento) {
        try {
            return DBMSView.queryGetDettagliAllenamento(allenamento);
        } catch (Exception e) {
        }
        return null;
    }

    public void PrenotaCliccato(Allenamento allenamento) {
        if (!CheckOccupato(allenamento.partita.getDataOra())) {//occupato
            Utils.creaPannelloErrore("Sei già impegnato in\nquesta fascia oraria");
        } else {
            if (DBMSView.queryVerificaAllenamento(allenamento.partita.getId())) {
                DBMSView.queryAddGiocatore(allenamento.partita.getId(), utente.getId(), 1);
                boundary1.selectAllenamento();
            } else {
                Utils.creaPannelloErrore("Non ci sono posti\nliberi nell'allenamento");
            }
        }
    }

    public boolean CheckOccupato(LocalDateTime dataora) {
        return DBMSView.queryGiocatoreOccupato(utente.getId(), dataora);
    }

    public void CloseWarningView() {

    }

    public void ClickMieiAllenamenti() {
        stageDialog.close();
        Utils.cambiaInterfaccia("FXML/VisualizzaMieiAllenamenti.fxml", stage, c -> {
            boundary = new DettagliMioAllenamentoView(this, DBMSView.queryGetMieiAllenamenti(utente));
            return boundary;
        });
    }

    public void CancellaClicked(Allenamento all) {
        if (this.CheckAllenatore(all)) {
            ArrayList<UtentePart> dest = new ArrayList<>();
            for (Utente u : all.partecipanti) {
                dest.add(new UtentePart(u, 0));
            }
            DBMSView.sendNotify("L'allenamento nel campo " + all.campo.getNomecampo() + " della sede " + all.sede.getNome_sede() + " il giorno " + all.campo.getOrarioString() + " è stato annullato", dest, 1);
            DBMSView.queryCancellaAllenamento(all);
        } else {
            DBMSView.queryCancellaUtenteAllenamento(utente, all);
        }
        boundary.showBnd();
    }

    public boolean CheckAllenatore(Allenamento all) {
        return utente.equals(all.allenatore);
    }

    public void passData(LocalDate data) {
        Utils.cambiaInterfaccia("FXML/AllenamentiGestore.fxml", stage, c -> {
            boundary1 = new DettagliAllenamentiView(this, DBMSView.queryGetAllenamentiSede(gestore.getSede(), data), gestore);
            return boundary1;
        });
    }

    public void toMain() {
        if (utente != null) {
            if (utente.getTipo().equals("al")) {
                Utils.cambiaInterfaccia("FXML/Allena-view.fxml", stage, c -> {
                    return new AllenaView(new AuthCtrl(stage), utente, stage);
                });
            } else {
                Utils.cambiaInterfaccia("FXML/Main-view.fxml", stage, c -> {
                    return new MainView(new AuthCtrl(stage), utente, stage);
                });
            }
        }
        if (gestore != null) {
            Utils.cambiaInterfaccia("FXML/Admin-view.fxml", stage, c -> {
                return new AdminView(new AuthCtrl(stage), gestore, stage);
            });
        }
    }
}
