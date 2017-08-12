package com.gebarowski.model;

import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import java.util.Properties;

public class EmailAccountBean {

    final Logger logger = LoggerFactory.getLogger(EmailAccountBean.class);

    private String emailAddress;
    private String password;
    private Properties properties;

    private Store store;
    private Session session;
    private int loginState = EmailConstants.LOGIN_STATE_NOT_READY;

    public EmailAccountBean(String EmailAddress, String Password) {
        this.emailAddress = EmailAddress;
        this.password = Password;
        //Configuration for gmail
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

        // Connecting with imap
        session = Session.getInstance(properties, auth);
        logger.info("Session created");
        try {
            //get info from the session
            this.store = session.getStore();
            logger.info("Store taken from the session");
            store.connect(properties.getProperty("incomingHost"), emailAddress, password);
            logger.info("Email account {}. has been successfully connected with {}.", emailAddress, properties.getProperty("incomingHost"));
            loginState = EmailConstants.LOGIN_STATE_SUCCEDED;

        } catch (Exception e) {
            e.printStackTrace();
            loginState = EmailConstants.LOGIN_STATE_FAILED_BY_CREDENTIALS;
            logger.error(" Connection {}. with {}. FAILED ", emailAddress, properties.getProperty("incomingHost"));
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

}
