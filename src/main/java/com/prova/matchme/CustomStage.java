package com.prova.matchme;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CustomStage extends Stage {
    public CustomStage(String title){
        super();
        this.setTitle(title);
        this.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
    }
}
