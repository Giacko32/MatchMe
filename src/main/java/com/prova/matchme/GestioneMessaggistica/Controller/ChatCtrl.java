package com.prova.matchme.GestioneMessaggistica.Controller;


import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AllenaView;
import com.prova.matchme.Autenticazione.Interfacce.MainView;
import com.prova.matchme.shared.DBMSView;
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
    private Chat selectedchat;
    private ArrayList<Chat> listaChat;

    public ChatCtrl(Stage s, Utente u) {
        this.s = s;
        this.u = u;
        listaChat = DBMSView.queryGetChat(u.getId());
        Utils.cambiaInterfaccia("FXML/Chat.fxml", s, c -> {
            controller = new ChatView(s, u, this, listaChat);
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
        selectedchat=chat;
        ArrayList<Messaggio> listam = DBMSView.getdetailChat(chat);
        controller.showChat(listam);
    }

    public void startCreateNewChat() {
        Utils.cambiaInterfaccia("FXML/Crea Nuova Chat.fxml", s, c -> {
            controllernew = new NewChatView(this);
            return controllernew;
        });

    }

    public void searchUser(String nome, String cognome) {
        ArrayList<Utente> listautenti=DBMSView.querySearchUser(nome,cognome,u.getId());
        controllernew.updateLista(listautenti);
    }
    public void createNewChat(Utente utente) {
        if(utente!=null){
            Chat chat=new Chat(DBMSView.queryCreatenewChat(u.getId(),utente.getId()), utente.getId(), utente.toString());
            listaChat.add(chat);
            Utils.cambiaInterfaccia("FXML/Chat.fxml", s, c -> {
                controller = new ChatView(s, u, this, listaChat);
                return controller;
            });
        }else{
            Utils.creaPannelloErrore("Non è stato selezionato\n nessun utente");
        }
    }
    public void InviaMessaggio(String messaggio) {
        DBMSView.queryNewMessage(new Messaggio(u.getId(),messaggio),selectedchat.getId());
        controller.ShowNewMessage(new Messaggio(u.getId(),messaggio));
    }

}
