package com.gebarowski.controller;

import com.gebarowski.model.EmailAccountBean;
import com.gebarowski.model.EmailMessageBean;
import com.gebarowski.model.folder.EmailFolderBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.mail.Folder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Point where all controllers can access the model
 * Reference for selected EmailMessageBean.
 * One email needs to be selected in different windows.
 * It has the reference to selected message bean
 */
public class ModelAccess {


    private Map<String, EmailAccountBean> emailAccounts = new HashMap<String, EmailAccountBean>();
    private ObservableList<String> emailAccountNames = FXCollections.observableArrayList();
    private EmailMessageBean selectedMessage;
    private EmailFolderBean<String> selectedFolder;
    /**
     * In order to have an access to all folders and add listener to them
     */

    private List<Folder> folderList = new ArrayList<Folder>();

    public ObservableList<String> getEmailAccountNames() {
        return emailAccountNames;
    }

    public EmailAccountBean getEmailAccountByName(String name) {

        return emailAccounts.get(name);

    }

    public void addAccount(EmailAccountBean account) {

        emailAccounts.put(account.getEmailAddress(), account);
        emailAccountNames.add(account.getEmailAddress());

    }

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
