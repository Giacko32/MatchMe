package com.prova.matchme.Entity;

public class Sede {
    private int id_sede;
    private String nome_sede;
    private String indirizzo;

    public Sede(int Id, String nome, String ind) {
        this.id_sede = Id;
        this.nome_sede = nome;
        this.indirizzo = ind;
    }
    public Sede(int Id) {
        this.id_sede = Id;
    }

    public int getId_sede() {
        return this.id_sede;
    }

    public String getNome_sede() {
        return this.nome_sede;
    }

    public String getIndirizzo() {
        return this.indirizzo;
    }

    public String toString() {
        return this.nome_sede + " in " + this.indirizzo;
    }


}
