package com.gebarowski.controller;

import com.gebarowski.model.EmailMessageBean;
import com.gebarowski.model.folder.EmailFolderBean;

import javax.mail.Folder;
import java.util.ArrayList;
import java.util.List;

/**
 * Point where all controllers can access the model
 * Reference for selected EmailMessageBean.
 * One email needs to be selected in different windows.
 * It has the reference to selected message bean
 */
public class ModelAccess {

    private EmailMessageBean selectedMessage;
    private EmailFolderBean<String> selectedFolder;

    /**
     * In order to have an access to all folders and add listener to them
     */

    private List<Folder> folderList = new ArrayList<Folder>();


    public void addFolder(Folder folder) {
        folderList.add(folder);
    }

    public EmailMessageBean getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(EmailMessageBean selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    /**
     * @return
     */
    public EmailFolderBean<String> getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(EmailFolderBean<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    public List<Folder> getFolderList() {
        return folderList;
    }

}
