package com.prova.matchme.GestioneAllenamenti.Interfacce;

import com.prova.matchme.GestioneAllenamenti.Controller.GestioneAllCtrl;

public class GestioneAllView {

	private GestioneAllCtrl gestioneAllCtrl;


	public GestioneAllView(GestioneAllCtrl gestioneAllCtrl){
		this.gestioneAllCtrl = gestioneAllCtrl;
	}

	public void ClickPrenota() {
		gestioneAllCtrl.PrenotaClicked();
	}

	public void ClickMieiAllenamenti() {
		gestioneAllCtrl.ClickMieiAllenamenti();
	}


}
