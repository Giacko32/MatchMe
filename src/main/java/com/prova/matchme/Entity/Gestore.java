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

    public void setdati(String nome,String cognome,String email,String username){
        this.username=username;
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
    }

    public int getId(){
        return this.id;
    }
    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password=password;
    }

    public String toString(){
        return this.nome + " " + this.cognome;
    }

    public String getNome(){
        return this.nome;
    }
    public String getCognome(){
        return this.cognome;
    }
    public String getUsername(){
        return this.username;
    }
    public String getEmail(){
        return this.email;
    }

    public static Gestore createFromDB(ResultSet row) throws SQLException {
        return new Gestore(
                row.getInt("Id_gestore"),
                row.getInt("Ref_sede"),
                row.getString("Nome"),
                row.getString("Cognome"),
                row.getString("Email"),
                row.getString("passwordGestore"),
                row.getString("Username"));

    }

}
