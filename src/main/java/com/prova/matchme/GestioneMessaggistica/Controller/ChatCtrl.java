package com.prova.matchme.GestioneMessaggistica.Controller;


import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AllenaView;
import com.prova.matchme.Autenticazione.Interfacce.MainView;
import com.prova.matchme.Autenticazione.Interfacce.RegisterView;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.Chat;
import com.prova.matchme.Entity.Messaggio;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneMessaggistica.Interfacce.ChatView;
import com.prova.matchme.GestioneMessaggistica.Interfacce.NewChatView;
import com.prova.matchme.Utils;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ChatCtrl {

    private Stage s;
    private Utente u;
    private ChatView controller;
    private NewChatView controllernew;

    public ChatCtrl(Stage s, Utente u) {
        this.s = s;
        this.u = u;
        ArrayList<Chat> lista = DBMSView.queryGetChat(u.getId());
        Utils.cambiaInterfaccia("FXML/Chat.fxml", s, c -> {
            controller = new ChatView(s, u, this, lista);
            return controller;
        });
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

    public void setSelectedChat(Chat chat) {
        ArrayList<Messaggio> listam = DBMSView.getdetailChat(chat);
        controller.showChat(listam, chat);
    }

    public void startCreateNewChat() {
        Utils.cambiaInterfaccia("FXML/Crea Nuova Chat.fxml", s, c -> {
            controllernew = new NewChatView(this);
            return controllernew;
        });

    }

    public void searchUser(String nome, String cognome) {
        ArrayList<Utente> listautenti=DBMSView.querySearchUser(nome,cognome);
        controllernew.updateLista(listautenti);
    }

    public void createNewChat(Utente utente) {
        if(utente!=null){
            DBMSView.queryCreatenewChat(u.getId(),utente.getId());
            ArrayList<Chat> lista = DBMSView.queryGetChat(u.getId());
            Utils.cambiaInterfaccia("FXML/Chat.fxml", s, c -> {
                controller = new ChatView(s, u, this, lista);
                return controller;
            });
        }else{
            Utils.creaPannelloErrore("Non è stato selezionato\nnessun utente");
        }
    }

    public void InviaMessaggio() {

    }

}
