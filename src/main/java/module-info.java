module com.prova.matchme {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires mysql.connector.j;
    requires jakarta.mail;
    requires com.fasterxml.jackson.annotation;
    requires java.desktop;
    requires annotations;

    opens com.prova.matchme to javafx.fxml;
    opens com.prova.matchme.Autenticazione.Interfacce to javafx.fxml;
    opens com.prova.matchme.shared to javafx.fxml;
    opens com.prova.matchme.GestioneProfilo.Interfacce to javafx.fxml;
    opens com.prova.matchme.GestionePartita.Interfacce to javafx.fxml;
    opens com.prova.matchme.GestioneMessaggistica.Interfacce to javafx.fxml;
    opens com.prova.matchme.GestioneNotifiche.Interfacce to javafx.fxml;
    opens com.prova.matchme.GestioneTornei.Interfacce to javafx.fxml;
    opens com.prova.matchme.GestioneSede.Interfacce to javafx.fxml;
    opens com.prova.matchme.GestioneAllenamenti.Interfacce to javafx.fxml;


    exports com.prova.matchme;
    exports com.prova.matchme.shared;
}