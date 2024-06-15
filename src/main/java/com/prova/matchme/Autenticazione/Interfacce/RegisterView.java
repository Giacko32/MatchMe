package com.prova.matchme.Autenticazione.Interfacce;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class RegisterView {


    private AuthCtrl authCtrl;

    public RegisterView(AuthCtrl authCtrl) {
        this.authCtrl = authCtrl;
    }

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField nomefield;

    @FXML
    private TextField cognomefield;

    @FXML
    private DatePicker datanascita;

    @FXML
    private RadioButton uomoButton;

    @FXML
    private RadioButton donnaButton;

    @FXML
    private TextField usernamefield;

    @FXML
    private TextField mailfield;

    @FXML
    private TextField passwordfield;

    @FXML
    public void initialize() {
        // Load the image
        Image backgroundImage = new Image(getClass().getResource("/com/prova/matchme/images/backgroundlogin.png").toExternalForm());

        // Create a BackgroundImage
        BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

        // Set the background to the AnchorPane
        anchorPane.setBackground(new Background(bgImage));
    }

    @FXML
    public void backlogin() {
        this.authCtrl.toLogin();
    }

    @FXML
    public void ClickRegistra() {
        char sesso = 'V';
        if (uomoButton.isSelected()) {
            sesso = 'u';
        } else if (donnaButton.isSelected()){
            sesso = 'd';
        }
        authCtrl.SendDati(nomefield.getText().trim(), cognomefield.getText().trim(), mailfield.getText().trim(), usernamefield.getText().trim(), datanascita.getValue(), passwordfield.getText().trim(), "nt", sesso, 0.0F);
    }

    @FXML
    public void ShowBnd() {

    }

}
