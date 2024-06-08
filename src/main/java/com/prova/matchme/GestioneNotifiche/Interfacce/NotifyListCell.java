package com.prova.matchme.GestioneNotifiche.Interfacce;



import com.prova.matchme.Entity.Notifica;
import javafx.scene.control.ListCell;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class NotifyListCell extends ListCell<Notifica> {
    @Override
    protected void updateItem(Notifica notification, boolean empty) {
        super.updateItem(notification, empty);

        if (empty || notification == null) {
            setText(null);
            setGraphic(null);
        } else {
            HBox hBox = new HBox();
            Label textLabel = new Label(notification.toString());
            Pane pane = new Pane();
            HBox.setHgrow(pane, Priority.ALWAYS);
            textLabel.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 10px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: #dcdcdc; -fx-border-width: 1px;");
            hBox.getChildren().addAll(textLabel, pane);

            setGraphic(hBox);
        }
    }
}

