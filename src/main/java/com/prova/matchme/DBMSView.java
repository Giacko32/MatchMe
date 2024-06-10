package com.prova.matchme;

import com.prova.matchme.Entity.*;
import javafx.util.Pair;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DBMSView {

    private static final String baseUrl = "localhost";

    private static final int port = 3306;

    private static final String user = "root";

    private static final String pass = "Gianvito1@";

    private static Connection connDBMS = null;


    private static void erroreComunicazioneDBMS(Exception e) {
        e.printStackTrace();
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
                listaCampi.add(new Campo(r.getInt("id"), r.getInt("ref_Sede"), r.getString("nome"), r.getString("sport"), LocalDateTime.of(data, r.getTime("orario").toLocalTime())));
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

    public static ArrayList<Utente> querySearchUser(String nome, String cognome, int idutente) {
        String query = "SELECT id, nome, cognome FROM utente u WHERE nome LIKE ? AND cognome LIKE ? AND u.id != ? AND u.id NOT IN (SELECT c.ref_Utente1 FROM chat c WHERE c.ref_Utente2 = ? UNION SELECT c.ref_Utente2 FROM chat c WHERE c.ref_Utente1 = ?)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, "%" + nome + "%");
            stmt.setString(2, "%" + cognome + "%");
            stmt.setString(3, String.valueOf(idutente));
            stmt.setString(4, String.valueOf(idutente));
            stmt.setString(5, String.valueOf(idutente));
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

    public static void queryNewMessage(Messaggio m, int idchat) {
        String query = "INSERT INTO messaggi(ref_Utente,ref_Chat,messaggio) values(?,?,?)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(m.getIdmittente()));
            stmt.setString(2, String.valueOf(idchat));
            stmt.setString(3, m.getMessaggio());
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    public static void queryCreaPartita(Partita partita, Utente utente) {
        String query = "INSERT INTO partita(ref_Campo,dataOra,tipo,vincoli) values(?,?,?,?)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, partita.getRef_campo());
            stmt.setTimestamp(2, Timestamp.valueOf(partita.getDataOra()));
            stmt.setString(3, partita.getTipo());
            stmt.setString(4, partita.getVincoli());
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

        String queryGetID = "SELECT id FROM partita WHERE ref_Campo = ? AND dataOra = ?";
        int idNewPartita = 0;
        try (PreparedStatement stmt = connDBMS.prepareStatement(queryGetID)) {
            stmt.setInt(1, partita.getRef_campo());
            stmt.setTimestamp(2, Timestamp.valueOf(partita.getDataOra()));
            var r = stmt.executeQuery();
            if (r.next()) {
                idNewPartita = r.getInt(1);
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

        String queryInsertCreatore = "INSERT INTO partecipa(ref_Utente,ref_Partita,n_squadra,n_giocatori_allenamento) values(?,?,?,?)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(queryInsertCreatore)) {
            stmt.setInt(1, utente.getId());
            stmt.setInt(2, idNewPartita);
            stmt.setInt(3, 1);
            stmt.setInt(4, 0);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }


    public static ArrayList<Notifica> queryGetNotifiche(int idutente) {
        String query = "SELECT * FROM notifica WHERE ref_Utente=?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, idutente);
            var r = stmt.executeQuery();
            ArrayList<Notifica> listanotifiche = new ArrayList<>();
            if (r.next()) {
                Notifica n = new Notifica(r.getInt("id"), r.getInt("ref_Utente"), r.getString("messaggio"), r.getInt("tipo"));
                listanotifiche.add(n);
            }
            return listanotifiche;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static Partita queryGetDetailsPartita(int idPartita) {
        String query = "SELECT * FROM partita WHERE id=? ";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, idPartita);
            var r = stmt.executeQuery();
            if (r.next()) {
                return new Partita(r.getInt("id"), r.getInt("ref_Campo"), r.getDate("dataOra").toLocalDate().atStartOfDay(), r.getString("tipo"), r.getString("vincoli"));
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static ArrayList<UtentePart> querygetpartecipanti(int idPartita) {
        String query = "SELECT u.*, pt.n_squadra FROM utente u JOIN partecipa pt ON u.id = pt.ref_Utente WHERE pt.ref_Partita = ? ";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, idPartita);
            var r = stmt.executeQuery();
            ArrayList<UtentePart> listautenti = new ArrayList<>();
            if (r.next()) {
                listautenti.add(UtentePart.createFromDB(r));
            }
            return listautenti;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static boolean queryGiocatoreOccupato(int idutente, LocalDateTime dataora) {
        String query = "SELECT p.id FROM partita p JOIN partecipa pt ON p.id = pt.ref_Partita WHERE pt.ref_Utente = ? AND p.dataOra = ? LIMIT 1";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, idutente);
            stmt.setDate(2, Date.valueOf(dataora.toLocalDate()));
            var r = stmt.executeQuery();
            if (r.next()) {
                return false;
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return true;
    }

    public static ArrayList<Partita> queryGetPartiteUtente(Utente utente) {
        String query = "SELECT id, ref_Campo, dataOra, tipo, vincoli FROM partita pt, partecipa pa WHERE pt.id = pa.ref_Partita AND pa.ref_Utente = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, utente.getId());
            var r = stmt.executeQuery();
            ArrayList<Partita> partiteUtente = new ArrayList<Partita>();
            while (r.next()) {
                partiteUtente.add(new Partita(r.getInt(1), r.getInt(2), r.getTimestamp(3).toLocalDateTime(), r.getString(4), r.getString(5)));
            }
            return partiteUtente;

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static void queryAddGiocatore(int idpartita, int idutente, int squadra) {
        String query = "INSERT INTO partecipa(ref_Utente,ref_Partita,n_squadra,n_giocatori_allenamento) values (?,?,?,?)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, idutente);
            stmt.setInt(2, idpartita);
            stmt.setInt(3, squadra);
            stmt.setInt(4, 0);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    public static void sendNotify(String notifica, ArrayList<UtentePart> listadest) {
        String query = "INSERT INTO notifica(ref_Utente,tipo,messaggio) values (?,?,?)";
        for (int i = 0; i < listadest.size(); i++) {
            try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
                stmt.setInt(1, listadest.get(i).getId());
                stmt.setInt(2, 1);
                stmt.setString(3, notifica);
                var r = stmt.executeUpdate();
            } catch (SQLException e) {
                erroreComunicazioneDBMS(e);
            }
        }
    }

    public static void eliminaNotifica(Notifica notifica) {
        String query = "DELETE FROM notifica WHERE id=?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, notifica.getId());
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    public static Accettazione queryGetDettagliPartecipantePartita(int idpartita, int idutente) {
        String query = "SELECT * FROM accettazione WHERE ref_Utente=? and ref_Partita=?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, idutente);
            stmt.setInt(2, idpartita);
            var r = stmt.executeQuery();
            while (r.next()) {
                return Accettazione.createfromdb(r);
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static Utente querygetDettagliUtente(int id) {
        String query = "SELECT * FROM utente WHERE id=? ";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, id);
            var r = stmt.executeQuery();
            while (r.next()) {
                return Utente.createFromDB(r);
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static void setAccettazione(Accettazione accettazione) {
        String query = "UPDATE accettazione SET n_Accettazioni=? WHERE ref_Partita=? and ref_Utente=?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, accettazione.getN_accettazioni() - 1);
            stmt.setInt(2, accettazione.getIdpartita());
            stmt.setInt(3, accettazione.getIdutente());
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

    }

    public static ArrayList<Torneo> queryGetImieiTornei(Utente utente) {
        String query = "SELECT id, ref_Sede, sport, n_Squadre, n_giocatori_squadra,vincoli FROM Iscrizione, Torneo WHERE Iscrizione.ref_Torneo = Torneo.id AND Iscrizione.ref_Utente = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, utente.getId());
            var r = stmt.executeQuery();
            ArrayList<Torneo> torneiUtente = new ArrayList<Torneo>();
            while (r.next()) {
                torneiUtente.add(new Torneo(r.getInt(1), r.getInt(2), r.getString(3), r.getInt(4), r.getInt(5), r.getString(6)));
            }
            return torneiUtente;

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static ArrayList<Torneo> queryGetTuttITornei() {
        String query = "SELECT id, ref_Sede, sport, n_Squadre, n_giocatori_squadra,vincoli FROM Torneo";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            var r = stmt.executeQuery();
            ArrayList<Torneo> tuttiTorneiUtente = new ArrayList<Torneo>();
            while (r.next()) {
                tuttiTorneiUtente.add(new Torneo(r.getInt(1), r.getInt(2), r.getString(3), r.getInt(4), r.getInt(5), r.getString(6)));
            }
            return tuttiTorneiUtente;

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static Torneo queryGetTorneo(Torneo torneo) {
        String query = "SELECT id, ref_Sede, sport, n_Squadre, n_giocatori_squadra,vincoli FROM Torneo WHERE id = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, torneo.getId());
            var r = stmt.executeQuery();
            if (r.next()) {
                Torneo torneoRisultato = new Torneo(r.getInt(1), r.getInt(2), r.getString(3), r.getInt(4), r.getInt(5), r.getString(6));
                return torneoRisultato;
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static PartitaDetails queryGetCampoSedePartita(Partita partita) {
        String query = "SELECT s.Id_Sede, s.Indirizzo, s.Nome_Sede, c.id, c.nome, c.sport FROM partita p, campo c, sede s WHERE p.ref_Campo = c.id AND c.ref_Sede = s.Id_Sede AND p.id = ?";
        PartitaDetails partitaDetails = new PartitaDetails();
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, partita.getId());
            var r = stmt.executeQuery();
            if (r.next()) {
                partitaDetails.setSede(new Sede(r.getInt(1), r.getString(3), r.getString(2)));
                partitaDetails.setCampo(new Campo(r.getInt(4), r.getInt(1), r.getString(5), r.getString(6), partita.getDataOra()));
                partitaDetails.setPartita(partita);
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

        String getSquadra1 = "SELECT username FROM utente, partecipa WHERE ref_Partita = ? AND ref_Utente = id AND n_squadra = 1";
        try (PreparedStatement stmt = connDBMS.prepareStatement(getSquadra1)) {
            stmt.setInt(1, partita.getId());
            var r = stmt.executeQuery();
            ArrayList<Utente> squadra1 = new ArrayList<Utente>();
            while (r.next()) {
                squadra1.add(new Utente(r.getString(1)));
            }
            partitaDetails.setSquadra1(squadra1);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

        String getSquadra2 = "SELECT username FROM utente, partecipa WHERE ref_Partita = ? AND ref_Utente = id AND n_squadra = 2";
        try (PreparedStatement stmt = connDBMS.prepareStatement(getSquadra2)) {
            stmt.setInt(1, partita.getId());
            var r = stmt.executeQuery();
            ArrayList<Utente> squadra2 = new ArrayList<Utente>();
            while (r.next()) {
                squadra2.add(new Utente(r.getString(1)));
            }
            partitaDetails.setSquadra2(squadra2);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return partitaDetails;
    }

    public static ArrayList<Utente> queryGetUtenti(String parametri) {
        String query = "SELECT id,nome,cognome FROM utente WHERE (nome LIKE ? or cognome LIKE ?) AND (tipo<>?)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, "%" + parametri + "%");
            stmt.setString(2, "%" + parametri + "%");
            stmt.setString(3, "al");
            var r = stmt.executeQuery();
            ArrayList<Utente> listautenti = new ArrayList<>();
            while (r.next()) {
                listautenti.add(new Utente(r.getInt("id"), r.getString("nome"), r.getString("cognome")));
            }
            return listautenti;

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;

    }

    public static void queryAttivaAllenatore(int id) {
        String query = "UPDATE utente SET tipo=? WHERE id=?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, "al");
            stmt.setInt(2, id);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    public static ArrayList<Utente> querySearchNonTesserati(String parametri) {
        String query = "SELECT id,nome,cognome,email FROM utente WHERE tipo=? AND (nome LIKE ? or cognome LIKE ?)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, "nt");
            stmt.setString(2, "%" + parametri + "%");
            stmt.setString(3, "%" + parametri + "%");
            var r = stmt.executeQuery();
            ArrayList<Utente> listanontesserati = new ArrayList<>();
            while (r.next()) {
                System.out.println(new Utente(r.getInt("id"), r.getString("nome"), r.getString("cognome")));
                listanontesserati.add(new Utente(r.getInt("id"), r.getString("nome"), r.getString("cognome"),r.getString("email")));
            }
            return listanontesserati;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static void querySetAbbonamento(String codice,LocalDate data,int idutente){
        String query="INSERT INTO abbonamento(codice,data_Scadenza,ref_Tesserato) values(?,?,?)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, codice);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setInt(3, idutente);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

}
