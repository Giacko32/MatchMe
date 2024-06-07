package com.prova.matchme.GestioneProfilo.Interfacce;


import com.prova.matchme.Entity.PartitaStorico;
import com.prova.matchme.GestioneProfilo.Controller.ProfiloCtrl;
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


}
