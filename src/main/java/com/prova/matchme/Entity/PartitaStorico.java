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

    public PartitaStorico(int idpartita,int refpartita,LocalDate data, int idcampo,String risultato,String sport,String nomeCampo){
        this.data=data;
        this.idcampo=idcampo;
        this.idpartita=idpartita;
        this.refpartita=refpartita;
        this.risultato=risultato;
        this.nomeCampo=nomeCampo;
        this.sport=sport;
    }


    public static PartitaStorico createfromdb(ResultSet row) throws SQLException {
        return new PartitaStorico(
                row.getInt("id_PartitaStorico"),
                row.getInt("id_partita_origine"),
                row.getDate("data").toLocalDate(),
                row.getInt("id"),
                row.getString("risultato"),
                row.getString("sport"),
                row.getString("nome")
        );
    }

}
