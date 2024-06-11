package com.prova.matchme.GestioneSede.Interfacce;

import com.prova.matchme.Entity.Campo;
import com.prova.matchme.GestioneSede.Controller.GestionePartiteSedeCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RinviaPartitaView {

	private GestionePartiteSedeCtrl gestionePartiteSedeCtrl;
	public RinviaPartitaView(GestionePartiteSedeCtrl gestionePartiteSedeCtrl){
		this.gestionePartiteSedeCtrl=gestionePartiteSedeCtrl;
	}

	@FXML
	public DatePicker data;
	public ListView<Campo> listacampi;
	public Button rinvia;
    @FXML
	public void ClickCercaDisponibilità() {
		gestionePartiteSedeCtrl.PassData2(data.getValue());
	}
	@FXML
	public void showLista(ArrayList<Campo> listaorari) {
		ObservableList<Campo> items= FXCollections.observableArrayList(listaorari);
		listacampi.setItems(items);
		listacampi.setDisable(false);
		rinvia.setDisable(false);
	}
	@FXML
	public void ClickRinviaPartita() {
      	gestionePartiteSedeCtrl.PassPartitaNewData(LocalDateTime.of(data.getValue(),listacampi.getSelectionModel().getSelectedItem().getOrario().toLocalTime()));
	}
	@FXML
	public void back(){
		gestionePartiteSedeCtrl.toAdmin();
	}

}
