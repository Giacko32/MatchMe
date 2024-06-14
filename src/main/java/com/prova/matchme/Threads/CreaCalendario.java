package com.prova.matchme.Threads;

import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.GestioneTornei.Controller.AmministrazioneTorneiCtrl;

import java.time.LocalTime;

public class CreaCalendario extends Thread {

    private Gestore gestore;
    private boolean running=true;

    public CreaCalendario(Gestore gestore) {
        this.gestore = gestore;
    }

    public void run(){
        AmministrazioneTorneiCtrl amminCtrl = new AmministrazioneTorneiCtrl(gestore);
        System.out.println("Crea calendario avviato");
        while(running){
            LocalTime currentTime = LocalTime.now();
            /*if (currentTime.isAfter(LocalTime.MIDNIGHT.minusSeconds(100)) && currentTime.isBefore(LocalTime.MIDNIGHT.plusSeconds(100))) {*/
              amminCtrl.CreaCalendario();
            //}
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
