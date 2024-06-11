package com.prova.matchme.GestioneSede.Interfacce;


import com.prova.matchme.GestioneSede.Controller.GestionePartiteSedeCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class InsertVincoliView {

	private GestionePartiteSedeCtrl gestionePartiteSedeCtrl;
	private Stage stage;

	public InsertVincoliView(GestionePartiteSedeCtrl gestionePartiteSedeCtrl,Stage s){
		this.gestionePartiteSedeCtrl=gestionePartiteSedeCtrl;
		this.stage=s;
	}
	@FXML
	private RadioButton uomoButton;
	@FXML
	private RadioButton donnaButton;
	@FXML
	private ToggleGroup genderToggleGroup;
	@FXML
	private Spinner<Integer> fromLivello;
	@FXML
	private Spinner<Integer> fromAge;
	@FXML
	private Spinner<Integer> toLivello;
	@FXML
	private Spinner<Integer> toAge;


	@FXML
	public void initialize() {
		SpinnerValueFactory<Integer> fromvaloriLivello =
				new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
		SpinnerValueFactory<Integer> tovaloriLivello =
				new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
		fromLivello.setValueFactory(fromvaloriLivello);
		toLivello.setValueFactory(tovaloriLivello);

		SpinnerValueFactory<Integer> fromvaloriEta =
				new SpinnerValueFactory.IntegerSpinnerValueFactory(18, 90, 18);
		SpinnerValueFactory<Integer> tovaloriEta =
				new SpinnerValueFactory.IntegerSpinnerValueFactory(18, 90, 18);
		fromAge.setValueFactory(fromvaloriEta);
		toAge.setValueFactory(tovaloriEta);

		genderToggleGroup.selectToggle(uomoButton);
	}

	@FXML
	public void ClickCreaPartita() {
		StringBuilder vincoli = new StringBuilder();
		if (uomoButton.isSelected()) {
			vincoli.append("uomo;");
		} else {
			vincoli.append("donna;");
		}
		vincoli.append(fromLivello.getValue().toString() + ";" + toLivello.getValue().toString() + ";" + fromAge.getValue().toString() + ";" + toAge.getValue().toString());
		gestionePartiteSedeCtrl.PassVincoli(vincoli.toString());
	}

	@FXML
	public void goBack() {
		gestionePartiteSedeCtrl.toAdmin();
	}

}
