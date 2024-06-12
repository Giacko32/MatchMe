package com.prova.matchme.GestioneSede.Interfacce;


import com.prova.matchme.GestioneAllenamenti.Controller.GestioneAllCtrl;
import com.prova.matchme.GestioneSede.Controller.GestionePartiteSedeCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.time.LocalDate;

public class SelectDataView {

	private Stage s;
	private GestionePartiteSedeCtrl gestionePartiteSedeCtrl;
	private GestioneAllCtrl gestioneAllCtrl;
	private LocalDate data;

	public SelectDataView(Stage s,GestionePartiteSedeCtrl gestionePartiteSedeCtrl,LocalDate data){
		this.s=s;
		this.data=data;
		this.gestionePartiteSedeCtrl=gestionePartiteSedeCtrl;
	}

	public SelectDataView(Stage s, GestioneAllCtrl gestioneAllCtrl, LocalDate data){
		this.s = s;
		this.gestioneAllCtrl = gestioneAllCtrl;
		this.data = data;
	}

	@FXML
	public DatePicker date;
	@FXML
	public void initialize(){
		date.setValue(data);
	}
    @FXML
	public void ClickVediPartite() {
		if(this.gestioneAllCtrl != null){
			this.gestioneAllCtrl.passData(date.getValue());
			s.close();
		} else {
			this.gestionePartiteSedeCtrl.PassVerificaPartite(date.getValue());
			s.close();
		}
	}


}
