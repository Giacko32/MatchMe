package com.prova.matchme.Entity;

import java.sql.ResultSet;

public class Chat {
    private int idchat;
    private int id_other;
    private String utente;

    public Chat(int idchat,int id_other,String utente){
        this.id_other=id_other;
        this.utente=utente;
        this.idchat=idchat;
    }

    public int getId(){
        return this.idchat;
    }
    public int getId_other(){
        return this.id_other;
    }
    public String toString(){
        return this.utente;
    }
}
