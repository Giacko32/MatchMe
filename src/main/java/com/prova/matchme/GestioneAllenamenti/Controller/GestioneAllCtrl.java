package com.prova.matchme.GestioneAllenamenti.Controller;


import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AllenaView;
import com.prova.matchme.Autenticazione.Interfacce.MainView;
import com.prova.matchme.CustomStage;
import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.Allenamento;
import com.prova.matchme.Entity.Partita;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneAllenamenti.Interfacce.DettagliAllenamentiView;
import com.prova.matchme.GestioneAllenamenti.Interfacce.DettagliMioAllenamentoView;
import com.prova.matchme.GestioneAllenamenti.Interfacce.GestioneAllView;
import com.prova.matchme.GestionePartita.Interfacce.SelectTipoPartiteView;
import com.prova.matchme.Utils;
import javafx.stage.Stage;

public class GestioneAllCtrl {

	private Utente utente;
	private Stage stage;
	private CustomStage stageDialog = new CustomStage("Allenamenti");

	public GestioneAllCtrl(Utente utente, Stage s){
		this.utente = utente;
		this.stage = s;
		Utils.cambiaInterfaccia("FXML/SelezioneAllenamento.fxml", stageDialog, c -> {
			return new GestioneAllView(this);
		}, 350, 170);
	}

	public void PrenotaClicked() {
		//DBMSView.
		stageDialog.close();
		Utils.cambiaInterfaccia("FXML/Allenamenti.fxml", stage , c -> {
			return new DettagliAllenamentiView(this, DBMSView.queryGetAllenamenti());
		});
	}

	public Allenamento AllenamentoClicked(Partita allenamento) {
		return DBMSView.queryGetDettagliAllenamento(allenamento);
	}

	public void PrenotaCliccato() {

	}

	public void CheckOccupato() {

	}

	public void CloseWarningView() {

	}

	public void ClickMieiAllenamenti() {
		stageDialog.close();
		Utils.cambiaInterfaccia("FXML/VisualizzaMieiAllenamenti.fxml", stage , c -> {
			return new DettagliMioAllenamentoView(this, DBMSView.queryGetMieiAllenamenti(utente));
		});
	}

	public void CancellaClicked(Allenamento all) {
		if(this.CheckAllenatore(all)){
			
		} else {

		}
	}

	public boolean CheckAllenatore(Allenamento all) {
		return utente.equals(all.allenatore);
	}

	public void toMain() {
		if (utente != null) {
			if (utente.getTipo().equals("al")) {
				Utils.cambiaInterfaccia("FXML/Allena-view.fxml", stage, c -> {
					return new AllenaView(new AuthCtrl(stage), utente, stage);
				});
			} else {
				Utils.cambiaInterfaccia("FXML/Main-view.fxml", stage, c -> {
					return new MainView(new AuthCtrl(stage), utente, stage);
				});
			}
		}
	}
}
