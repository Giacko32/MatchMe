package com.prova.matchme;

import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.Entity.Utente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class DBMSView {

    private static final String baseUrl = "localhost";

    private static final int port = 3306;

    private static final String user = "root";

    private static final String pass = "Rtx4060ticx!";

    private static Connection connDBMS = null;


    private static void erroreComunicazioneDBMS(Exception e) {
        //Main.log.error("Errore durante comunicazione con DBMS", e);
        Utils.creaPannelloErrore("C'è stato un problema durante la comunicazione con la base di dati, riprova");

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

    public static String queryControllaCredenziali(String username, String password) {
        var query = "SELECT u.* FROM utente u WHERE username = ? and passwordUtente = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            var r = stmt.executeQuery();
            if (r.next()) {
                return "ut";
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        var query2 = "SELECT g.* FROM gestore g WHERE Username = ? and passwordGestore = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query2)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            var r = stmt.executeQuery();
            if (r.next()) {
                return "g";
            }

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return "Errore";
    }

    public static boolean queryDBMSCheckUsernameandMail(String username, String mail) {
        var query = "SELECT * FROM utente WHERE username = ? or email = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, mail);
            var r = stmt.executeQuery();
            if (r.next()) {
                return false;
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return true;
    }
    public static Utente queryControllaCredenzialiUtente(String username, String password) {
        var query = "SELECT u.* FROM utente u WHERE username = ? and passwordUtente = ?";
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

    public static Gestore queryControllaCredenzialiGest(String username, String password) {
        var query = "SELECT g.* FROM gestore g WHERE Username = ? and passwordGestore = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            var r = stmt.executeQuery();
            if (r.next()) {
                return Gestore.createFromDB(r);
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static boolean registraUtente(String nome, String cognome, String email, String username, int eta, String password, String tipo, char sesso, float livello) {
        String query = "INSERT INTO utente(nome, cognome, email, username, eta, passwordUtente, tipo, sesso, livello) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, nome);
            stmt.setString(2, cognome);
            stmt.setString(3, email);
            stmt.setString(4, username);
            stmt.setInt(5, eta);
            stmt.setString(6, password);
            stmt.setString(7, tipo);
            stmt.setString(8, Character.toString(sesso));
            stmt.setFloat(9, livello);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
            return false;
        }
        return true;
    }


    public static boolean queryDBMSCheckMail(String mail) {
        var query = "SELECT * FROM utente WHERE email = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, mail);
            var r = stmt.executeQuery();
            if (r.next()) {
                return true;
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    public static void queryDBMSChangePassword(String mail, String password) {
        var query = "UPDATE utente SET passwordUtente=? WHERE email = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, password);
            stmt.setString(2, mail);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

    }


}
