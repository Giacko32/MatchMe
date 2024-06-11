package com.prova.matchme.GestionePartita.Interfacce;


import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.Campo;
import com.prova.matchme.Entity.Partita;
import com.prova.matchme.Entity.Sede;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestionePartita.Controller.PartitaCtrl;
import com.prova.matchme.Utils;
import jakarta.mail.Part;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PrenotazionePartitaView {

    private Utente utente;
    private PartitaCtrl partitaCtrl;
    private Campo selectedCampo;
    private Sede selectedSede;
    private Stage stage;
    @FXML
    private ToggleGroup ToggleGroup;
    @FXML
    private RadioButton uomoButton;
    @FXML
    private RadioButton donnaButton;
    @FXML
    private RadioButton privataButton;
    @FXML
    private RadioButton pubblicaButton;
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

    public PrenotazionePartitaView(Utente u, PartitaCtrl partitaCtrl, Campo campo, Sede selectedSede, Stage stage) {
        this.utente = u;
        this.partitaCtrl = partitaCtrl;
        this.selectedCampo = campo;
        this.selectedSede = selectedSede;
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> fromvaloriLivello =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, utente.getLivello().intValue());
        SpinnerValueFactory<Integer> tovaloriLivello =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, utente.getLivello().intValue());
        fromLivello.setValueFactory(fromvaloriLivello);
        toLivello.setValueFactory(tovaloriLivello);

        SpinnerValueFactory<Integer> fromvaloriEta =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(18, 90, Integer.parseInt(utente.getEta()));
        SpinnerValueFactory<Integer> tovaloriEta =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(18, 90, Integer.parseInt(utente.getEta()));
        fromAge.setValueFactory(fromvaloriEta);
        toAge.setValueFactory(tovaloriEta);

        ToggleGroup.selectToggle(pubblicaButton);
        genderToggleGroup.selectToggle(uomoButton);
    }


    public void ClickCreaPartita() {
        String tipo;
        if (pubblicaButton.isSelected()) {
            tipo = "ppub";
        } else {
            tipo = "ppriv";
        }
        StringBuilder vincoli = new StringBuilder();
        if (uomoButton.isSelected()) {
            vincoli.append("uomo;");
        } else {
            vincoli.append("donna;");
        }
        if(fromLivello.getValue() < toLivello.getValue() && fromAge.getValue() < toAge.getValue()) {
            vincoli.append(fromLivello.getValue().toString() + ";" + toLivello.getValue().toString() + ";" + fromAge.getValue().toString() + ";" + toAge.getValue().toString());
            Partita newPartita = new Partita(0, selectedCampo.getId_campo(), selectedCampo.getOrario(), tipo, vincoli.toString());
            partitaCtrl.passPartita(newPartita);
        }else{
            Utils.creaPannelloErrore("Età o livello errati");
        }
    }

    @FXML
    public void goBack() {
        partitaCtrl.passSedeSportData(selectedSede, selectedCampo.getSport(), selectedCampo.getOrario(), stage,1);
    }

}
