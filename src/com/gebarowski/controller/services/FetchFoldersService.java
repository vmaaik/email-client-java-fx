package com.gebarowski.controller.services;

import com.gebarowski.model.EmailAccountBean;
import com.gebarowski.model.folder.EmailFolderBean;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Folder;

public class FetchFoldersService extends Service<Void> {

    private EmailFolderBean<String> foldersRoot;
    private EmailAccountBean emailAccountBean;


    public FetchFoldersService(EmailFolderBean<String> foldersRoot, EmailAccountBean emailAccountBean) {
        this.foldersRoot = foldersRoot;
        this.emailAccountBean = emailAccountBean;
    }

    /**
     * Fetches all folders from gmail mailbox
     * Task is used in CreateAndRegisterEmailAccountService
     * @return
     */
    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                if (emailAccountBean != null) {

                    Folder[] folders = emailAccountBean.getStore().getDefaultFolder().list();
                    for (Folder folder : folders) {

                        EmailFolderBean<String> item = new EmailFolderBean<String>(folder.getName(), folder.getFullName());
                        foldersRoot.getChildren().add(item);
                        item.setExpanded(true);
                        System.out.println("Folder added " + folder.getName());
                        Folder[] subFolders = folder.list();
                        for (Folder subFolder : subFolders) {
                            EmailFolderBean<String> subitem = new EmailFolderBean<String>(subFolder.getName(), subFolder.getFullName());
                            item.getChildren().add(subitem);
                            System.out.println("SubFolder added " + subFolder.getName());
                        }
                    }
                }
                return null;
            }
        };
    }
}
