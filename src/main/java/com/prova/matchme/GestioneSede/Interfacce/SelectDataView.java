package com.prova.matchme.GestioneSede.Interfacce;


import com.prova.matchme.GestioneSede.Controller.GestionePartiteSedeCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.time.LocalDate;

public class SelectDataView {


	private Stage s;
	private GestionePartiteSedeCtrl gestionePartiteSedeCtrl;
	private LocalDate data;

	public SelectDataView(Stage s,GestionePartiteSedeCtrl gestionePartiteSedeCtrl,LocalDate data){
		this.s=s;
		this.data=data;
		this.gestionePartiteSedeCtrl=gestionePartiteSedeCtrl;
	}
	@FXML
	public DatePicker date;
	@FXML
	public void initialize(){
		date.setValue(data);
	}
    @FXML
	public void ClickVediPartite() {
		this.gestionePartiteSedeCtrl.PassVerificaPartite(date.getValue());
		s.close();
	}


}
