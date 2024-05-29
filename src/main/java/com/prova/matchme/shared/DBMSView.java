package com.prova.matchme.shared;
/*
public class DBMSView {

	public String queryDBMSGetPassword(String username) {
		return null;
	}

	public lista queryDBMSGetDati(String username){
		return null;
	}

	public boolean queryDBMSCheckUsernameandMail(username,email)() {
		return false;
	}

	public void queryDBMSRegisterUser(dati)() {

	}

	public boolean queryDBMSCheckMail() {
		return false;
	}

	public void queryDBMSUpdatePassword() {

	}

	public void queryDBMSUpdateData() {

	}

	public lista getUtentiInattivi() {
		return null;
	}

	public lista getTesserati() {
		return null;
	}

	public void sendNotify(String notify, int lista id destinatari) {

	}

	public boolean checkDisponibilitàCampo(String id campo, String data, String ora) {
		return false;
	}

	public lista getUsualPartite(String id Tesserato) {
		return null;
	}

	public void getUser() {

	}

	public void createNewChat(int id utente1, int id utente2) {

	}

	public int queryGetIdCode(int codice) {
		return 0;
	}

	public void queryAttivaAbbonamento(int id) {

	}

	public lista queryPartiteGiocate() {
		return null;
	}

	public void queryNewMessage(String message, int id_chat, int id_mittente) {

	}

	public lista queryGetNuoviMessaggi() {
		return null;
	}

	public void queryDeleteMessages() {

	}

	public void queryGetDetailsPartita(int id partita) {

	}

	public void queryAddGiocatore(int id player, int id squadra) {

	}

	public void queryGetDettagliPartecipantePartita(int id partecipante, int id partita) {

	}

	public void querySetAccettazione() {

	}

	public lista queryGetNuoveNotifiche(int id utente) {
		return null;
	}

	public void queryEliminaNotifiche() {

	}

	public void queryGetGiocatoreSconto() {

	}

	public void queryScontoApplicato() {

	}

	public lista querySearchTesserati() {
		return null;
	}

	public void sendDiscountCode(String Code) {

	}

	public void queryAttivaAllenatore() {

	}

	public void querySearchNonTesserati(String Search Field) {

	}

	public void querySetAbbonamento(int id, String codice, String sede, datetime datafine) {

	}

	public lista queryGetAbbonamenti(String sede) {
		return null;
	}

	public void queryEliminaAbbonamento(int id) {

	}

	public lista queryGetPartiteAllenatore(int id allenatore) {
		return null;
	}

	public void queryAddBonusPlayer(int PlayerId, int BonusValue) {

	}

	public void queryCreaPartita() {

	}

	public lista queryGetAllPartiteSede(String sede) {
		return null;
	}

	public lista queryGetTesseratiLiberiCompatibili(Vincoli vincoli, int id campo, datetime orario) {
		return null;
	}

	public void queryCancellaPartita(int id partita) {

	}

	public lista queryGetPartiteSedeSportData() {
		return null;
	}

	public void queryGetDetailsCampo() {

	}

	public lista queryGetPartiteUtente(int id utente) {
		return null;
	}

	public lista queryGetPartiteSedeSport(String Sede, String sport) {
		return null;
	}

	public lista queryGetGiocatoriSuggeriti(int id) {
		return null;
	}

	public void queryGetListaGiocatori() {

	}

	public void sendInvitation() {

	}

	public void queryAddOspite() {

	}

	public void queryCancellaPrenotazione() {

	}

	public void queryNotificaAccettazione() {

	}

	public lista queryGetPartiteSede(datetime data, String sede) {
		return null;
	}

	public lista queryGetGiocatoriCompatibili(int Vincoli) {
		return null;
	}

	public lista queryGetGiocatoriRicercati(String Dati) {
		return null;
	}

	public lista queryGetCampiDisponibili(String sede, datetime date) {
		return null;
	}

	public void queryCreatePartita(String Sede, datetime Date, int Campo, int Vincoli) {

	}

	public void querySendPunteggi(int punteggio, lista id destinatari) {

	}

	public void queryPartitaGiocata(int id) {

	}

	public lista queryGetTorneiSede(String sede) {
		return null;
	}

	public Torneo queryGetTorneo(int id torneo) {
		return null;
	}

	public void queryCreateTorneo(String sede, int vincoli) {

	}

	public void queryGetTuttiGiocatori() {

	}

	public void queryUpdateTorneo(int vincoli, int id Torneo) {

	}

	public void queryCancellaTorneo(int id Torneo) {

	}

	public lista queryGetSquadreInAttesa(int id Torneo) {
		return null;
	}

	public Squadra queryGetSquadra(int id squadra) {
		return null;
	}

	public void querySendInvitation() {

	}

	public void queryUpdateSquadraTorneo(int id Squadra, int id Torneo) {

	}

	public void queryRimuoviSquadraInAttesa() {

	}

	public lista queryGetTuttiITornei() {
		return null;
	}

	public lista queryGetImieiTornei(int id ) {
		return null;
	}

	public void queryPutSquadraInAttesa(Squadra squadra, int id Torneo) {

	}

	public void queryPutSquadraTorneo(Squadra squadra, int Id Torneo) {

	}

	public void queryDeleteSquadraTorneo(int id_utente) {

	}

	public void queryAddPartiteCalendario(int sede, Squadra squadre, datetime data) {

	}

	public void queryGetDisponibilitàCampoData() {

	}

	public void queryRinviaPartita() {

	}

	public boolean queryGiocatoreOccupato(int id Giocatore, datetime date) {
		return false;
	}

	public int CheckNumeroSquadreTorneo(int id Torneo) {
		return 0;
	}

	public void GetTesserati() {

	}

	public void SendDiscountCode() {

	}

	public void getusualPartite() {

	}

	public lista getChat(String id) {
		return null;
	}

	public void insertChat(String id, String nome utente, String cognome utente) {

	}

	public lista GetDetailchat(int id) {
		return null;
	}

	public void SalvaNuoviMessaggi() {

	}

	public lista getNotifiche(int id) {
		return null;
	}

	public void eliminaNotifica() {

	}

	public void SalvaNuoveNotifiche() {

	}

	public void queryGetOrariAllenamento() {

	}

	public void queryInsertNuovoAllenamento() {

	}

	public void queryGetAllenamenti() {

	}

	public void queryGetDettagliAllenamento() {

	}

	public void queryAddGiocatoreAllenamento() {

	}

	public void queryGetMieiAllenamenti() {

	}

	public void queryCancellaUtenteAllenamento() {

	}

	public void queryCancellaAllenamento(int id_all) {

	}

	public void queryGetPreviousAllenamenti(datetime dataora, int idsede) {

	}

	public void queryAllenamentoFinito(int id_all, lista ListaPart) {

	}

}
*/