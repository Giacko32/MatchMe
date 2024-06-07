package com.prova.matchme.Entity;

public class Messaggio {
    private int idmittente;
    private String messaggio;

    public Messaggio(int idmittente,String messaggio){
        this.messaggio=messaggio;
        this.idmittente=idmittente;
    }


    public int getIdmittente(){
        return this.idmittente;
    }

    public String getMessaggio(){
        return this.messaggio;
    }
}
