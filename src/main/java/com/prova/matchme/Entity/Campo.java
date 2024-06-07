package com.prova.matchme.Entity;

import java.sql.Time;
import java.time.LocalDate;

public class Campo {
    private int id_campo;
    private int ref_sede;
    private String nomecampo;
    private String sport;
    private Time orario;

    public Campo(int id, int sede, String nome, String sport, Time ora){
        this.id_campo = id;
        this.ref_sede = sede;
        this.nomecampo = nome;
        this.sport = sport;
        this.orario = ora;
    }

    public String toString(){
        return this.nomecampo + " di " + this.sport + " alle ore " + this.orario;
    }
}
