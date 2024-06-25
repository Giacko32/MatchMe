package com.prova.matchme.Threads;

import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.GestioneSede.Controller.AmministrazioneSedeCtrl;

import java.time.LocalTime;

public class ContrAbb extends Thread{
    private Gestore gestore;
    private AmministrazioneSedeCtrl amministrazioneSedeCtrl;
    private boolean running=true;
    public ContrAbb(Gestore gestore){
        this.gestore=gestore;
    }

    @Override
    public void run(){
        this.amministrazioneSedeCtrl=new AmministrazioneSedeCtrl(gestore);
        System.out.println("Thread avviato");
        while (running) {
            LocalTime currentTime = LocalTime.now();
            if (currentTime.isAfter(LocalTime.MIDNIGHT.minusSeconds(100)) && currentTime.isBefore(LocalTime.MIDNIGHT.plusSeconds(100))) {
                amministrazioneSedeCtrl.checkTime();
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
        System.out.println("Thread stoppato");
        this.running=false;
    }

}
