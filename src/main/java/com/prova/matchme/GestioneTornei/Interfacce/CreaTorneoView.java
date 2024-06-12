package com.prova.matchme.GestioneTornei.Interfacce;

import com.prova.matchme.GestioneTornei.Controller.AmministrazioneTorneiCtrl;
import com.prova.matchme.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.function.UnaryOperator;

public class CreaTorneoView {

    private AmministrazioneTorneiCtrl amminCtrl;
    private Stage stage;

    @FXML
    private ComboBox<String> sportComboBox;

    @FXML
    private Spinner<Integer> livelloSpinner;

    @FXML
    private Spinner<Integer> numeroSquadreSpinner;

    @FXML
    private DatePicker dataInizioPicker;

    @FXML
    private DatePicker dataFinePicker;

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
        // Configurare lo Spinner per il livello (0 a 100, editabile)
        livelloSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        livelloSpinner.setEditable(true); // Permette l'inserimento manuale

        // Aggiungere un TextFormatter per permettere solo input numerici
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        };
        TextFormatter<Integer> textFormatter = new TextFormatter<>(filter);
        livelloSpinner.getEditor().setTextFormatter(textFormatter);

        // Configurare lo Spinner per il numero di squadre (4, 8, 16)
        numeroSquadreSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                javafx.collections.FXCollections.observableArrayList(4, 8, 16)
        ));
        numeroSquadreSpinner.setEditable(false); // Non permette l'inserimento manuale

        // Impostare valori iniziali
        livelloSpinner.getValueFactory().setValue(0);
        numeroSquadreSpinner.getValueFactory().setValue(4);
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
        Integer livello = livelloSpinner.getValue();
        Integer numeroSquadre = numeroSquadreSpinner.getValue();
        String dataInizio = dataInizioPicker.getValue() != null ? dataInizioPicker.getValue().toString() : "";
        String dataFine = dataFinePicker.getValue() != null ? dataFinePicker.getValue().toString() : "";

        // Controllare se i campi obbligatori sono vuoti
        if (sport == null || sport.isEmpty() || dataInizio.isEmpty() || dataFine.isEmpty()) {
            Utils.creaPannelloErrore("Campi incompleti");
        } else {
            amminCtrl.PassData(sport, livello, numeroSquadre, dataInizio, dataFine);
            this.back();
        }

    }

}
