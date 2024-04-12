package com.prova.matchme;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class MatchMeController {

    @FXML
    protected void registrazione()
    {
        FXMLLoader loader = new FXMLLoader(ApplicationMatchMe.class.getResource("Register-view.fxml"));
    }

}