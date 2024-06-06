package com.prova.matchme.GestioneProfilo.Interfacce;

import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneProfilo.Controller.ProfiloCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProfileView {


    private ProfiloCtrl profiloCtrl;
    private Utente u;
    private Gestore g;
    public ProfileView(ProfiloCtrl profiloCtrl, Utente u){
        this.profiloCtrl=profiloCtrl;

        this.u=u;
    }
    public ProfileView(ProfiloCtrl profiloCtrl,  Gestore g){
        this.profiloCtrl=profiloCtrl;

        this.g=g;
    }

    @FXML
    public TextField nomeUtente;
    public TextField cognomeUtente;
    public TextField emailUtente;
    public TextField etaUtente;
    public TextField usernameUtente;
    public Label statoUtente;
    public TextField livello;


    @FXML
    public TextField nomeGestore;
    public TextField cognomeGestore;
    public TextField emailGestore;
    public TextField usernameGestore;

    @FXML
    public void initialize(){
        if(u!=null){
            nomeUtente.setText(u.getNome());
            cognomeUtente.setText(u.getCognome());
            emailUtente.setText(u.getEmail());
            etaUtente.setText(u.getEta());
            usernameUtente.setText(u.getUsername());
            livello.setText(String.valueOf(u.getLivello()));
            String stato;
            if(u.getTipo().equals("t")){
                stato="Tesserato";
            }else if(u.getTipo().equals("nt")){
                stato="Non Tesserato";
            } else{
                stato="Allenatore";
            }
            statoUtente.setText("Stato Utente: "+stato);
        }
        if(g!=null){
            nomeGestore.setText(g.getNome());
            cognomeGestore.setText(g.getCognome());
            emailGestore.setText(g.getEmail());
            usernameGestore.setText(g.getUsername());
        }
    }
    @FXML
    public void back(){
        this.profiloCtrl.toMain();
    }

}
