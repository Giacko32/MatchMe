package com.prova.matchme.GestioneNotifiche.Controller;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AllenaView;
import com.prova.matchme.Autenticazione.Interfacce.MainView;
import com.prova.matchme.CustomStage;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.Notifica;
import com.prova.matchme.Entity.Partita;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.Entity.UtentePart;
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
            case "calcio":{
                numpartsport=10;
                break;
            }
            case "padel", "tennis":{
                numpartsport=2;
                break;
            }
            case "padeld", "tennisd":{
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
            CustomStage s=new CustomStage("Attenzione");
            Partita p=DBMSView.queryGetDetailsPartita(idPartita);
            ArrayList<UtentePart> listautenti =DBMSView.querygetpartecipanti(idPartita);
            if(DBMSView.queryGiocatoreOccupato(u.getId(),p.getDataOra())){
                if(listautenti.size()<numpartsport){
                    Utils.cambiaInterfaccia("FXML/Accetta Invito.fxml",this.s,c->{
                       return new DetailPartitaView(s,this,listautenti,numpartsport);
                    });
                }else{
                    Utils.cambiaInterfaccia("FXML/Dialog Num Max.fxml",s,c->{
                        return new WarningView(s);
                    },350,150);
                }
            }else{
                Utils.cambiaInterfaccia("FXML/DialogUtenteImpegnato.fxml",s,c->{
                    return new WarningView(s);
                },350,150);
            }
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
}
