package com.prova.matchme.Autenticazione.Interfacce;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneMessaggistica.Controller.ChatCtrl;
import com.prova.matchme.GestioneNotifiche.Controller.NotifyCtrl;
import com.prova.matchme.GestionePartita.Controller.PartitaCtrl;
import com.prova.matchme.GestioneProfilo.Controller.ProfiloCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AllenaView {

    private AuthCtrl authCtrl;
    private Utente u;
    private Stage s;

    public AllenaView(AuthCtrl authCtrl, Utente u, Stage s) {
        this.u = u;
        this.authCtrl = authCtrl;
        this.s = s;
    }

    @FXML
    private Label nome;
    @FXML
    private AnchorPane Ancorpane;

    @FXML
    public void initialize() {
        // Load the image
        Image backgroundImage = new Image(getClass().getResource("/com/prova/matchme/images/background.png").toExternalForm());

        // Create a BackgroundImage
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );

        // Set the background to the AnchorPane
        Ancorpane.setBackground(new Background(bgImage));

        nome.setText(u.toString());

    }

    @FXML
    public void ClickLogout() {
        this.authCtrl.toConfirm();
    }

    @FXML
    public void ClickStorico() {
        new ProfiloCtrl(this.s, this.u);
    }

    @FXML
    public void clickChat() {
        new ChatCtrl(s, u);
    }

    @FXML
    public void ClickProfile() {
        new ProfiloCtrl(this.s, u, null);
    }

    public void ClickAssegnaBonus() {

    }
    @FXML
    public void ClickNotifiche() {
        new NotifyCtrl(s,u);
    }

    @FXML
    public void ClickVisualizzaCampiLiberi() {
        new PartitaCtrl(u, s);
    }


    public void ClickCreaAllenamento() {

    }

    public void ShowBnd() {

    }

}
