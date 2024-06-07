package com.prova.matchme.Entity;

public class Sede {
    private int id_sede;
    private String nome_sede;
    private String indirizzo;

    public Sede(int Id, String nome, String ind){
        this.id_sede = Id;
        this.nome_sede = nome;
        this.indirizzo = ind;
    }

    public String toString(){
        return this.nome_sede + " in " + this.indirizzo;
    }


}
