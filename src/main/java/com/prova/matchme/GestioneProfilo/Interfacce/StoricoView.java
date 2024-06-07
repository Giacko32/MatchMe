package com.prova.matchme.GestioneProfilo.Interfacce;


import com.prova.matchme.Entity.PartitaStorico;
import com.prova.matchme.GestioneProfilo.Controller.ProfiloCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;

public class StoricoView {

    private ProfiloCtrl profiloCtrl;
    private Stage s;
    private ArrayList<PartitaStorico> lista;
    public StoricoView(ProfiloCtrl profiloCtrl, Stage s, ArrayList<PartitaStorico> lista){
        this.profiloCtrl=profiloCtrl;
        this.s=s;
        this.lista=lista;
    }

    @FXML
    public ListView<String> listapartitegiocate;
    @FXML
    public void initialize(){
        ObservableList<String> items = FXCollections.observableArrayList();
        for(int i=0;i<lista.size();i++){
            items.add(lista.get(i).torna());
        }
        listapartitegiocate.setItems(items);
        listapartitegiocate.setDisable(true);
    }

    @FXML
    public void back(){
        this.profiloCtrl.toMain();
    }

}
