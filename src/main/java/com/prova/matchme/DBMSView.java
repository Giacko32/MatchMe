package com.prova.matchme;

import com.prova.matchme.Entity.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DBMSView {

    private static final String baseUrl = "localhost";

    private static final int port = 3306;

    private static final String user = "root";

    private static final String pass = "Gianvito1@";

    private static Connection connDBMS = null;


    private static void erroreComunicazioneDBMS(Exception e) {
        //Main.log.error("Errore durante comunicazione con DBMS", e);
        Utils.creaPannelloErrore("C'è stato un problema \ndurante la comunicazione \ncon la base di dati, riprova");

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
                System.out.println("Connessione effettuata");
            }
        } catch (SQLException e) {
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

    public static void queryDBMSUpdateData(Utente u, Gestore g) {
        if (u != null) {
            var query = "UPDATE utente SET username=?,nome=?,cognome=?,email=? WHERE id=?";
            try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
                stmt.setString(1, u.getUsername());
                stmt.setString(2, u.getNome());
                stmt.setString(3, u.getCognome());
                stmt.setString(4, u.getEmail());
                stmt.setString(5, String.valueOf(u.getId()));
                var r = stmt.executeUpdate();
            } catch (SQLException e) {
                erroreComunicazioneDBMS(e);
            }
        }
        if (g != null) {
            var query = "UPDATE gestore SET Username=?,Nome=?,Cognome=?,Email=? WHERE Id_Gestore=?";
            try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
                stmt.setString(1, g.getUsername());
                stmt.setString(2, g.getNome());
                stmt.setString(3, g.getCognome());
                stmt.setString(4, g.getEmail());
                stmt.setString(5, String.valueOf(g.getId()));
                var r = stmt.executeUpdate();
            } catch (SQLException e) {
                erroreComunicazioneDBMS(e);
            }
        }
    }

    public static void queryDBMSUpdatePassword(Utente u, Gestore g, String password) {
        if (u != null) {
            var query = "UPDATE utente SET passwordUtente=? WHERE id=?";
            try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
                stmt.setString(1, password);
                stmt.setString(2, String.valueOf(u.getId()));
                var r = stmt.executeUpdate();
            } catch (SQLException e) {
                erroreComunicazioneDBMS(e);
            }
        }
        if (g != null) {
            var query = "UPDATE gestore SET passwordGestore=? WHERE Id_Gestore=?";
            try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
                stmt.setString(1, password);
                stmt.setString(2, String.valueOf(g.getId()));
                var r = stmt.executeUpdate();
            } catch (SQLException e) {
                erroreComunicazioneDBMS(e);
            }
        }
    }


    public static int queryGetIdCode(String codice) {
        var query = "SELECT ref_Tesserato FROM abbonamento WHERE id= ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, codice);
            var r = stmt.executeQuery();
            if (r.next()) {
                return r.getInt("ref_Tesserato");
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return 0;
    }

    public static void querySetAbbonamento(int id) {
        var query = "UPDATE utente SET tipo=? WHERE id=?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, "t");
            stmt.setString(2, String.valueOf(id));
            var r = stmt.executeUpdate();

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

    }

    public static ArrayList<Sede> queryGetSedi() {
        var query = "SELECT Id_Sede, Nome_Sede, Indirizzo FROM sede";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            var r = stmt.executeQuery();
            ArrayList<Sede> listaSedi = new ArrayList<Sede>();
            while (r.next()) {
                listaSedi.add(new Sede(r.getInt(1), r.getString(2), r.getString(3)));
            }
            return listaSedi;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static ArrayList<PartitaStorico> queryPartiteGiocate(int id) {
        var query = "SELECT pg.id_PartitaStorico, pg.id_partita_origine, pg.risultato, pg.data, c.id, c.nome, c.sport FROM partitestorico AS pg JOIN partecipa ON pg.id_PartitaStorico = partecipa.ref_Partita JOIN partita AS p ON pg.id_partita_origine = p.id JOIN campo AS c ON p.ref_Campo = c.id WHERE partecipa.ref_Utente = ? ";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(id));
            var r = stmt.executeQuery();
            ArrayList<PartitaStorico> listaPartite = new ArrayList<>();
            while (r.next()) {
                PartitaStorico p = PartitaStorico.createfromdb(r);
                listaPartite.add(p);
            }
            return listaPartite;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static ArrayList<Campo> queryGetCampiLiberi(Sede sede, String sport, LocalDate data) {
        var query = "SELECT * FROM campo cp, orari o WHERE cp.ref_Sede = ? and cp.sport = ? and orario not in (SELECT TIME(p.dataOra) as orario FROM partita p, campo c WHERE DATE(p.dataOra) = ? and p.ref_Campo = c.id and c.id = cp.id) ORDER BY cp.id";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, sede.getId_sede());
            stmt.setString(2, sport);
            stmt.setDate(3, Date.valueOf(data));
            var r = stmt.executeQuery();
            ArrayList<Campo> listaCampi = new ArrayList<Campo>();
            while (r.next()) {
                listaCampi.add(new Campo(r.getInt("id"), r.getInt("ref_Sede"), r.getString("nome"), r.getString("sport"), r.getTime("orario")));
                System.out.println(listaCampi.getLast().toString());
            }
            return listaCampi;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static ArrayList<Chat> queryGetChat(int id) {
        String query = "SELECT c.id, " +
                "CASE WHEN c.ref_Utente1 = ? THEN u2.id ELSE u1.id END AS altro_id, " +
                "CASE WHEN c.ref_Utente1 = ? THEN u2.nome ELSE u1.nome END AS nome, " +
                "CASE WHEN c.ref_Utente1 = ? THEN u2.cognome ELSE u1.cognome END AS cognome " +
                "FROM chat c " +
                "JOIN utente u1 ON c.ref_Utente1 = u1.id " +
                "JOIN utente u2 ON c.ref_Utente2 = u2.id " +
                "WHERE c.ref_Utente1 = ? OR c.ref_Utente2 = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(id));
            stmt.setString(2, String.valueOf(id));
            stmt.setString(3, String.valueOf(id));
            stmt.setString(4, String.valueOf(id));
            stmt.setString(5, String.valueOf(id));
            var r = stmt.executeQuery();
            ArrayList<Chat> listaChat = new ArrayList<>();
            while (r.next()) {
                Chat c = new Chat(r.getInt("id"), r.getInt("altro_id"), r.getString("nome") + " " + r.getString("cognome"));
                listaChat.add(c);
            }
            return listaChat;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static ArrayList<Messaggio> getdetailChat(Chat chat) {
        String query = "SELECT * FROM messaggi WHERE ref_Chat=?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(chat.getId()));
            var r = stmt.executeQuery();
            ArrayList<Messaggio> listamessaggi = new ArrayList<>();
            while (r.next()) {
                Messaggio m = new Messaggio(r.getInt("ref_Utente"), r.getString("messaggio"));
                listamessaggi.add(m);
            }
            return listamessaggi;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;


    }

    public static ArrayList<Utente> querySearchUser(String nome, String cognome) {
        String query = "SELECT id, nome, cognome FROM utente WHERE nome LIKE ? AND cognome LIKE ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, "%" + nome + "%");
            stmt.setString(2, "%" + cognome + "%");
            var r = stmt.executeQuery();
            ArrayList<Utente> listaUtenti = new ArrayList<>();
            while (r.next()) {
                Utente u = new Utente(r.getInt("id"), r.getString("nome"), r.getString("cognome"));
                listaUtenti.add(u);
            }
            return listaUtenti;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static void queryCreatenewChat(int idutente1, int idutente2) {
        String query = "INSERT INTO chat(ref_Utente1,ref_Utente2) values(?,?)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(idutente1));
            stmt.setString(2, String.valueOf(idutente2));
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    public static Sede queryGetDetailsCampo(Campo campo) {
        String query = "SELECT Id_Sede, Indirizzo, Nome_Sede FROM sede, campo WHERE Id_Sede = ref_Sede and id = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, campo.getId_campo());
            var r = stmt.executeQuery();
            Sede sede = null;
            if (r.next()) {
                sede = new Sede(r.getInt(1), r.getString(3), r.getString(2));
            }
            return sede;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }


}
