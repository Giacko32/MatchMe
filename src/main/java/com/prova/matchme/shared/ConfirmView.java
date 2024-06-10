package com.prova.matchme.shared;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.GestioneProfilo.Controller.ProfiloCtrl;
import com.prova.matchme.GestioneSede.Controller.AmministrazioneSedeCtrl;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ConfirmView {

	private AuthCtrl authCtrl;
	private ProfiloCtrl profiloCtrl;
	private AmministrazioneSedeCtrl amministrazioneSedeCtrl;
	private Stage s;
	public ConfirmView(AuthCtrl authCtrl, Stage s){
		this.authCtrl=authCtrl;
		this.s=s;
	}

	public ConfirmView(ProfiloCtrl profiloCtrl, Stage s){
		this.profiloCtrl=profiloCtrl;
		this.s=s;
	}

	public ConfirmView(AmministrazioneSedeCtrl amministrazioneSedeCtrl, Stage s){
		this.amministrazioneSedeCtrl=amministrazioneSedeCtrl;
		this.s=s;
	}
	@FXML
	public void ClickConferma() {
		if(authCtrl!=null){
			this.authCtrl.toLogin();
			s.close();
		}
		if (profiloCtrl!=null){
			this.profiloCtrl.CloseConfirmeView();
			s.close();
		}
		if(amministrazioneSedeCtrl!=null){
			this.amministrazioneSedeCtrl.confirmAbilitazione();
			s.close();
		}

	}

}
