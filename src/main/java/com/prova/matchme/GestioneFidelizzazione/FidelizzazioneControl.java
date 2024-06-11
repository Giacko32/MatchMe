package com.prova.matchme.GestioneFidelizzazione;


import com.prova.matchme.DBMSView;
import com.prova.matchme.Entity.Utente;

import java.util.ArrayList;

public class FidelizzazioneControl {

	public void fideltess(){
		ArrayList<Utente> lista= DBMSView.queryGetTesserati();
		lista=pickRandomTesserati(lista);
		for(Utente u:lista){
			String codice=generateDiscountCode();
			DBMSView.querySendDiscountCode(codice,u.getId());
			String notifica="Grazie per essere abbonato, come regalo ti mandiamo questo codice sconto da usare quando desideri: "+codice;
			DBMSView.sendNotify2(notifica,u.getId(),1);
		}
	}
	public void recupera(){
		ArrayList<Utente> lista= DBMSView.queryGetUtentiInattivi();
		for(Utente u:lista){
			String codice=generateDiscountCode();
			DBMSView.querySendDiscountCode(codice,u.getId());
			String notifica="Ciao abbiamo notato la tua assenza, per invogliarti a tornare a giocare ti mandiamo questo codice sconto da usare quando desideri: "+codice;
			DBMSView.sendNotify2(notifica,u.getId(),1);
		}
	}


	private Object ListaTesserati;

	private Object ListaRandomTesserati;

	private Object ListaPartiteUsual;

	public String generateDiscountCode() {
		StringBuilder codice = new StringBuilder();
		char[] lettere = "abcdefghijkmlmnopqrstuvwxyz1234567890".toCharArray();
		for (int i = 0; i < 7; i++) {
			codice.append(lettere[((int) (Math.random() * lettere.length))]);
		}
		return codice.toString();
	}

	public ArrayList<Utente> pickRandomTesserati(ArrayList<Utente> listatesserati) {
		if(listatesserati.size()<30){
			return listatesserati;
		}else{
			ArrayList<Utente> listaret=new ArrayList<>();
			for(Utente u:listatesserati){
				if((int)(Math.random()*2)==0){
					listaret.add(u);
				}
			}
			return listaret;
		}
	}

}
