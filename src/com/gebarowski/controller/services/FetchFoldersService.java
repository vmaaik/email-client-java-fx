package com.gebarowski.controller.services;

import com.gebarowski.model.EmailAccountBean;
import com.gebarowski.model.folder.EmailFolderBean;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.mail.Folder;

public class FetchFoldersService extends Service<Void> {

    final Logger logger = LoggerFactory.getLogger(FetchFoldersService.class);

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
                        logger.info("Folder: {} has been added to the tree view", folder.getName());
                        Folder[] subFolders = folder.list();
                        for (Folder subFolder : subFolders) {
                            EmailFolderBean<String> subitem = new EmailFolderBean<String>(subFolder.getName(), subFolder.getFullName());
                            item.getChildren().add(subitem);
                            logger.info("Subfolder: {} has been added to the tree view. Full name: {}.", subFolder.getName(),subFolder.getFullName());
                        }
                    }
                }
                return null;
            }
        };
    }
}
