package com.prova.matchme;

import com.prova.matchme.Entity.Utente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBMSView {

    private static final String baseUrl = "localhost";

    private static final int port = 3306;

    private static final String user = "root";

    private static final String pass = "Gianvito1@";

    private static Connection connDBMS = null;


    private static void erroreComunicazioneDBMS(Exception e) {
        //Main.log.error("Errore durante comunicazione con DBMS", e);
        //Utils.creaPannelloErrore("C'è stato un problema durante la comunicazione con la base di dati, riprova");
        e.printStackTrace();
    }

    /**
     * @hidden
     */
    private static String buildConnectionUrl(String dbName) {
        return "jdbc:mysql://" + baseUrl + ":" + port + "/" + dbName + "?user=" + user + "&password=" + pass;
    }


    public static void connectDBMS() {
        try {
            if (connDBMS == null) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                DBMSView.connDBMS = DriverManager.getConnection(buildConnectionUrl("matchmeDB"));
                System.out.println("IDDU E'");
            }
        } catch (java.sql.SQLException e) {
            erroreComunicazioneDBMS(e);

        }
    }



    public static Utente queryControllaCredenziali(String username, String password) {
        var query = "SELECT u.* FROM utente u WHERE username = ? and password = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            var r = stmt.executeQuery();
            if (r.next()) {
                return Utente.createFromDB(r);
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }
}
