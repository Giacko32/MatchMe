package com.prova.matchme.GestioneMessaggistica.Interfacce;


import com.prova.matchme.Entity.Chat;
import com.prova.matchme.Entity.Messaggio;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneMessaggistica.Controller.ChatCtrl;
import com.prova.matchme.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ChatView {

	private Stage stage;
	private Utente u;
	private ChatCtrl chatCtrl;
	private ArrayList<Chat> lista;
	private ArrayList<Messaggio> listamex;

	public ChatView(Stage s, Utente u,ChatCtrl chatCtrl, ArrayList<Chat> lista){
		this.u=u;
		this.stage=s;
		this.chatCtrl=chatCtrl;
		this.lista=lista;
	}


	@FXML
	public ListView<Chat> listachat;
	public ImageView manda;


	@FXML
	public void back(){
		this.chatCtrl.toMain();
	}
	@FXML
	public void initialize(){
		ObservableList<Chat> items = FXCollections.observableArrayList(lista);
		listachat.setItems(items);
		manda.setDisable(true);
		listachat.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			this.chatCtrl.setSelectedChat(newValue);
		});
	}

	@FXML
	public ListView<String> listamessaggi;
	public TextArea newmex;
    @FXML
	public void clickNewChat() {
		this.chatCtrl.startCreateNewChat();
	}
    @FXML
	public void showChat(ArrayList<Messaggio> listamex,Chat chat) {
		this.listamex=listamex;
		listamessaggi.setDisable(false);
		newmex.setDisable(false);
		manda.setDisable(false);
		ObservableList<String> items = FXCollections.observableArrayList();
		for(int i=0;i<listamex.size();i++){
			if(chat.getId_other()==listamex.get(i).getIdmittente()){
				items.add("Messaggio: "+listamex.get(i).getMessaggio()+"\n"+"Mittente: "+chat);
			}else{
				items.add("Messaggio: "+listamex.get(i).getMessaggio()+"\n"+"Mittente: "+u.toString());
			}
		}
		listamessaggi.setItems(items);
	}

	@FXML
	public void ShowNewMessage(Messaggio m,Chat chat){
		newmex.setText("");
		this.listamex.add(m);
		ObservableList<String> items = FXCollections.observableArrayList();
		for(int i=0;i<this.listamex.size();i++){
			if(chat.getId_other()==listamex.get(i).getIdmittente()){
				items.add("Messaggio: "+listamex.get(i).getMessaggio()+"\n"+"Mittente: "+chat);
			}else{
				items.add("Messaggio: "+listamex.get(i).getMessaggio()+"\n"+"Mittente: "+u.toString());
			}
		}
		listamessaggi.setItems(items);
	}

	@FXML
	public void ClickInvia(){
		this.chatCtrl.InviaMessaggio(newmex.getText());
	}


}
