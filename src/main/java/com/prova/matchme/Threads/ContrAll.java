package com.prova.matchme.Threads;

import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.GestioneSede.Controller.AmministrazioneSedeCtrl;

import java.time.LocalTime;

public class ContrAll extends Thread{
    private Gestore g;
    private boolean running=true;
    private AmministrazioneSedeCtrl amministrazioneSedeCtrl;

    public ContrAll(Gestore g){
        this.g=g;
    }

    @Override
    public void run() {
        this.amministrazioneSedeCtrl=new AmministrazioneSedeCtrl(g);
        System.out.println("Thread controlla allenamento avviato");
        while(running){
            LocalTime currentTime = LocalTime.now();
            if (currentTime.isAfter(LocalTime.MIDNIGHT.minusSeconds(100)) && currentTime.isBefore(LocalTime.MIDNIGHT.plusSeconds(100))) {
                amministrazioneSedeCtrl.verificaAllenamenti();
            }
            try {
                // Attende un minuto prima di controllare di nuovo
                Thread.sleep(60000); // 60000 millisecondi = 1 minuto
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void onstop(){
        System.out.println("Thread controllo stoppato");
        running=false;
    }
}
