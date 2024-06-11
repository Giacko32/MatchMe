package com.prova.matchme.Threads;

import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.GestioneSede.Controller.GestionePartiteSedeCtrl;

public class VerPartite extends Thread{
    private Gestore g;
    private GestionePartiteSedeCtrl gestionePartiteSedeCtrl;
    private boolean running=true;


    public VerPartite(Gestore g){
        this.g=g;
    }

    @Override
    public void run() {
        this.gestionePartiteSedeCtrl=new GestionePartiteSedeCtrl(g);
        System.out.println("Thread verifica partite avviato");
        while(running){
            gestionePartiteSedeCtrl.verifica();
            try{
                Thread.sleep( 3600000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void onstop(){
        System.out.println("Thread verifica stoppato");
        running=false;
    }
}
