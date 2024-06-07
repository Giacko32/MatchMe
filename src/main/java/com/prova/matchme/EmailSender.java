package com.prova.matchme;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;


public class EmailSender {

    public static void sendEmail(String toAddress, String message) {
        try {
            // Imposta le proprietà SMTP
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            // Disabilita la verifica del certificato
            properties.put("mail.smtp.ssl.trust", "*");

            // Crea un'autenticazione per il server SMTP
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("nonrisponderematchme@gmail.com", "kagi xufi xlsk zdgl");
                }
            };

            // Crea una nuova sessione con l'autenticazione
            Session session = Session.getInstance(properties, auth);

            // Crea un nuovo messaggio email
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("nonrisponderematchme@gmail.com"));
            InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject("nonrisponderematchme@gmail.com");
            msg.setSentDate(new java.util.Date());
            msg.setText("Ciao, abbiamo notato che hai smarrito la tua password\necco il tuo codice temporaneo: "+message);
            // Invia il messaggio email
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}

