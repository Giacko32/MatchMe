package com.prova.matchme;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HelloController {
    @FXML
    private Label username;

    @FXML
    protected void funzioneacaso()
    {
        username.setText("suca");
    }


}