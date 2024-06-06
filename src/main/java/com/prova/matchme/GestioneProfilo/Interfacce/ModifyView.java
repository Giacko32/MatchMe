package com.prova.matchme.GestioneProfilo.Interfacce;

import com.prova.matchme.Entity.Gestore;
import com.prova.matchme.Entity.Utente;
import com.prova.matchme.GestioneProfilo.Controller.ProfiloCtrl;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ModifyView {

	private ProfiloCtrl profiloCtrl;
	private Utente u;
	private Gestore g;
	public ModifyView(ProfiloCtrl profiloCtrl, Utente u){
		this.profiloCtrl=profiloCtrl;
		this.u=u;
	}
	public ModifyView(ProfiloCtrl profiloCtrl,  Gestore g){
		this.profiloCtrl=profiloCtrl;
		this.g=g;
	}

	@FXML
	public TextField nomeUtente;
	public TextField cognomeUtente;
	public TextField emailUtente;
	public TextField username;
	public AnchorPane Anchorpane;

	@FXML
	public TextField nomeGestore;
	public TextField cognomeGestore;
	public TextField emailGestore;
	public TextField usernameGestore;

	@FXML
	public void initialize(){
		if(u!=null) {
			nomeUtente.setText(u.getNome());
			cognomeUtente.setText(u.getCognome());
			emailUtente.setText(u.getEmail());
			username.setText(u.getUsername());
		}
		if(g!=null){
			nomeGestore.setText(g.getNome());
			cognomeGestore.setText(g.getCognome());
			emailGestore.setText(g.getEmail());
			usernameGestore.setText(g.getUsername());
		}
	}

	@FXML
	public void back(){
		this.profiloCtrl.toMain();
	}

	@FXML
	public void ClickModify() {
		if (u!=null){
			this.profiloCtrl.PassData(username.getText(),emailUtente.getText(),nomeUtente.getText(),cognomeUtente.getText());
		}
		if(g!=null){
			this.profiloCtrl.PassData(usernameGestore.getText(),emailGestore.getText(),nomeGestore.getText(),cognomeGestore.getText());
		}
	}


}
