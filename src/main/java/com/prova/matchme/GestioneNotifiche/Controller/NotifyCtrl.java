package com.prova.matchme.GestioneNotifiche.Controller;

import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.Notifica;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneNotifiche.Interfacce.NotifyView;
import com.prova.matchme.Utils;
import javafx.stage.Stage;

import java.util.ArrayList;

public class NotifyCtrl {

    private Stage s;
    private Utente u;
    private int idPartita;

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
        System.out.println(idPartita);

    }
}
