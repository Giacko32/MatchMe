package com.prova.matchme.GestionePartita.Interfacce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class DetailsTuttePartiteView {

	@FXML
	private ListView<String> listaPartite;

	@FXML
	public void initialize(){
		ObservableList<String> items = FXCollections.observableArrayList(
				"1:Elemento 1", "2:Elemento 2", "Elemento 3", "Elemento 4");
		listaPartite.setItems(items);

		listaPartite.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				System.out.println(newValue);
			}
		});
	}

	public void ClickPartecipa() {

	}

	public void ShowBnd() {

	}

}
