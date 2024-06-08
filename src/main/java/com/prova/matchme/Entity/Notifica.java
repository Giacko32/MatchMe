package com.prova.matchme.Entity;

public class Notifica {
    private int id;
    private int ref_utente;
    private String messaggio;
    private int tipo;

    public Notifica(int id,int ref_utente,String messaggio,int tipo){
        this.id=id;
        this.ref_utente=ref_utente;
        this.tipo=tipo;
        this.messaggio=messaggio;
    }

    public String toString(){
        return this.messaggio;
    }

    public int getId() {
        return id;
    }

    public int getTipo(){
        return this.tipo;
    }


}
