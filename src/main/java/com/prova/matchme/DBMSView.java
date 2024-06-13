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

    private static final String pass = "Gioele2002!";

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
        query = "SELECT * FROM gestore WHERE Email=?";
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
        var query = "SELECT ref_Tesserato FROM abbonamento WHERE codice= ?";
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
        var query = "SELECT pg.id_PartitaStorico, pg.id_partita_origine, pg.risultato, pg.data, c.id, c.nome, c.sport,p.tipo FROM partitestorico AS pg JOIN partecipa ON pg.id_PartitaStorico = partecipa.ref_Partita JOIN partita AS p ON pg.id_partita_origine = p.id JOIN campo AS c ON p.ref_Campo = c.id WHERE partecipa.ref_Utente = ? ";
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
        if (utente != null) {
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
    }

    public static ArrayList<Notifica> queryGetNotifiche(int idutente) {
        String query = "SELECT * FROM notifica WHERE ref_Utente=?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, idutente);
            var r = stmt.executeQuery();
            ArrayList<Notifica> listanotifiche = new ArrayList<>();
            while (r.next()) {
                listanotifiche.add(new Notifica(r.getInt("id"), r.getInt("ref_Utente"), r.getString("messaggio"), r.getInt("tipo")));
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
        String query = "SELECT p.id FROM partita p, partecipa pt WHERE p.id = pt.ref_Partita AND pt.ref_Utente = ? AND p.dataOra = ? LIMIT 1";
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
        String query = "SELECT id, ref_Campo, dataOra, tipo, vincoli FROM partita pt, partecipa pa WHERE pt.id = pa.ref_Partita AND pt.tipo <> \"all\" AND pa.ref_Utente = ? AND pa.ref_Partita NOT IN (SELECT id_partita_origine FROM partitestorico)";
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

    public static void sendNotify(String notifica, ArrayList<UtentePart> listadest, int tipo) {
        String query = "INSERT INTO notifica(ref_Utente,tipo,messaggio) values (?,?,?)";
        for (int i = 0; i < listadest.size(); i++) {
            try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
                stmt.setInt(1, listadest.get(i).getId());
                stmt.setInt(2, tipo);
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
        String query = "SELECT id, ref_Sede, sport, n_Squadre, n_giocatori_squadra,vincoli, data_inizio, data_fine FROM Iscrizione, Torneo WHERE Iscrizione.ref_Torneo = Torneo.id AND Iscrizione.ref_Utente = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, utente.getId());
            var r = stmt.executeQuery();
            ArrayList<Torneo> torneiUtente = new ArrayList<Torneo>();
            while (r.next()) {
                torneiUtente.add(new Torneo(r.getInt(1), r.getInt(2), r.getString(3), r.getInt(4), r.getInt(5), r.getString(6), r.getDate(7).toLocalDate(), r.getDate(8).toLocalDate()));
            }
            return torneiUtente;

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static ArrayList<Torneo> queryGetTuttITornei(int idUtente) {
        String query = "SELECT id, ref_Sede, sport, n_Squadre, n_giocatori_squadra,vincoli, data_inizio, data_fine FROM Torneo  WHERE id not in (select distinct ref_Torneo from iscrizione where ref_Utente = ?) ";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, idUtente);
            var r = stmt.executeQuery();
            ArrayList<Torneo> tuttiTorneiUtente = new ArrayList<Torneo>();
            while (r.next()) {
                tuttiTorneiUtente.add(new Torneo(r.getInt(1), r.getInt(2), r.getString(3), r.getInt(4), r.getInt(5), r.getString(6), r.getDate(7).toLocalDate(), r.getDate(8).toLocalDate()));
            }
            return tuttiTorneiUtente;

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static ArrayList<Torneo> queryGetTorneiSede(Gestore gestore) {
        String query = "SELECT id, ref_Sede, sport, n_Squadre, n_giocatori_squadra, vincoli, data_inizio, data_fine FROM Torneo WHERE ref_Sede = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, gestore.getSede());
            var r = stmt.executeQuery();
            ArrayList<Torneo> torneiSede = new ArrayList<Torneo>();
            while (r.next()) {
                torneiSede.add(new Torneo(r.getInt(1), r.getInt(2), r.getString(3), r.getInt(4), r.getInt(5), r.getString(6), r.getDate(7).toLocalDate(), r.getDate(8).toLocalDate()));
            }
            return torneiSede;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static Torneo queryGetTorneo(Torneo torneo) {
        String query = "SELECT id, ref_Sede, sport, n_Squadre, n_giocatori_squadra,vincoli, data_inizio, data_fine FROM Torneo WHERE id = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, torneo.getId());
            var r = stmt.executeQuery();
            if (r.next()) {
                Torneo torneoRisultato = new Torneo(r.getInt(1), r.getInt(2), r.getString(3), r.getInt(4), r.getInt(5), r.getString(6), r.getDate(7).toLocalDate(), r.getDate(8).toLocalDate());
                return torneoRisultato;
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static int queryGetNumeroPartecipantiSquadreTorneo(Torneo torneo, int numeroSquadra) {
        String query = "SELECT COUNT(ref_Utente) AS NumeroDiPartecipanti FROM Iscrizione WHERE ref_Torneo = ? AND n_Squadra = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, torneo.getId());
            stmt.setInt(2, numeroSquadra);
            var r = stmt.executeQuery();
            if (r.next()) {
                int numeroDiPartecipanti = r.getInt("NumeroDiPartecipanti");
                return numeroDiPartecipanti;
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return 0;
    }


    public static int queryGetNumeroSquadreTorneo(Torneo torneo) {
        String query = "SELECT COUNT(DISTINCT n_Squadra) AS NumeroDiSquadre FROM Iscrizione WHERE ref_Torneo = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, torneo.getId());
            var r = stmt.executeQuery();
            if (r.next()) {
                int numeroSquadre = r.getInt("NumeroDiSquadre");
                return numeroSquadre;
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return 0;
    }

    public static ArrayList<Utente> queryGetGiocatoriRicercati(String nome, String cognome) {
        String query = "SELECT id, nome, cognome FROM utente WHERE (nome LIKE ? OR cognome LIKE ?) AND id > 0";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, "%" + nome + "%");
            stmt.setString(2, "%" + cognome + "%");
            var r = stmt.executeQuery();
            ArrayList<Utente> lista = new ArrayList<>();
            while (r.next()) {
                lista.add(new Utente(r.getInt(1), r.getString(2), r.getString(3)));
            }
            return lista;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static void queryPutSquadraInAttesa(Torneo torneo, int numeroSquadra, ArrayList<Utente> squadra, String nomeSquadra) {
        String query = "INSERT INTO SquadreAttesa (ref_Utente, ref_Torneo, n_Squadra, nomeSquadra) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            for (Utente utente : squadra) {
                stmt.setInt(1, utente.getId());
                stmt.setInt(2, torneo.getId());
                stmt.setInt(3, numeroSquadra);
                stmt.setString(4, nomeSquadra);
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    public static void queryPutSquadraTorneo(Torneo torneo, int numeroSquadra, ArrayList<Utente> squadra, String nomeSquadra) {
        String query = "INSERT INTO iscrizione (ref_Utente, ref_Torneo, n_Squadra, nomeSquadra) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            for (Utente utente : squadra) {
                stmt.setInt(1, utente.getId());
                stmt.setInt(2, torneo.getId());
                stmt.setInt(3, numeroSquadra);
                stmt.setString(4, nomeSquadra);
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    public static int getNumeroSquadraUtenteTorneo(Torneo torneo, Utente utente) {
        String query = "SELECT n_Squadra FROM iscrizione WHERE ref_Utente = ? AND ref_Torneo = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, utente.getId());
            stmt.setInt(2, torneo.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("n_Squadra");
                }
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;  // Restituisce -1 se non viene trovato alcun risultato
    }
    public static String getNomeSquadraUtenteTorneo(Torneo torneo, Utente utente) {
        String query = "SELECT nomeSquadra FROM iscrizione WHERE ref_Utente = ? AND ref_Torneo = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, utente.getId());
            stmt.setInt(2, torneo.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nomeSquadra");
                }
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return "";  // Restituisce -1 se non viene trovato alcun risultato
    }

    public static void queryDeleteSquadraTorneo(Torneo torneo, Utente utente, int numeroSquadra, String nomeSquadra) {
        String query = "SELECT ref_Utente FROM iscrizione WHERE ref_Torneo = ? AND n_Squadra = ? AND nomeSquadra = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, torneo.getId());
            stmt.setInt(2, numeroSquadra);
            stmt.setString(3, nomeSquadra);
            var r = stmt.executeQuery();
            String notifica = "Sei stato eliminato dalla squadra " + nomeSquadra + " del torneo " + torneo.getId();
            while (r.next()) {

                DBMSView.sendNotify2(notifica, r.getInt("ref_Utente"), 1);
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

        String query2 = "DELETE FROM iscrizione WHERE ref_Torneo = ? AND n_Squadra = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query2)) {
            stmt.setInt(1, torneo.getId());
            stmt.setInt(2, numeroSquadra);
            stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    public static void queryCreateTorneo(String sport, int livelloMinimo, int livelloMassimo, int numeroSquadre, String dataInizio, String dataFine, int refSede, int numeroGiocatoriPerSquadra) {
        String livello = livelloMinimo + "," + livelloMassimo;
        String query = "INSERT INTO torneo (ref_Sede, sport, n_Squadre, n_giocatori_squadra, vincoli, data_inizio, data_fine) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, refSede);
            stmt.setString(2, sport);
            stmt.setInt(3, numeroSquadre);
            stmt.setInt(4, numeroGiocatoriPerSquadra);
            stmt.setString(5, livello);
            stmt.setDate(6, java.sql.Date.valueOf(dataInizio));
            stmt.setDate(7, java.sql.Date.valueOf(dataFine));
            stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    public static ArrayList<Integer> queryGetTuttiGiocatori() {
        String query = "SELECT * FROM utente";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            var r = stmt.executeQuery();
            ArrayList<Integer> lista = new ArrayList<>();
            while (r.next()) {
                lista.add(r.getInt(1));
            }
            return lista;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static void queryDeleteTorneo(Torneo torneo) {
        String query = "SELECT ref_Utente FROM iscrizione WHERE ref_Torneo = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, torneo.getId());
            var r = stmt.executeQuery();
            String notifica = "Il torneo " + torneo.getId()+ " è stato eliminato dal gestore";
            while(r.next()) {

                DBMSView.sendNotify2(notifica, r.getInt("ref_Utente"),1);
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        String query2 = "DELETE FROM iscrizione WHERE ref_Torneo = ? ";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query2)) {
            stmt.setInt(1, torneo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        String query3 = "DELETE FROM SquadreAttesa WHERE ref_Torneo = ? ";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query3)) {
            stmt.setInt(1, torneo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

        String query4 = "DELETE FROM torneo WHERE id = ? ";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query4)) {
            stmt.setInt(1, torneo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

    }

    public static void queryUpdateTorneo(Torneo torneo, LocalDate dataInizio, LocalDate dataFine) {
        String query = "SELECT ref_Utente FROM iscrizione WHERE ref_Torneo = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, torneo.getId());
            var r = stmt.executeQuery();
            String notifica = "Il torneo " + torneo.getId()+ " è stato aggiornato dal gestore";
            while(r.next()) {

                DBMSView.sendNotify2(notifica, r.getInt("ref_Utente"),1);
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

        String query3 = "UPDATE torneo SET data_inizio = ?, data_fine = ? WHERE id = ? ";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query3)) {
            stmt.setDate(1, java.sql.Date.valueOf(dataInizio));
            stmt.setDate(2, java.sql.Date.valueOf(dataFine));
            stmt.setInt(3, torneo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

    }

    public static ArrayList<String> queryGetSquadreInAttesa(Torneo torneo) {
        String query = "SELECT n_Squadra, nomeSquadra FROM SquadreAttesa where ref_Torneo = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, torneo.getId());
            var r = stmt.executeQuery();
            ArrayList<String> lista = new ArrayList<>();
            while (r.next()) {
                String risultato ="Numero squadra: " + r.getInt(1)+ " nome squadra: " + r.getString(2);
                lista.add(risultato);
            }
            return lista;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static ArrayList<Utente> queryGetSquadra(int nSquadra, String nomeSquadra, Torneo torneo) {
        String query = "SELECT u.id, u.nome, u.cognome, u.email, u.username, u.sesso, u.tipo, u.passwordUtente, u.livello, u.eta " +
                "FROM SquadreAttesa sa " +
                "JOIN utente u ON sa.ref_Utente = u.id " +
                "WHERE sa.ref_Torneo = ? AND sa.n_Squadra = ? AND sa.nomeSquadra = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, torneo.getId());
            stmt.setInt(2, nSquadra);
            stmt.setString(3, nomeSquadra);
            ResultSet r = stmt.executeQuery();
            ArrayList<Utente> lista = new ArrayList<>();
            while (r.next()) {
                Utente utente = new Utente(
                        r.getInt("id"),
                        r.getString("nome"),
                        r.getString("cognome"),
                        r.getString("email"),
                        r.getString("username"),
                        r.getString("tipo"),
                        r.getString("passwordUtente"),
                        r.getFloat("livello"),
                        r.getInt("eta"),
                        r.getString("sesso")
                );
                lista.add(utente);
            }
            return lista;
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

        String getSquadra1 = "SELECT id, nome, cognome, email, username, eta, passwordUtente, tipo, sesso, livello FROM utente, partecipa WHERE ref_Partita = ? AND ref_Utente = id AND n_squadra = 1";
        try (PreparedStatement stmt = connDBMS.prepareStatement(getSquadra1)) {
            stmt.setInt(1, partita.getId());
            var r = stmt.executeQuery();
            ArrayList<Utente> squadra1 = new ArrayList<Utente>();
            while (r.next()) {
                squadra1.add(new Utente(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(8), r.getString(7), r.getFloat(10), r.getInt(6), r.getString(9)));
            }
            partitaDetails.setSquadra1(squadra1);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

        String getSquadra2 = "SELECT id, nome, cognome, email, username, eta, passwordUtente, tipo, sesso, livello FROM utente, partecipa WHERE ref_Partita = ? AND ref_Utente = id AND n_squadra = 2";
        try (PreparedStatement stmt = connDBMS.prepareStatement(getSquadra2)) {
            stmt.setInt(1, partita.getId());
            var r = stmt.executeQuery();
            ArrayList<Utente> squadra2 = new ArrayList<Utente>();
            while (r.next()) {
                squadra2.add(new Utente(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(8), r.getString(7), r.getFloat(10), r.getInt(6), r.getString(9)));
            }
            partitaDetails.setSquadra2(squadra2);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return partitaDetails;
    }

    public static ArrayList<Utente> queryGetUtenti(String parametri) {
        String query = "SELECT id,nome,cognome FROM utente WHERE (nome LIKE ? or cognome LIKE ?) AND (tipo<>?) AND id > 0";
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
                listanontesserati.add(new Utente(r.getInt("id"), r.getString("nome"), r.getString("cognome"), r.getString("email")));
            }
            return listanontesserati;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static ArrayList<Utente> queryGetGiocatoriSuggeriti(Utente utente) {
        String query = "SELECT DISTINCT u2.id, u2.nome, u2.cognome FROM utente u1, utente u2, partecipa p1, partecipa p2 WHERE u1.id = p1.ref_Utente AND u2.id = p2.ref_Utente AND p1.ref_Partita = p2.ref_Partita AND u1.id = ? AND u2.id > 0";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, utente.getId());
            var r = stmt.executeQuery();
            ArrayList<Utente> listasuggeriti = new ArrayList<Utente>();
            while (r.next()) {
                if (r.getInt(1) != utente.getId()) {
                    listasuggeriti.add(new Utente(r.getInt(1), r.getString(2), r.getString(3)));
                }
            }
            return listasuggeriti;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static void querySetAbbonamento(String codice, LocalDate data, int idutente, int idgestore) {
        String query = "INSERT INTO abbonamento(codice,data_Scadenza,ref_Tesserato,ref_Gestore) values(?,?,?,?)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, codice);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setInt(3, idutente);
            stmt.setInt(4, idgestore);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    public static Utente queryGetGiocatoreSconto(String codice) {
        String query = "SELECT u.id,u.nome,u.cognome,s.quantita FROM sconto s,utente u WHERE s.codice=? AND s.ref_Tesserato=u.id";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, codice);
            var r = stmt.executeQuery();
            while (r.next()) {
                return new Utente(r.getInt("id"), r.getString("nome"), r.getString("cognome"), r.getInt("quantita"));
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static void queryScontoApplicato(int id) {
        String query = "DELETE FROM sconto WHERE ref_Tesserato=?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, id);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    public static ArrayList<Utente> queryGetListaGiocatori(String textSearched) {
        String query = "SELECT id, nome, cognome FROM utente WHERE nome LIKE ? OR cognome LIKE ? AND id > 0";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, "%" + textSearched + "%");
            stmt.setString(2, "%" + textSearched + "%");
            var r = stmt.executeQuery();
            ArrayList<Utente> lista = new ArrayList<>();
            while (r.next()) {
                lista.add(new Utente(r.getInt(1), r.getString(2), r.getString(3)));
            }
            return lista;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static ArrayList<Abbonamento> getAbbonamenti(int idgestore) {
        String query = "SELECT * FROM abbonamento WHERE ref_Gestore=?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, idgestore);
            var r = stmt.executeQuery();
            ArrayList<Abbonamento> listaabb = new ArrayList<>();
            while (r.next()) {
                listaabb.add(new Abbonamento(r.getInt("ref_Tesserato"), r.getDate("data_Scadenza").toLocalDate(), r.getString("codice"), r.getInt("ref_Gestore"), r.getInt("notificato")));
            }
            return listaabb;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static void sendNotify2(String notifica, int id, int tipo) {
        String query = "INSERT INTO notifica(ref_Utente,tipo,messaggio) values (?,?,?)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setInt(2, tipo);
            stmt.setString(3, notifica);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    public static void querySetNotificato(int idutente) {
        String query = "UPDATE abbonamento SET notificato=1 WHERE ref_Tesserato=?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, idutente);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }


    public static void queryEliminaAbbonamento(int id) {
        String query = "DELETE FROM abbonamento WHERE ref_Tesserato=?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, id);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        query = "UPDATE utente SET tipo=? WHERE id=?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(2, id);
            stmt.setString(1, "nt");
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

    }

    public static void queryAddOspite(PartitaDetails partitaDetails, int n_squadra) {
        String query = "INSERT INTO partecipa(ref_Utente,ref_Partita,n_squadra,n_giocatori_allenamento) values (-1,?,?,0)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, partitaDetails.partita.getId());
            stmt.setInt(2, n_squadra);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    public static ArrayList<Partita> queryGetPartiteSede(int refSede, LocalDate data) {
        String query = "SELECT p.id, p.ref_Campo, p.dataOra, p.tipo, p.vincoli  FROM partita p,campo c,sede s WHERE p.ref_Campo=c.id AND c.ref_Sede=s.Id_Sede AND s.Id_Sede=? AND DATE(p.dataOra)=? AND p.tipo<>?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, refSede);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setString(3, "all");
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

    public static void queryCancellaPrenotazione(int id_partita, int id_utente) {
        String query = "DELETE FROM partecipa WHERE ref_Partita = ? AND ref_Utente = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, id_partita);
            stmt.setInt(2, id_utente);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    public static ArrayList<Campo> queryGetCampiLiberi2(int idsede, LocalDate data) {
        var query = "SELECT * FROM campo cp, orari o WHERE cp.ref_Sede = ? and orario not in (SELECT TIME(p.dataOra) as orario FROM partita p, campo c WHERE DATE(p.dataOra) = ? and p.ref_Campo = c.id and c.id = cp.id) ORDER BY cp.id";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, idsede);
            stmt.setDate(2, Date.valueOf(data));
            var r = stmt.executeQuery();
            ArrayList<Campo> listaCampi = new ArrayList<Campo>();
            while (r.next()) {
                listaCampi.add(new Campo(r.getInt("id"), r.getInt("ref_Sede"), r.getString("nome"), r.getString("sport"), LocalDateTime.of(data, r.getTime("orario").toLocalTime())));
            }
            return listaCampi;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static ArrayList<Utente> queryGetGiocatoriCompatibili(String vincoli) {
        String[] vincoli2 = vincoli.split(";");
        char sesso = vincoli2[0].charAt(0);
        int fromlivello = Integer.parseInt(vincoli2[1]);
        int tolivello = Integer.parseInt(vincoli2[2]);
        int fromage = Integer.parseInt(vincoli2[3]);
        int toage = Integer.parseInt(vincoli2[4]);
        String query = "SELECT id,nome,cognome FROM utente WHERE sesso = ? AND livello BETWEEN ? AND ? AND eta BETWEEN ? AND ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(sesso));
            stmt.setInt(2, fromlivello);
            stmt.setInt(3, tolivello);
            stmt.setInt(4, fromage);
            stmt.setInt(5, toage);
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

    public static ArrayList<Campo> queryGetDisponibilitaCampoData(int idcampo, LocalDate data) {
        var query = "SELECT * FROM campo cp, orari o WHERE cp.id = ? and orario not in (SELECT TIME(p.dataOra) as orario FROM partita p, campo c WHERE DATE(p.dataOra) = ? and p.ref_Campo = c.id and c.id = cp.id) ORDER BY cp.id";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, idcampo);
            stmt.setDate(2, Date.valueOf(data));
            var r = stmt.executeQuery();
            ArrayList<Campo> listaCampi = new ArrayList<Campo>();
            while (r.next()) {
                listaCampi.add(new Campo(r.getInt("id"), r.getInt("ref_Sede"), r.getString("nome"), r.getString("sport"), LocalDateTime.of(data, r.getTime("orario").toLocalTime())));
            }
            return listaCampi;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static void queryRinviaPartita(int idPartita, LocalDateTime dataora) {
        String query = "UPDATE partita SET dataOra=? WHERE id=?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setTimestamp(1, Timestamp.valueOf(dataora));
            stmt.setInt(2, idPartita);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

    }

    public static ArrayList<Partita> queryGetTuttePartite(Sede s, String sport) {
        String query = "SELECT p.id,p.ref_Campo,p.dataOra,p.tipo,p.vincoli FROM partita p, campo c WHERE c.id = p.ref_Campo AND c.sport = ? AND c.ref_Sede = ? AND p.tipo = ? AND p.id NOT IN (SELECT id_partita_origine FROM partitestorico)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, sport);
            stmt.setInt(2, s.getId_sede());
            stmt.setString(3, "ppub");
            var r = stmt.executeQuery();
            ArrayList<Partita> listaPartite = new ArrayList<>();
            while (r.next()) {
                listaPartite.add(new Partita(r.getInt(1), r.getInt(2), r.getTimestamp(3).toLocalDateTime(), r.getString(4), r.getString(5)));
            }
            return listaPartite;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static void querySetRichiestaAccettazione(int id_utente, int id_partita, int n) {
        String query = "INSERT INTO accettazione(ref_Utente,ref_Partita,n_accettazioni) VALUES(?,?,?)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, id_utente);
            stmt.setInt(2, id_partita);
            stmt.setInt(3, n);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }


    public static void querySendPunteggi(ArrayList<Integer> ids, ArrayList<Float> punteggi) {
        String selectQuery = "SELECT livello FROM utente WHERE id=?";
        String updateQuery = "UPDATE utente SET livello=? WHERE id=?";

        try (PreparedStatement selectStmt = connDBMS.prepareStatement(selectQuery);
             PreparedStatement updateStmt = connDBMS.prepareStatement(updateQuery)) {

            for (int i = 0; i < ids.size(); i++) {
                // Eseguire la query di selezione
                selectStmt.setInt(1, ids.get(i));
                try (ResultSet r = selectStmt.executeQuery()) {
                    Float livellocorrente = 0.0F;
                    if (r.next()) {
                        livellocorrente = r.getFloat("livello");
                    }
                    livellocorrente += punteggi.get(i);
                    // Eseguire la query di aggiornamento
                    updateStmt.setFloat(1, livellocorrente);
                    updateStmt.setInt(2, ids.get(i));
                    updateStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    public static void queryPartitaGiocata(int idpartita, String risultato, LocalDateTime dataOra) {
        String query = "INSERT INTO partitestorico(id_partita_origine,data,risultato) values(?,?,?)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, idpartita);
            stmt.setTimestamp(2, Timestamp.valueOf(dataOra));
            stmt.setString(3, risultato);
            var t = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

    }

    public static ArrayList<Partita> queryGetPartiteAllenatore(int id_allenatore) {
        String query = "SELECT p.id, p.ref_Campo, p.dataOra, p.tipo FROM partita p, partecipa pr WHERE p.id = pr.ref_Partita AND pr.ref_Utente = ? AND p.id IN (SELECT id_partita_origine FROM partitestorico)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, id_allenatore);
            var r = stmt.executeQuery();
            ArrayList<Partita> allenamenti = new ArrayList<>();
            while (r.next()) {
                allenamenti.add(new Partita(r.getInt(1), r.getInt(2), r.getTimestamp(3).toLocalDateTime(), r.getString(4), ""));
            }
            return allenamenti;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static void queryAddBonusPlayer(Utente utente, float value) {
        String query = "UPDATE utente SET livello = livello + ? WHERE id = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setFloat(1, value);
            stmt.setInt(2, utente.getId());
            var t = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    public static ArrayList<Partita> queryGetAllPartiteSede(int refSede) {
        String query = "SELECT p.id, p.ref_Campo, p.dataOra, p.tipo, p.vincoli  FROM partita p,campo c,sede s WHERE p.ref_Campo=c.id AND c.ref_Sede=s.Id_Sede AND s.Id_Sede=? AND  p.tipo<> ? AND p.id NOT IN (SELECT id_partita_origine FROM partitestorico)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, refSede);
            stmt.setString(2, "all");
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

    public static String queryGetSport(int idcampo) {
        String query = "SELECT sport FROM campo WHERE id=?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, idcampo);
            var r = stmt.executeQuery();
            while (r.next()) {
                return r.getString("sport");
            }

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static void queryCancellaPartita(int idpartita) {
        String query = "DELETE FROM partecipa WHERE ref_Partita=?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, idpartita);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        String query2 = "DELETE FROM partita WHERE id=?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query2)) {
            stmt.setInt(1, idpartita);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }


    public static ArrayList<Partita> queryGetPreviousAllenamenti(LocalDateTime data, int idsede) {
        String query = "SELECT p.id, p.ref_Campo, p.dataOra, p.tipo, p.vincoli  FROM partita p,campo c,sede s WHERE p.ref_Campo=c.id AND c.ref_Sede=s.Id_Sede AND s.Id_Sede=? AND  p.tipo=? AND p.dataOra<? AND p.id NOT IN (SELECT id_partita_origine FROM partitestorico)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, idsede);
            stmt.setString(2, "all");
            stmt.setTimestamp(3, Timestamp.valueOf(data));
            var r = stmt.executeQuery();
            ArrayList<Partita> listaall = new ArrayList<>();
            while (r.next()) {
                listaall.add(new Partita(r.getInt(1), r.getInt(2), r.getTimestamp(3).toLocalDateTime(), r.getString(4), r.getString(5)));
            }
            return listaall;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static void queryAllenamentoFinito(int idallenamento, LocalDateTime data) {
        String query = "INSERT INTO partitestorico(id_partita_origine,data,risultato) values(?,?,?)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, idallenamento);
            stmt.setTimestamp(2, Timestamp.valueOf(data));
            stmt.setString(3, "-");
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

    }

    public static ArrayList<Utente> queryGetTesserati() {
        String query = "SELECT id,nome,cognome FROM utente WHERE tipo=?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, "t");
            var r = stmt.executeQuery();
            ArrayList<Utente> listaUtenti = new ArrayList<>();
            while (r.next()) {
                listaUtenti.add(new Utente(r.getInt(1), r.getString(2), r.getString(3)));
            }
            return listaUtenti;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static void querySendDiscountCode(String codice, int idtesserato) {
        String query = "INSERT INTO sconto(ref_Tesserato,codice,quantita) values(?,?,?)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, idtesserato);
            stmt.setString(2, codice);
            stmt.setInt(3, 30);
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

    }

    public static ArrayList<Utente> queryGetUtentiInattivi() {
        String query = "SELECT u.id,u.nome,u.cognome FROM utente u LEFT JOIN partecipa p ON u.id = p.ref_Utente LEFT JOIN partita pa ON p.ref_Partita = pa.id WHERE pa.dataOra IS NULL OR pa.dataOra < DATE_SUB(CURDATE(), INTERVAL 10 DAY);";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            var r = stmt.executeQuery();
            ArrayList<Utente> listaUtenti = new ArrayList<>();
            while (r.next()) {
                listaUtenti.add(new Utente(r.getInt(1), r.getString(2), r.getString(3)));
            }
            return listaUtenti;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;

    }

    public static ArrayList<Campo> queryGetUsualPartite(int idutente) {
        String query = "SELECT p1.ref_Campo, TIME(p1.dataOra) as ora, COUNT(*) as num_partite FROM partita p1 JOIN partecipa p2 ON p1.id = p2.ref_Partita WHERE p2.ref_Utente = ? GROUP BY p1.ref_Campo, ora HAVING num_partite >= 3";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, idutente);
            var r = stmt.executeQuery();
            ArrayList<Campo> listaUsual = new ArrayList<>();
            while (r.next()) {
                listaUsual.add(new Campo(r.getInt(1), LocalDateTime.of(LocalDate.now(), r.getTime(2).toLocalTime())));
            }
            return listaUsual;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static boolean queryCheckDisponibitaCampo(int idcampo, LocalDateTime dataOra) {
        String query = "SELECT COUNT(*) as num_partite \n" +
                "FROM partita \n" +
                "WHERE ref_Campo = ? \n" +
                "AND TIME(dataOra) = ? \n" +
                "AND DATE(dataOra) = CURDATE();";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, idcampo);
            stmt.setTime(2, Time.valueOf(dataOra.toLocalTime()));
            var r = stmt.executeQuery();
            if (r.next()) {
                if (r.getInt(1) == 0) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    public static ArrayList<Partita> queryGetAllenamenti() {
        String query = "SELECT * FROM partita WHERE tipo = ? AND id NOT IN (SELECT id_partita_origine FROM partitestorico)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, "all");
            var r = stmt.executeQuery();
            ArrayList<Partita> listaAllenamenti = new ArrayList<>();
            while (r.next()) {
                listaAllenamenti.add(new Partita(r.getInt(1), r.getInt(2), r.getTimestamp(3).toLocalDateTime(), r.getString(4), r.getString(5)));
            }
            return listaAllenamenti;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static Allenamento queryGetDettagliAllenamento(Partita allenamento) {
        String query = "SELECT s.Id_Sede, s.Indirizzo, s.Nome_Sede, c.id, c.nome, c.sport FROM partita p, campo c, sede s WHERE p.ref_Campo = c.id AND c.ref_Sede = s.Id_Sede AND p.id = ?";
        Allenamento allenamentoDetails = new Allenamento();
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, allenamento.getId());
            var r = stmt.executeQuery();
            if (r.next()) {
                allenamentoDetails.setSede(new Sede(r.getInt(1), r.getString(3), r.getString(2)));
                allenamentoDetails.setCampo(new Campo(r.getInt(4), r.getInt(1), r.getString(5), r.getString(6), allenamento.getDataOra()));
                allenamentoDetails.setPartita(allenamento);
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

        String getPartecipanti = "SELECT id, nome, cognome, email, username, eta, passwordUtente, tipo, sesso, livello FROM utente, partecipa WHERE ref_Partita = ? AND ref_Utente = id AND n_squadra = 1";
        try (PreparedStatement stmt = connDBMS.prepareStatement(getPartecipanti)) {
            stmt.setInt(1, allenamento.getId());
            var r = stmt.executeQuery();
            ArrayList<Utente> squadra1 = new ArrayList<Utente>();
            while (r.next()) {
                squadra1.add(new Utente(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(8), r.getString(7), r.getFloat(10), r.getInt(6), r.getString(9)));
            }
            allenamentoDetails.setPartecipanti(squadra1);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

        String getAllenatore = "SELECT id, nome, cognome, email, username, eta, passwordUtente, tipo, sesso, livello FROM utente WHERE id = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(getAllenatore)) {
            stmt.setInt(1, Integer.parseInt(allenamento.getVincoli().split(";")[0]));
            var r = stmt.executeQuery();
            if (r.next()) {
                allenamentoDetails.setAllenatore(new Utente(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(8), r.getString(7), r.getFloat(10), r.getInt(6), r.getString(9)));
            }

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return allenamentoDetails;
    }

    public static ArrayList<Partita> queryGetMieiAllenamenti(Utente u) {
        String query = "SELECT partita.* FROM partita, partecipa WHERE tipo = ? AND ref_Partita = id AND ref_Utente = ? AND id NOT IN (SELECT id_partita_origine FROM partitestorico)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, "all");
            stmt.setInt(2, u.getId());
            var r = stmt.executeQuery();
            ArrayList<Partita> listaAllenamenti = new ArrayList<>();
            while (r.next()) {
                listaAllenamenti.add(new Partita(r.getInt(1), r.getInt(2), r.getTimestamp(3).toLocalDateTime(), r.getString(4), r.getString(5)));
            }
            return listaAllenamenti;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static void queryCancellaUtenteAllenamento(Utente utente, Allenamento allenamento) {
        String query = "DELETE FROM partecipa WHERE ref_Partita = ? AND ref_Utente = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, allenamento.partita.getId());
            stmt.setInt(2, utente.getId());
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    public static void queryCancellaAllenamento(Allenamento allenamento) {
        String query = "DELETE FROM partecipa WHERE ref_Partita = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, allenamento.partita.getId());
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        String query1 = "DELETE FROM partita WHERE id = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query1)) {
            stmt.setInt(1, allenamento.partita.getId());
            var r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    public static ArrayList<Campo> queryGetOrariAllenamtento(LocalDate data, String sport) {
        var query = "SELECT * FROM campo cp, orari o WHERE cp.sport = ? and orario not in (SELECT TIME(p.dataOra) as orario FROM partita p, campo c WHERE DATE(p.dataOra) = ? and p.ref_Campo = c.id and c.id = cp.id) ORDER BY cp.id";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setString(1, sport);
            stmt.setDate(2, Date.valueOf(data));
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

    public static void queryInsertNuovoAllenamento(Campo campo, int id_all, int n_partecipanti) {
        String query = "INSERT INTO partita(ref_Campo, dataOra, tipo, vincoli) values(?,?,?,?)";
        int id_allenamento = 0;
        try (PreparedStatement stmt = connDBMS.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, campo.getId_campo());
            stmt.setTimestamp(2, Timestamp.valueOf(campo.getOrario()));
            stmt.setString(3, "all");
            stmt.setString(4, String.valueOf(id_all) + ";" + String.valueOf(n_partecipanti));
            int r = stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id_allenamento = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

        String query1 = "INSERT INTO partecipa(ref_Utente, ref_Partita, n_squadra, n_giocatori_allenamento) values(?,?,1,0)";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query1)) {
            stmt.setInt(1, id_all);
            stmt.setInt(2, id_allenamento);
            int r = stmt.executeUpdate();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    public static boolean queryVerificaAllenamento(int id) {
        String query = "SELECT vincoli FROM partita WHERE id = ?";
        int dimensione = 0;
        int partecipanti = 0;

        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet r = stmt.executeQuery()) {
                if (r.next()) {
                    String vincoli = r.getString("vincoli");
                    dimensione = Integer.parseInt(vincoli.split(";")[1]);  // Assumendo che il numero di partecipanti sia il secondo elemento
                }
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
            return false;  // Restituire false in caso di errore per coerenza
        }

        String query1 = "SELECT COUNT(*) FROM partecipa WHERE ref_Partita = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query1)) {
            stmt.setInt(1, id);
            try (ResultSet r = stmt.executeQuery()) {
                if (r.next()) {
                    partecipanti = r.getInt(1);
                }
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
            return false;
        }

        return partecipanti < dimensione;
    }

    public static ArrayList<Partita> queryGetAllenamentiSede(int id_Sede, LocalDate data){
        String query = "SELECT p.* FROM partita p, campo c WHERE p.ref_Campo = c.id AND c.ref_Sede = ? AND p.tipo = \"all\" AND DATE(p.dataOra) = ?";
        try (PreparedStatement stmt = connDBMS.prepareStatement(query)) {
            stmt.setInt(1, id_Sede);
            stmt.setDate(2, Date.valueOf(data));
            var r = stmt.executeQuery();
            ArrayList<Partita> listaAllenamenti = new ArrayList<>();
            while (r.next()) {
                listaAllenamenti.add(new Partita(r.getInt(1), r.getInt(2), r.getTimestamp(3).toLocalDateTime(), r.getString(4), r.getString(5)));
            }
            return listaAllenamenti;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

}
