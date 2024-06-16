package com.prova.matchme.GestioneAllenamenti.Controller;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Autenticazione.Interfacce.AllenaView;
import com.prova.matchme.shared.DBMSView;
import com.prova.matchme.Entity.Campo;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneAllenamenti.Interfacce.CreaAllenamentoView;
import com.prova.matchme.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CreaAllCtrl {

	private Utente utente;
	private Stage stage;

	public CreaAllCtrl(Utente utente, Stage stage){
		this.utente = utente;
		this.stage = stage;
		Utils.cambiaInterfaccia("FXML/Crea Allenamento.fxml", stage, c -> {
			return new CreaAllenamentoView(this);
		});
	}

	public ArrayList<Campo> VerificaClicked(LocalDate dateTime, String sport) {
		if(dateTime != null && sport != null){
			ArrayList<Campo> campi = DBMSView.queryGetOrariAllenamtento(dateTime, sport);
			LocalDateTime now = LocalDateTime.now();
			campi.removeIf(campo -> campo.getOrario().isBefore(now));
			return campi;
		} else {
			Utils.creaPannelloErrore("Non hai inserito\ntutti i campi");
			return null;
		}
	}

	public void Creaclicked(Campo selected, Integer n_partecipanti) {
		if(n_partecipanti != 0){
			DBMSView.queryInsertNuovoAllenamento(selected, utente.getId(), n_partecipanti);
		}
	}

	public void toMain() {
		if (utente != null) {
			if (utente.getTipo().equals("al")) {
				Utils.cambiaInterfaccia("FXML/Allena-view.fxml", stage, c -> {
					return new AllenaView(new AuthCtrl(stage), utente, stage);
				});
			}
		}
	}

}
