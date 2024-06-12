package com.prova.matchme.shared;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Entity.PartitaDetails;
import com.prova.matchme.Entity.Torneo;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestionePartita.Controller.PartitaCtrl;
import com.prova.matchme.GestioneProfilo.Controller.ProfiloCtrl;
import com.prova.matchme.GestioneSede.Controller.AmministrazioneSedeCtrl;
import com.prova.matchme.GestioneTornei.Controller.AmministrazioneTorneiCtrl;
import com.prova.matchme.GestioneTornei.Controller.TorneiCtrl;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ConfirmView {

	private AuthCtrl authCtrl;
	private ProfiloCtrl profiloCtrl;
	private AmministrazioneSedeCtrl amministrazioneSedeCtrl;
	private PartitaCtrl partitaCtrl;
	private TorneiCtrl torneiCtrl;
	private AmministrazioneTorneiCtrl amminCtrl;
	private Stage s;
	private PartitaDetails partitaDetails;
	private Torneo torneo;
	private Utente utente;
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

	public ConfirmView(PartitaCtrl partitaCtrl, Stage s){
		this.partitaCtrl = partitaCtrl;
		this.s = s;
	}

	public ConfirmView(PartitaCtrl partitaCtrl, Stage s, PartitaDetails partitaDetails){
		this.partitaCtrl = partitaCtrl;
		this.s = s;
		this.partitaDetails = partitaDetails;
	}

	public ConfirmView(TorneiCtrl torneoCtrl, Stage s, Utente utente, Torneo torneo){
		this.torneiCtrl = torneoCtrl;
		this.s = s;
		this.utente = utente;
		this.torneo = torneo;
	}
	public ConfirmView( AmministrazioneTorneiCtrl amminCtrl, Stage s, Torneo torneo){
		this.amminCtrl = amminCtrl;
		this.s = s;
		this.torneo = torneo;
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
		if(partitaCtrl != null && partitaDetails == null){
			this.partitaCtrl.passConferma();
			s.close();
		}
		if(partitaCtrl != null && partitaDetails != null){
			this.partitaCtrl.deleteWarning(partitaDetails);
			s.close();
		}
		if(torneiCtrl !=  null){
			this.torneiCtrl.CloseConfirmView(utente, torneo);
			s.close();
		}
		if(amminCtrl != null){
			this.amminCtrl.CloseConfirmView(torneo);
			s.close();
		}

	}

}
