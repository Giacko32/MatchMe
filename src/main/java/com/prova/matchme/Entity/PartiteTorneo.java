package com.prova.matchme.Entity;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class PartiteTorneo{
    private int id1;
    private int id2;
    private Campo campo;
    private String nomeSquadra1;
    private String nomeSquadra2;
    private LocalDateTime data;

    public PartiteTorneo() {}
    public PartiteTorneo(int id1, int id2, String nomeSquadra1, String nomeSquadra2) {
        this.id1 = id1;
        this.id2 = id2;
        this.nomeSquadra1 = nomeSquadra1;
        this.nomeSquadra2 = nomeSquadra2;
    }

    public PartiteTorneo(int id1, int id2, String nomeSquadra1, String nomeSquadra2, LocalDateTime data) {
        this.id1 = id1;
        this.id2 = id2;
        this.nomeSquadra1 = nomeSquadra1;
        this.nomeSquadra2 = nomeSquadra2;
        this.data = data;
    }


    public void setCampo(Campo campo) {
        this.campo = campo;
    }

    public String getNomeSquadra2() {
        return nomeSquadra2;
    }

    public Campo getCampo() {
        return campo;
    }

    public int getId1() {
        return id1;
    }

    public int getId2() {
        return id2;
    }

    public String getNomeSquadra1() {
        return nomeSquadra1;
    }

    public void setNomeSquadra1(String nomeSquadra1) {
        this.nomeSquadra1 = nomeSquadra1;
    }
    public void setNomeSquadra2(String nomeSquadra2) {
        this.nomeSquadra2 = nomeSquadra2;
    }

    public String toString(){
        return nomeSquadra1+ " VS "+ nomeSquadra2 + " orario: " + data.toLocalDate().toString() + " " + data.toLocalTime().toString();
    }

}
