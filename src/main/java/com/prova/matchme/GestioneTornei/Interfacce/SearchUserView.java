package com.prova.matchme.GestioneTornei.Interfacce;


import com.prova.matchme.GestioneTornei.Controller.TorneiCtrl;

public class SearchUserView {


	private TorneiCtrl torneiCtrl;
	public SearchUserView(TorneiCtrl torneiCtrl) {
		this.torneiCtrl = torneiCtrl;
	}
	public void InsertData() {

	}

	public void ClickCerca() {

	}

	public void MostraLista() {

	}

	public void SelectGiocatore() {

	}

	public void ClickAggiungi() {

	}

	public void back() {
		this.torneiCtrl.toMain();
	}

}
