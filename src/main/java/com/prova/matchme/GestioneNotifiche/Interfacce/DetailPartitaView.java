package com.prova.matchme.GestioneNotifiche.Interfacce;


import com.prova.matchme.Entity.Notifica;
import com.prova.matchme.Entity.UtentePart;
import com.prova.matchme.GestioneNotifiche.Controller.NotifyCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class DetailPartitaView {


	private Stage s;
	private NotifyCtrl notifyCtrl;
	private ArrayList<UtentePart> listautenti;
	private int numpart;

	public DetailPartitaView(Stage s, NotifyCtrl notifyCtrl, ArrayList<UtentePart> list,int numpart){
		this.s=s;
		this.notifyCtrl=notifyCtrl;
		this.listautenti=list;
		this.numpart=numpart;
		System.out.println(listautenti);
	}

	@FXML
	public ListView<UtentePart> SquadraA;
	public ListView<UtentePart> SquadraB;
	public Button buttonA;
	public Button buttonB;
	@FXML
	public void initialize(){
		ObservableList<UtentePart> itemsA = FXCollections.observableArrayList();
		ObservableList<UtentePart> itemsB = FXCollections.observableArrayList();
		for(int i=0;i<listautenti.size();i++){
			if(listautenti.get(i).getSquadra()==1){
				itemsA.add(listautenti.get(i));
			}else{
				itemsB.add(listautenti.get(i));
			}
		}
		SquadraA.setItems(itemsA);
		SquadraB.setItems(itemsB);
		if (itemsA.size()==numpart/2){
			buttonA.setDisable(true);
		}
		if (itemsB.size()==numpart/2){
			buttonB.setDisable(true);
		}
	}
	@FXML
	public void back(){
		this.notifyCtrl.toMain();
	}

	@FXML
	public void selectA(){
		this.notifyCtrl.squadraselected(1);
		s.close();
	}
	@FXML
	public void selectB(){
		this.notifyCtrl.squadraselected(2);
		s.close();
	}




}
