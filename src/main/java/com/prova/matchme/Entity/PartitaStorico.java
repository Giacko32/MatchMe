package com.prova.matchme.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PartitaStorico {
    private int idpartita;
    private int refpartita;
    private LocalDate data;
    private int idcampo;
    private String risultato;
    private String sport;
    private String nomeCampo;
    private String tipo;

    public PartitaStorico(int idpartita, int refpartita, LocalDate data, int idcampo, String risultato, String sport, String nomeCampo, String tipo) {
        this.data = data;
        this.idcampo = idcampo;
        this.idpartita = idpartita;
        this.refpartita = refpartita;
        this.risultato = risultato;
        this.nomeCampo = nomeCampo;
        this.sport = sport;
        this.tipo = tipo;
    }

    public String toString() {
        return "";
    }

    public String torna() {
        if (this.tipo.equals("all")) {
            return "Id Allenamento:" + this.idpartita + " Data:" + this.data + " Id Campo:" + this.idcampo + " Squadra vincitrice: " + this.risultato + " Sport:" + this.sport + " Nome del Campo:" + this.nomeCampo;
        }else{
            return "Id Partita:" + this.idpartita + " Data:" + this.data + " Id Campo:" + this.idcampo + " Squadra vincitrice: " + this.risultato + " Sport:" + this.sport + " Nome del Campo:" + this.nomeCampo;
        }
    }

    public static PartitaStorico createfromdb(ResultSet row) throws SQLException {
        return new PartitaStorico(
                row.getInt("id_PartitaStorico"),
                row.getInt("id_partita_origine"),
                row.getDate("data").toLocalDate(),
                row.getInt("id"),
                row.getString("risultato"),
                row.getString("sport"),
                row.getString("nome"),
                row.getString("tipo")
        );
    }

}
