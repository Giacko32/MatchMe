package com.prova.matchme.GestioneNotifiche.Controller;

import com.prova.matchme.CustomStage;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.Notifica;
import com.prova.matchme.Entity.Partita;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneNotifiche.Interfacce.NotifyView;
import com.prova.matchme.Utils;
import com.prova.matchme.shared.ChoiceView;
import javafx.stage.Stage;

import java.util.ArrayList;

public class NotifyCtrl {

    private Stage s;
    private Utente u;
    private int idPartita;
    private int numpartsport;

    public NotifyCtrl(Stage s,Utente u){
        this.u=u;
        this.s=s;
        ArrayList<Notifica> listanotifiche=DBMSView.queryGetNotifiche(u.getId());
        Utils.cambiaInterfaccia("FXML/Notifiche.fxml",s,c->{
            return new NotifyView(s,listanotifiche,this);
        });
    }

    public void rispostaInvito(String messaggio){
        String[] parole=messaggio.split(" ");
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
            Partita p=DBMSView.queryGetDetailsPartita(idPartita);
            int npart=DBMSView.querygetNpartecipanti(idPartita);
            if(DBMSView.queryGiocatoreOccupato(u.getId(),p.getDataOra())){
                if(npart<numpartsport){

                }else{
                    Utils.cambiaInterfaccia()
                }
            }


        }
    }
}
