package com.prova.matchme.Threads;

import com.prova.matchme.GestioneFidelizzazione.FidelizzazioneControl;

import java.time.LocalTime;

public class FidTess extends Thread{
    private boolean running=true;
    private FidelizzazioneControl fidelizzazioneControl;

    @Override
    public void run() {
        running=true;
        fidelizzazioneControl=new FidelizzazioneControl();
        System.out.println("Thread di fidelizzazione avviato");
        while(running){
            LocalTime currentTime = LocalTime.now();
            //if (currentTime.isAfter(LocalTime.MIDNIGHT.minusSeconds(100)) && currentTime.isBefore(LocalTime.MIDNIGHT.plusSeconds(100))) {
               fidelizzazioneControl.fideltess();
               fidelizzazioneControl.recupera();
           // }
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
