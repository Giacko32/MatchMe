package com.prova.matchme.GestioneTornei.Interfacce;

import com.prova.matchme.GestioneTornei.Controller.AmministrazioneTorneiCtrl;
import com.prova.matchme.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public class CreaTorneoView {

    private AmministrazioneTorneiCtrl amminCtrl;
    private Stage stage;

    @FXML
    private ComboBox<String> sportComboBox;

    @FXML
    private Spinner<Integer> livelloMinSpinner;

    @FXML
    private Spinner<Integer> livelloMaxSpinner;

    @FXML
    private Spinner<Integer> numeroSquadreSpinner;

    @FXML
    private DatePicker dataInizioPicker;

    @FXML
    private DatePicker dataFinePicker;
    Map<String, Integer> sportGiocatoriMap = new HashMap<>();



    @FXML
    public void initialize() {
        // Popolare il ComboBox con gli sport
        sportComboBox.getItems().addAll(
                "Calcio a 5",
                "Tennis singolo",
                "Tennis doppio",
                "Padel singolo",
                "Padel doppio"
        );
        // Configurare lo Spinner per il livello minimo (da 0 a 100, editabile)
        livelloMinSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        livelloMinSpinner.setEditable(true); // Permette l'inserimento manuale

        // Configurare lo Spinner per il livello massimo (da 0 a 100, editabile)
        livelloMaxSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 100));
        livelloMaxSpinner.setEditable(true); // Permette l'inserimento manuale

        // Impostare valori iniziali per livello minimo e massimo
        livelloMinSpinner.getValueFactory().setValue(0);
        livelloMaxSpinner.getValueFactory().setValue(100);

        // Assicurarsi che il valore massimo selezionabile nello spinner del livello minimo
        // sia inferiore o uguale al valore massimo selezionabile nello spinner del livello massimo
        livelloMinSpinner.getValueFactory().valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue > livelloMaxSpinner.getValue()) {
                livelloMinSpinner.getValueFactory().setValue(oldValue);
            }
        });

        livelloMaxSpinner.getValueFactory().valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue < livelloMinSpinner.getValue()) {
                livelloMaxSpinner.getValueFactory().setValue(oldValue);
            }
        });

        // Aggiungere un TextFormatter per permettere solo input numerici
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        };

        TextFormatter<Integer> livelloMinTextFormatter = new TextFormatter<>(filter);
        livelloMinSpinner.getEditor().setTextFormatter(livelloMinTextFormatter);

        TextFormatter<Integer> livelloMaxTextFormatter = new TextFormatter<>(filter);
        livelloMaxSpinner.getEditor().setTextFormatter(livelloMaxTextFormatter);

        // Configurare lo Spinner per il numero di squadre (4, 8, 16)
        numeroSquadreSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                javafx.collections.FXCollections.observableArrayList(4, 8, 16)
        ));
        numeroSquadreSpinner.setEditable(false); // Non permette l'inserimento manuale

        // Aggiungere un TextFormatter per permettere solo input numerici
        TextFormatter<Integer> squadreTextFormatter = new TextFormatter<>(filter);
        numeroSquadreSpinner.getEditor().setTextFormatter(squadreTextFormatter);

        // Impostare valori iniziali
        numeroSquadreSpinner.getValueFactory().setValue(4);
        //mappa
        sportGiocatoriMap.put("Calcio a 5", 5);
        sportGiocatoriMap.put("Tennis singolo", 1);
        sportGiocatoriMap.put("Tennis doppio", 2);
        sportGiocatoriMap.put("Padel singolo", 1);
        sportGiocatoriMap.put("Padel doppio", 2);
    }


    public CreaTorneoView(Stage stage, AmministrazioneTorneiCtrl amminCtrl ) {
        this.stage = stage;
        this.amminCtrl = amminCtrl;
    }

    @FXML
    public void back() {
        this.amminCtrl.toMain();
    }


    @FXML
    public void clickConferma() {
        String sport = sportComboBox.getValue();
        Integer livelloMin = livelloMinSpinner.getValue();
        Integer livelloMax = livelloMaxSpinner.getValue();
        Integer numeroSquadre = numeroSquadreSpinner.getValue();
        String dataInizio = dataInizioPicker.getValue() != null ? dataInizioPicker.getValue().toString() : "";
        String dataFine = dataFinePicker.getValue() != null ? dataFinePicker.getValue().toString() : "";
        int numeroGiocatoriPerSquadra = sportGiocatoriMap.getOrDefault(sport, 0); // Ottenere il numero di giocatori per squadra dalla mappa

        // Controllare se i campi obbligatori sono vuoti
        if (sport == null || sport.isEmpty() || dataInizio.isEmpty() || dataFine.isEmpty()) {
            Utils.creaPannelloErrore("Campi incompleti");
        } else {
            amminCtrl.PassData(sport, livelloMin, livelloMax, numeroSquadre, dataInizio, dataFine, numeroGiocatoriPerSquadra);
            this.back();
        }

    }

}
