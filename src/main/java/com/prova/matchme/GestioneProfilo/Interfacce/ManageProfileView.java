package com.prova.matchme.GestioneProfilo.Interfacce;


import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneProfilo.Controller.ProfiloCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ManageProfileView {


    private ProfiloCtrl profiloCtrl;
    private Utente u;
    private Gestore g;

    public ManageProfileView(ProfiloCtrl profiloCtrl, Utente u) {
        this.profiloCtrl = profiloCtrl;
        this.u = u;
    }

    public ManageProfileView(ProfiloCtrl profiloCtrl, Gestore g) {
        this.profiloCtrl = profiloCtrl;
        this.g = g;
    }

    @FXML
    private TextField nome;
    public TextField username;
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
            tastoabb.setVisible(false);
        }
    }

    public void ClickVisualizzaAccount() {

    }

    public void ClickModifyAccount() {

    }

    public void ClickModifyPassword() {

    }

    public void ShowBnd() {

    }

}
