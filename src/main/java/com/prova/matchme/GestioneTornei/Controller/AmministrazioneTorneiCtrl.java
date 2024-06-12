package com.prova.matchme.GestioneTornei.Controller;


import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.GestioneTornei.Interfacce.VisualizzaDettagliTuttiITornei;
import com.prova.matchme.GestioneTornei.Interfacce.VisualizzaTorneiGestori;
import com.prova.matchme.Utils;
import javafx.stage.Stage;

public class AmministrazioneTorneiCtrl {

	private Stage stage;
	private Gestore gestore;
	private VisualizzaTorneiGestori boundaryGestori;

	public AmministrazioneTorneiCtrl(Stage stage, Gestore gestore) {
		this.stage = stage;
		this.gestore = gestore;
	}

	public void mostraTorneiSede(Stage stage){
		stage.close();
		Utils.cambiaInterfaccia("FXML/VisualizzaTorneiGestore.fxml", stage, c -> {
			boundaryGestori = new VisualizzaTorneiGestori(DBMSView.queryGetTorneiSede(gestore),stage, this);
			return boundaryGestori;
		});
	}

	public void TorneoSelezionato() {

	}

	public void NuovoTorneo() {

	}

	public void PassData() {

	}

	public void ModificaTorneo() {

	}

	public void CancellaTorneo() {

	}

	public void CloseConfirmView() {

	}

	public void SquadreInAttesa() {

	}

	public void PassSquadra() {

	}

	public void AccettaCliccato() {

	}

	public void RifiutaCliccato() {

	}

	public void CheckSquadre() {

	}

	public void CloseWarningView() {

	}

	public void checkTime() {

	}

	public void AccoppiaSquadre() {

	}

	public void MatchPartitaCampo() {

	}

	public void checkNumeroTornei() {

	}

	public void checkNumeroPartite() {

	}

}
