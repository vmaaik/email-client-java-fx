package com.gebarowski.model;

import javafx.collections.ObservableList;

import javax.mail.*;
import java.util.Properties;

public class EmailAccountBean {

    private String emailAddress;
    private String password;
    private Properties properties;

    private Store store;
    private Session session;
    private int loginState = EmailConstants.LOGIN_STATE_NOT_READY;

    public EmailAccountBean(String EmailAddress, String Password) {
        this.emailAddress = EmailAddress;
        this.password = Password;

        /**
         * Configuration for gmail
         */

        properties = new Properties();
        properties.setProperty("mail.store.protocol", "imaps");
        properties.setProperty("mail.transport.protocol", "smtps");
        properties.setProperty("mail.smtps.host", "smtp.gmail.com");
        properties.setProperty("mail.smtps.auth", "true");
        properties.setProperty("incomingHost", "imap.gmail.com");
        properties.setProperty("outgoingHost", "smtp.gmail.com");

        Authenticator auth = new Authenticator() {
            @Override

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailAddress, password);

            }

        };


        //Connecting
        session = Session.getInstance(properties, auth);
        System.out.println("Session created!");
        try {
            //get info from the session
            this.store = session.getStore();
            System.out.println("store");
            store.connect(properties.getProperty("incomingHost"), emailAddress, password);

            System.out.println("EmailAccountBean constructed successfully");
            loginState = EmailConstants.LOGIN_STATE_SUCCEDED;

        } catch (Exception e) {
            e.printStackTrace();
            loginState = EmailConstants.LOGIN_STATE_FAILED_BY_CREDENTIALS;
            System.out.println("EmailAccountBean failed");
        }
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Properties getProperties() {
        return properties;
    }

    public Store getStore() {
        return store;
    }

    public Session getSession() {
        return session;
    }

    public int getLoginState() {
        return loginState;
    }

    public void addEmailsToData(ObservableList<EmailMessageBean> data) {
        try {

            System.out.println("fetching emails thread: "+Thread.currentThread().getName());
            Folder folder = store.getFolder("INBOX");

            System.out.println("Inbox folder has been open");
            folder.open(Folder.READ_ONLY);
            for (int i = folder.getMessageCount(); i > 0; i--) {
                Message message = folder.getMessage(i);
                EmailMessageBean messageBean = new EmailMessageBean(message.getSubject(), message.getFrom()[0].toString(),
                        message.getSize(), "", message.getFlags().contains(Flags.Flag.SEEN));
                System.out.println("Message " + messageBean + " has been fetched!");
                data.add(messageBean);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
