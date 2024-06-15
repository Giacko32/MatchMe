package com.prova.matchme.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Campo {
    private int id_campo;
    private int ref_sede;
    private String nomecampo;
    private String sport;
    private LocalDateTime orario;

    public Campo(int id, int sede, String nome, String sport, LocalDateTime ora) {
        this.id_campo = id;
        this.ref_sede = sede;
        this.nomecampo = nome;
        this.sport = sport;
        this.orario = ora;
    }
    public Campo(int id,LocalDateTime ora){
        this.id_campo=id;
        this.orario=ora;
    }

    public int getId_campo() {
        return this.id_campo;
    }

    public String getNomecampo() {
        return this.nomecampo;
    }

    public String getSport() {
        return this.sport;
    }

    public String getOrarioString() {
        return this.orario.toLocalDate().toString() + " " + this.orario.toLocalTime().toString();
    }

    public LocalDateTime getOrario(){
        return this.orario;
    }

    public String toString() {
        return this.nomecampo + " di " + this.sport + " alle ore " + this.orario.toLocalTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campo campo = (Campo) o;
        return Objects.equals(orario, campo.orario);
    }


}
