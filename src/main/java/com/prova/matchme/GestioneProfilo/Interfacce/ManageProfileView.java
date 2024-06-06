package com.prova.matchme.GestioneProfilo.Interfacce;


import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneProfilo.Controller.ProfiloCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ManageProfileView {


    private ProfiloCtrl profiloCtrl;
    private Utente u;
    private Gestore g;
    private Stage s;
    public ManageProfileView(ProfiloCtrl profiloCtrl, Utente u, Stage s) {
        this.profiloCtrl = profiloCtrl;
        this.u = u;
        this.s=s;
    }

    public ManageProfileView(ProfiloCtrl profiloCtrl, Gestore g,Stage s) {
        this.profiloCtrl = profiloCtrl;
        this.g = g;
        this.s=s;
    }

    @FXML
    public Button tastoabb;
    public AnchorPane AnchorProfilo;

    @FXML
    public void initialize() {
        if (u != null) {
            if (u.getTipo().equals("nt")) {
                tastoabb.setDisable(false);
            }
        }
        if(g != null){
            AnchorProfilo.setStyle("-fx-background-color:#E89A3F;");
        }
    }

    @FXML
    public void ClickVisualizzaAccount() {
        this.profiloCtrl.VisualizzaAccount();
        s.close();
    }
    @FXML
    public void ClickModifyAccount() {
        this.profiloCtrl.ModificaDati();
        s.close();
    }
    @FXML
    public void ClickModifyPassword() {
        this.profiloCtrl.ModificaPassword();
        s.close();
    }
    @FXML
    public void back() {
        this.profiloCtrl.toMain();
        s.close();
    }
}
