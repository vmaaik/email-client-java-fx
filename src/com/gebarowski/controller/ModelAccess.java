package com.gebarowski.controller;

import com.gebarowski.model.EmailMessageBean;

/**
 * Point where all controllers can access the model
 * Reference for seleted EmailMessageBean.
 * One email needs to be selected in different windows.
 * It has to reference to selected message bean
 */
public class ModelAccess {

    private EmailMessageBean selectedMessage;

    public EmailMessageBean getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(EmailMessageBean selectedMessage) {
        this.selectedMessage = selectedMessage;
    }
}
