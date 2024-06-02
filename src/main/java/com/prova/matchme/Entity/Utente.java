package com.prova.matchme.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Utente {
    private int id;
    private String nome;
    private String cognome;
    private String email;
    private String username;
    private String sesso;
    private String tipo;
    private String password;
    private float livello;
    private int eta;

    public Utente(int id, String nome, String cognome, String email, String username, String tipo, String password, float livello, int eta,String sesso) {
        this.id=id;
        this.cognome=cognome;
        this.password=password;
        this.username=username;
        this.eta=eta;
        this.tipo=tipo;
        this.email=email;
        this.nome=nome;
        this.livello=livello;
        this.sesso=sesso;
    }

    public String toString(){
        return this.nome + " " + this.cognome;
    }

    public String getTipo(){
        return this.tipo;
    }

    public static Utente createFromDB(ResultSet row) throws SQLException {
        return new Utente(
                row.getInt("id"),
                row.getString("nome"),
                row.getString("cognome"),
                row.getString("email"),
                row.getString("username"),
                row.getString("tipo"),
                row.getString("passwordUtente"),
                row.getFloat("livello"),
                row.getInt("eta"),
                row.getString("sesso"));


    }

}
