package com.prova.matchme;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBMSView {

    private static final String baseUrl = "localhost";

    private static final int port = 3306;

    private static final String user = "root";

    private static final String pass = "Rtx4060ticx!";

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


    public static void connectFarmacia() {
        try {
            if (connDBMS == null) {
                //Main.log.debug("Connettendo con Farmacia...");
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                DBMSView.connDBMS = DriverManager.getConnection(buildConnectionUrl("matchmeDB"));
                //Main.log.info("Connesso con Farmacia");
            }
        } catch (java.sql.SQLException e) {
            erroreComunicazioneDBMS(e);

        }
    }
}
