package com.prova.matchme.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UtentePart extends Utente{
    private int squadra;
    public UtentePart(int id, String nome, String cognome, String email, String username, String tipo, String password, float livello, int eta,String sesso,int squadra){
        super(id, nome, cognome, email, username, tipo, password, livello, eta, sesso);
        this.squadra=squadra;
    }
    public UtentePart(Utente u,int squadra){
        super(u.getId(), u.getNome(), u.getCognome(), u.getEmail(), u.getUsername(),u.getTipo(), u.getPassword(), u.getLivello(),1,"");
        this.squadra=squadra;
    }

    public int getSquadra() {
        return squadra;
    }
    public static UtentePart createFromDB(ResultSet row) throws SQLException {
        return new UtentePart(
                row.getInt("id"),
                row.getString("nome"),
                row.getString("cognome"),
                row.getString("email"),
                row.getString("username"),
                row.getString("tipo"),
                row.getString("passwordUtente"),
                row.getFloat("livello"),
                row.getInt("eta"),
                row.getString("sesso"),
                row.getInt("n_squadra"));

    }
}
