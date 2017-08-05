package com.gebarowski.model;

import javafx.beans.property.SimpleStringProperty;

import java.util.HashMap;
import java.util.Map;

public class EmailMessageBean {

    /**
     *
     */


    public static Map<String, Integer> formattedValues = new HashMap<String, Integer>();
    private SimpleStringProperty sender;
    private SimpleStringProperty subject;
    private SimpleStringProperty size;
    private String content;

    public EmailMessageBean(String Subject, String Sender, int size, String Content) {

        this.sender = new SimpleStringProperty(Sender);
        this.subject = new SimpleStringProperty(Subject);
        this.size = new SimpleStringProperty(formatSize(size));
        this.content = Content;

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

    public String getContent(){

        return content;
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


}
