package com.gebarowski.controller.services;

import com.gebarowski.model.EmailAccountBean;
import com.gebarowski.model.EmailConstants;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EmailSenderService extends Service<Integer> {

    private int result;
    private EmailAccountBean emailAccountBean;
    private String subject;
    private String recipent;
    private String content;

    private List<File> attachments = new ArrayList<File>();

    public EmailSenderService(EmailAccountBean emailAccountBean, String subject, String recipent, String content, List<File> attachments) {
        this.emailAccountBean = emailAccountBean;
        this.subject = subject;
        this.recipent = recipent;
        this.content = content;
        this.attachments = attachments;
    }

    @Override

    protected Task<Integer> createTask() {
        return new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                try {

                    //Setting up the sesion
                    Session session = emailAccountBean.getSession();
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(emailAccountBean.getEmailAddress());
                    message.addRecipients(Message.RecipientType.TO, recipent);

                    message.setSubject(subject);
                    //Adding content to multipart
                    Multipart multipart = new MimeMultipart();
                    BodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setContent(content, "text/html");
                    multipart.addBodyPart(messageBodyPart);


                    //Adding attachments
                    if (attachments.size() > 0) {
                        for (File file : attachments) {
                            MimeBodyPart messageBodyPartAttach = new MimeBodyPart();
                            DataSource source = new FileDataSource(file.getAbsolutePath());
                            messageBodyPartAttach.setDataHandler(new DataHandler(source));
                            messageBodyPartAttach.setFileName(file.getName());
                            multipart.addBodyPart(messageBodyPart);

                        }
                    }
                    //Adding content to message
                    message.setContent(multipart);

                    //Sending the message
                    Transport transport = session.getTransport();
                    transport.connect(emailAccountBean.getProperties().getProperty("outgoingHost"),
                            emailAccountBean.getEmailAddress(),
                            emailAccountBean.getPassword()); //!!
                    transport.sendMessage(message, message.getAllRecipients());
                    transport.close();
                    result = EmailConstants.MESSAGE_SENT_SUCCESSFULLY;
                } catch (Exception e) {
                    result = EmailConstants.MESSAGE_SENT_FAILURE;
                    e.printStackTrace();
                }
                return result;
            }
        };
    }
}
