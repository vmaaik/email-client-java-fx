package com.gebarowski.controller;

import com.gebarowski.model.EmailMessageBean;
import com.gebarowski.model.folder.EmailFolderBean;

/**
 * Point where all controllers can access the model
 * Reference for selected EmailMessageBean.
 * One email needs to be selected in different windows.
 * It has the reference to selected message bean
 */
public class ModelAccess {

    private EmailMessageBean selectedMessage;

    public EmailMessageBean getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(EmailMessageBean selectedMessage) {
        this.selectedMessage = selectedMessage;
    }


    /**
     *
     * @return
     */
    public EmailFolderBean<String> getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(EmailFolderBean<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    private EmailFolderBean<String> selectedFolder;
}
