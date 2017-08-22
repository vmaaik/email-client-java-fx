package com.gebarowski.model;

import com.gebarowski.model.table.AbstractTableItem;
import com.gebarowski.model.table.FormatableInteger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmailMessageBean extends AbstractTableItem {

//    final Logger logger = LoggerFactory.getLogger(EmailMessageBean.class);
    private SimpleStringProperty sender;
    private SimpleStringProperty subject;
    private SimpleObjectProperty<FormatableInteger> size;
    private Message messageReference;
    private SimpleObjectProperty<Date> date;
    //attachments list
    private List<MimeBodyPart> attachmentsList = new ArrayList<MimeBodyPart>();
    private StringBuffer attachemntsNames = new StringBuffer();


    public EmailMessageBean(String Subject, String Sender, int size, boolean isRead, Date date, Message messageReference) {
        super(isRead);
        this.sender = new SimpleStringProperty(Sender);
        this.subject = new SimpleStringProperty(Subject);
        this.size = new SimpleObjectProperty<FormatableInteger>(new FormatableInteger(size));
        this.messageReference = messageReference;
        this.date = new SimpleObjectProperty<Date>(date);

    }

    public Date getDate() {
        return date.get();
    }

    public SimpleObjectProperty<Date> dateProperty() {
        return date;
    }

    public List<MimeBodyPart> getAttachmentsList() {
        return attachmentsList;
    }

    public String getAttachemntsNames() {
        return attachemntsNames.toString();
    }

    public String getSender() {

        return sender.get();
    }

    public String getSubject() {

        return subject.get();
    }

    public FormatableInteger getSize() {

        return size.get();
    }


    public Message getMessageReference() {
        return messageReference;
    }

    public void addAttachment(MimeBodyPart mimeBodyPart) {
        attachmentsList.add(mimeBodyPart);
        try {
            attachemntsNames.append(mimeBodyPart.getFileName() + "; ");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public boolean hasAttachments() {

        return attachmentsList.size() > 0;
    }

    public void clearAttachmentList() {
        /**
         *  Clear attachmentList and attachmentNames in order to
         *  prevent from adding the same files several times once
         *  email is clicked.
         */
        attachmentsList.clear();
        attachemntsNames.setLength(0);
    }

    @Override
    public String toString() {
        return "EmailMessageBean{" +
                "sender=" + sender +
                ", subject=" + subject +
                ", size=" + size +
                ", messageReference=" + messageReference +
                '}';
    }
}
