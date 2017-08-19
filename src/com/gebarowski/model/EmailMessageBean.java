package com.gebarowski.model;

import com.gebarowski.model.table.AbstractTableItem;
import javafx.beans.property.SimpleStringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailMessageBean extends AbstractTableItem {

    public static Map<String, Integer> formattedValues = new HashMap<String, Integer>();
    final Logger logger = LoggerFactory.getLogger(EmailMessageBean.class);
    private SimpleStringProperty sender;
    private SimpleStringProperty subject;
    private SimpleStringProperty size;
    private Message messageReference;
    //attachments list
    private List<MimeBodyPart> attachmentsList = new ArrayList<MimeBodyPart>();
    private StringBuffer attachemntsNames = new StringBuffer();

    public EmailMessageBean(String Subject, String Sender, int size, boolean isRead, Message messageReference) {
        super(isRead);
        this.sender = new SimpleStringProperty(Sender);
        this.subject = new SimpleStringProperty(Subject);
        this.size = new SimpleStringProperty(formatSize(size));
        this.messageReference = messageReference;
        logger.info("Email {}. has been created", toString());


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

    public String getSize() {

        return size.get();
    }


    private String formatSize(int size) {
        /**
         * Method converts size of email adding suffix, accordingly: B,KB,MB.
         * @param size it's size of an email
         * @variable formattedValues Map links String with Integer in column size's comparator.
         * @return formatted value of size with suffix
         */

        String returnValue;

        if (size < 1024) {
            returnValue = size + " B";
        } else if (size < 1048576) {
            returnValue = size / 1025 + " KB";
        } else {
            returnValue = size / 1048576 + " MB";
        }

        formattedValues.put(returnValue, size);
        return returnValue;


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

    public boolean hasAttachments(){

        return attachmentsList.size() > 0;
    }

    public void clearAttachmentList(){
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
