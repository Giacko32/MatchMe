package com.prova.matchme.GestioneMessaggistica.Interfacce;


import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneMessaggistica.Controller.ChatCtrl;
import com.prova.matchme.Utils;
import javafx.stage.Stage;

public class ChatView {

	private Stage stage;
	private Utente u;
	private ChatCtrl chatCtrl;

	public ChatView(Stage s, Utente u,ChatCtrl chatCtrl){
		this.u=u;
		this.stage=s;
		this.chatCtrl=chatCtrl;
	}


	public void SelezionaChat() {

	}

	public void clickNewChat() {

	}

	public void showChat() {

	}

}
