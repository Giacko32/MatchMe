package com.prova.matchme.GestioneMessaggistica.Controller;


import com.prova.matchme.Autenticazione.Interfacce.RegisterView;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneMessaggistica.Interfacce.ChatView;
import com.prova.matchme.Utils;
import javafx.stage.Stage;

public class ChatCtrl {

	private Stage s;
	private Utente u;

	public ChatCtrl(Stage s, Utente u){
		this.s=s;
		this.u=u;
		Utils.cambiaInterfaccia("FXML/Chat.fxml", s, c -> {
			return new ChatView(s,u,this);
		});
	}

	private int idmittente;

	private int iddestinatario;

	public void setSelectedChat() {

	}

	public void startCreateNewChat() {

	}

	public void searchUser(String name, String cognome) {

	}

	public void createNewChat(String userid, String nomeuser, String cognomeuser) {

	}

	public void InviaMessaggio() {

	}

}
