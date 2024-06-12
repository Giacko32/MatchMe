package com.prova.matchme.Entity;


import java.time.LocalDate;

public class Torneo {

        private int id;
        private int ref_Sede;
        private String sport;
        private int n_Squadre;
        private int n_giocatori_squadra;
        private String vincoli;
        private LocalDate data_inizio;
        private LocalDate data_fine;

        public Torneo(int id, int ref_Sede, String sport, int n_Squadre, int n_giocatori_squadra, String vincoli){
            this.id = id;
            this.ref_Sede = ref_Sede;
            this.sport = sport;
            this.n_Squadre = n_Squadre;
            this.n_giocatori_squadra = n_giocatori_squadra;
            this.vincoli = vincoli;
        }

        public Torneo(int id, int ref_Sede, String sport, int n_Squadre, int n_giocatori_squadra, String vincoli, LocalDate data_inizio, LocalDate data_fine){
            this.id = id;
            this.ref_Sede = ref_Sede;
            this.sport = sport;
            this.n_Squadre = n_Squadre;
            this.n_giocatori_squadra = n_giocatori_squadra;
            this.vincoli = vincoli;
            this.data_inizio = data_inizio;
            this.data_fine = data_fine;
        }

        public int getRef_Sede(){
            return this.ref_Sede;
        }

        public String getSport(){
            return this.sport;
        }

        public int getN_Squadre(){
            return this.n_Squadre;
        }

        public int getN_Giocatori_squadra(){
            return this.n_giocatori_squadra;
        }

        public String getVincoli(){return this.vincoli;}

        public String toString(){
            return "torneo di " + sport + " con " + n_Squadre + " squadre inizio: " + data_inizio + " fine: " + data_fine;
        }


    public int getId() {
        return id;
    }

    public LocalDate getData_fine() {
        return data_fine;
    }

    public LocalDate getData_inizio() {
        return data_inizio;
    }
}
