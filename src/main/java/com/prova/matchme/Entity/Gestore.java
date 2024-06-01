package com.prova.matchme.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Gestore {
    private int id;
    private int sede;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String username;

    public Gestore(int id,int sede,String nome,String cognome,String email,String password,String username){
        this.id=id;
        this.nome=nome;
        this.sede=sede;
        this.cognome=cognome;
        this.email=email;
        this.password=password;
        this.username=username;
    }

    public static Gestore createFromDB(ResultSet row) throws SQLException {
        return new Gestore(
                row.getInt("id"),
                row.getInt("sede"),
                row.getString("nome"),
                row.getString("cognome"),
                row.getString("email"),
                row.getString("passwordGestore"),
                row.getString("username"));

    }

}
