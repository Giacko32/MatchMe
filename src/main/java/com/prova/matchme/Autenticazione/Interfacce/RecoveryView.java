package com.prova.matchme.Autenticazione.Interfacce;

import com.prova.matchme.Autenticazione.Controller.AuthCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RecoveryView {


    public TextField codice;
    private AuthCtrl authCtrl;
    private Stage s;

    public RecoveryView(AuthCtrl authCtrl, Stage s) {
        this.authCtrl = authCtrl;
        this.s = s;
    }

    @FXML
    private TextField email;

    @FXML
    public void ClickInviaMail() {
        if (this.authCtrl.passMail(email.getText())) {

        } else {
            s.close();
        }
    }

    @FXML
    public void ClickVerificaCodice() {
        this.authCtrl.passcode(codice.getText());
        s.close();

    }


}
