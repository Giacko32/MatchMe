package com.prova.matchme.GestioneNotifiche.Controller;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AllenaView;
import com.prova.matchme.Autenticazione.Interfacce.MainView;
import com.prova.matchme.CustomStage;
import com.prova.matchme.shared.DBMSView;
import com.prova.matchme.Entity.*;
import com.prova.matchme.GestioneNotifiche.Interfacce.AccettazioneView;
import com.prova.matchme.GestioneNotifiche.Interfacce.DetailPartitaView;
import com.prova.matchme.GestioneNotifiche.Interfacce.NotifyView;
import com.prova.matchme.Utils;
import com.prova.matchme.shared.ChoiceView;
import com.prova.matchme.shared.WarningView;
import javafx.stage.Stage;

import java.util.ArrayList;

public class NotifyCtrl {

    private Stage s;
    private Utente u;
    private int idPartita;
    private int numpartsport;
    private NotifyView controller;
    private Notifica selectednotifica;
    private Partita partitaselected;
    private ArrayList<UtentePart> listapart;
    private int idutentedaacc;
    private Accettazione accettazione;

    public NotifyCtrl(Stage s,Utente u){
        this.u=u;
        this.s=s;
        ArrayList<Notifica> listanotifiche=DBMSView.queryGetNotifiche(u.getId());
        Utils.cambiaInterfaccia("FXML/Notifiche.fxml",s,c->{
            controller=new NotifyView(s,listanotifiche,this);
            return controller;
        });
    }

    public void rispostaInvito(Notifica n){
        selectednotifica=n;
        String[] parole=n.toString().split(" ");
        idPartita= Integer.parseInt(parole[parole.length-1]);
        switch (parole[parole.length-2]){
            case "5":{
                numpartsport=10;
                break;
            }
            case "singolo":{
                numpartsport=2;
                break;
            }
            case "doppio" :{
                numpartsport=4;
                break;
            }
        }
        CustomStage s=new CustomStage("Scegli");
        Utils.cambiaInterfaccia("FXML/Dialog Invito Ricevuto.fxml",s,c->{
            return new ChoiceView(this,s);
        },350,150);
    }


    public void inviaEsito(boolean esito){
        if(esito){
            CustomStage s;
            partitaselected=DBMSView.queryGetDetailsPartita(idPartita);
            listapart =DBMSView.querygetpartecipanti(idPartita);
            if(DBMSView.queryGiocatoreOccupato(u.getId(),partitaselected.getDataOra())){
                if(listapart.size()<numpartsport){
                    s=new CustomStage("Scegli la squadra");
                    Utils.cambiaInterfaccia("FXML/Accetta Invito.fxml",s,c->{
                       return new DetailPartitaView(s,this,listapart,numpartsport);
                    });
                }else{
                    s=new CustomStage("Attenzione");
                    Utils.cambiaInterfaccia("FXML/Dialog Num Max.fxml",s,c->{
                        return new WarningView(s);
                    },350,150);
                    DBMSView.eliminaNotifica(selectednotifica);
                    controller.rimuoviNotificaCancellata(selectednotifica);
                }
            }else{
                s=new CustomStage("Attenzione");
                Utils.cambiaInterfaccia("FXML/DialogUtenteImpegnato.fxml",s,c->{
                    return new WarningView(s);
                },350,150);
                DBMSView.eliminaNotifica(selectednotifica);
                controller.rimuoviNotificaCancellata(selectednotifica);
            }

        }else{
            DBMSView.eliminaNotifica(selectednotifica);
            controller.rimuoviNotificaCancellata(selectednotifica);
        }
    }

    public void toMain() {
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

    public void squadraselected(int squadra){
        DBMSView.queryAddGiocatore(idPartita,u.getId(),squadra);
        String notifica="L'utente invitato alla partita con id: "+idPartita +" ha accettato\nvai a controllare che squadra ha scelto";
        DBMSView.sendNotify(notifica,listapart, 1);
        DBMSView.eliminaNotifica(selectednotifica);
        controller.rimuoviNotificaCancellata(selectednotifica);
    }


    public void cancellaNotifica(Notifica n){
        DBMSView.eliminaNotifica(n);
        controller.rimuoviNotificaCancellata(n);
    }

    public void rispostaAccettazione(Notifica n){
        selectednotifica=n;
        String[] parole=n.toString().split(" ");
        idPartita= Integer.parseInt(parole[parole.length-1]);
        switch (parole[parole.length-2]){
            case "5":{
                numpartsport=10;
                break;
            }
            case "singolo":{
                numpartsport=2;
                break;
            }
            case "doppio" :{
                numpartsport=4;
                break;
            }
        }
        idutentedaacc=Integer.parseInt(parole[4]);
        accettazione=DBMSView.queryGetDettagliPartecipantePartita(idPartita,idutentedaacc);
        listapart=DBMSView.querygetpartecipanti(idPartita);
        CustomStage s;
        if(listapart.size()<numpartsport){
            s=new CustomStage("Scegli");
            Utils.cambiaInterfaccia("FXML/Accettazione-Rifiuto Componente.fxml", s, c -> {
                return new AccettazioneView(s,DBMSView.querygetDettagliUtente(idutentedaacc),this);
            });
        }else{
            s=new CustomStage("Attenzione");
            Utils.cambiaInterfaccia("FXML/Dialog Num Max.fxml",s,c->{
                return new WarningView(s);
            },350,150);
            DBMSView.eliminaNotifica(selectednotifica);
            controller.rimuoviNotificaCancellata(selectednotifica);
        }
    }

    public void passAccetta(){
        DBMSView.setAccettazione(accettazione);
        if(accettazione.getN_accettazioni()-1==0){
            DBMSView.queryAddGiocatore(idPartita,idutentedaacc,scegliSquadra());
            String notifica="L'utente con id: "+idutentedaacc+" è stato accettato nella partita: "+idPartita+" vai a\n controllare le squadre";
            listapart.add(new UtentePart(DBMSView.querygetDettagliUtente(idutentedaacc),1));
            DBMSView.sendNotify(notifica,listapart, 1);
        }
        DBMSView.eliminaNotifica(selectednotifica);
        controller.rimuoviNotificaCancellata(selectednotifica);
    }

    public void passRifiuta(){
        DBMSView.eliminaNotifica(selectednotifica);
        controller.rimuoviNotificaCancellata(selectednotifica);
    }
    public int scegliSquadra(){
        int squadraA=0,squadraB=0;
        for(int i=0;i<listapart.size();i++){
            if(listapart.get(i).getSquadra()==1){
                squadraA++;
            }else{
                squadraB++;
            }
        }
        if(squadraA>squadraB){
            return 2;
        } else {
            return 1;
        }
    }

}
