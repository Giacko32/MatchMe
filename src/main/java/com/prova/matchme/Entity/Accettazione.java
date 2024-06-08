package com.prova.matchme.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Accettazione {
    private int idpartita;
    private int idutente;
    private int n_accettazioni;

    public Accettazione(int idpartita, int idutente, int n_accettazioni) {
        this.idpartita = idpartita;
        this.idutente = idutente;
        this.n_accettazioni = n_accettazioni;
    }

    public int getIdpartita() {
        return idpartita;
    }

    public int getIdutente() {
        return idutente;
    }

    public int getN_accettazioni() {
        return n_accettazioni;
    }

    public static Accettazione createfromdb(ResultSet r) throws SQLException {
        return new Accettazione(
                r.getInt("ref_Partita"),
                r.getInt("ref_Utente"),
                r.getInt("n_Accettazioni")
        );
    }
}
