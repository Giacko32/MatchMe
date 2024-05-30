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

    opens com.prova.matchme to javafx.fxml;
    opens com.prova.matchme.Autenticazione.Interfacce to javafx.fxml;

    exports com.prova.matchme;
}