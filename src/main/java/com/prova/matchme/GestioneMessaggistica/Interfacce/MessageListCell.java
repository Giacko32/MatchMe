package com.prova.matchme.GestioneMessaggistica.Interfacce;

import com.prova.matchme.Entity.Messaggio;
import javafx.scene.control.ListCell;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class MessageListCell extends ListCell<Messaggio> {
    private int myUserId;

    public MessageListCell(int myUserId) {
        this.myUserId = myUserId;
    }

    @Override
    protected void updateItem(Messaggio message, boolean empty) {
        super.updateItem(message, empty);

        if (empty || message == null) {
            setText(null);
            setGraphic(null);
        } else {
            HBox hBox = new HBox();
            Label textLabel = new Label(message.getMessaggio());
            Pane pane = new Pane();
            HBox.setHgrow(pane, Priority.ALWAYS);

            if (message.getIdmittente() == myUserId) {
                textLabel.setStyle("-fx-background-color: lightblue; -fx-padding: 10px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
                hBox.getChildren().addAll(pane, textLabel); // Messaggio allineato a destra
            } else {
                textLabel.setStyle("-fx-background-color: lightgreen; -fx-padding: 10px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
                hBox.getChildren().addAll(textLabel, pane); // Messaggio allineato a sinistra
            }

            setGraphic(hBox);
        }
    }
}
