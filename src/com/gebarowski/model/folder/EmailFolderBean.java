package com.gebarowski.model.folder;

import com.gebarowski.model.EmailMessageBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 * New type of TreeItem which holds information about messages
 * ie. unread message, counter, name etc.
 */
public class EmailFolderBean<T> extends TreeItem<String> {

    // indicates email folder top element
    private boolean topElement = false;
    private int unreadMessageCount;
    //name of email folder bean
    private String name;
    private String completeName;
    // email message beans data
    private ObservableList<EmailMessageBean> data = FXCollections.observableArrayList();

    /**
     * Constructor for items that are top elements
     * This elements do not contain any data
     *
     * @param value name of top element ie. michal.gebarowski@gmail.com
     */
    public EmailFolderBean(String value) {
        super(value);
        //TODO call the super here and set up icon for the folder (PART-4)
        this.name = value;
        this.completeName = value;
        this.data = null;
        this.topElement = true;
        this.setExpanded(true);
    }

    /**
     * Constructor for the non-top-element item
     *
     * @param value        it is the name of the particular folder ie. Inbox, Sent, Trash ect.
     * @param completeName
     */
    public EmailFolderBean(String value, String completeName) {
        super(value);
        //TODO call the super here and set up icon for the folder (PART-4)
        this.name = value;
        this.completeName = completeName;
    }

    /**
     * Update value (name) of treeItem.
     * In this case name + unread messages ie. Inbox (6)
     * This method is called everytime when messages are increasing q
     */
    private void updateValue() {

        if (unreadMessageCount > 0) {
            this.setValue((String) (name + "(" + unreadMessageCount + ")"));
        } else {
            this.setValue(name);
        }
    }

    public void increaseUnreadMessageCounter(int newMessages) {
        unreadMessageCount = unreadMessageCount + newMessages;
        updateValue();
    }

    public void decreaseUnreadMessageCounter() {
        unreadMessageCount--;
        updateValue();
    }

    /**
     *
     * @param email
     */
    public void addEmail(EmailMessageBean email) {
        data.add(email);
        if (!email.isRead()) {
            increaseUnreadMessageCounter(1);
        }
    }

    public boolean isTopElement() {
        return topElement;
    }

    public ObservableList<EmailMessageBean> getData() {
        return data;
    }
}
