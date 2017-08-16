package com.gebarowski.controller.services;

import com.gebarowski.controller.ModelAccess;
import com.gebarowski.model.EmailAccountBean;
import com.gebarowski.model.folder.EmailFolderBean;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;

public class FetchFoldersService extends Service<Void> {

    final Logger logger = LoggerFactory.getLogger(FetchFoldersService.class);

    private EmailFolderBean<String> foldersRoot;
    private EmailAccountBean emailAccountBean;
    //In order to use folder methods hold in ModelAccess
    private ModelAccess modelAccess;


    public FetchFoldersService(EmailFolderBean<String> foldersRoot, EmailAccountBean emailAccountBean, ModelAccess modelAccess) {
        this.foldersRoot = foldersRoot;
        this.emailAccountBean = emailAccountBean;
        this.modelAccess = modelAccess;

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
                        modelAccess.addFolder(folder);
                        EmailFolderBean<String> item = new EmailFolderBean<String>(folder.getName(), folder.getFullName());
                        foldersRoot.getChildren().add(item);
                        item.setExpanded(true);
                        addMessageListenerToFolder(folder,item);
                        logger.info("Folder: {} has been added to the tree view", folder.getName());
                        FetchMessagesService fetchMessagesService = new FetchMessagesService(item, folder);
                        fetchMessagesService.start();
                        Folder[] subFolders = folder.list();
                        for (Folder subFolder : subFolders) {
                            modelAccess.addFolder(subFolder);
                            EmailFolderBean<String> subitem = new EmailFolderBean<String>(subFolder.getName(), subFolder.getFullName());
                            item.getChildren().add(subitem);
                            addMessageListenerToFolder(subFolder,subitem);
                            logger.info("Subfolder: {} has been added to the tree view. Full name: {}.", subFolder.getName(),subFolder.getFullName());
                            FetchMessagesService fetchMessagesSubfolderService = new FetchMessagesService(item, subFolder);
                            fetchMessagesSubfolderService.start();
                        }
                    }
                }
                return null;
            }
        };
    }

    /**
     *
     * @param folder
     * @param item
     */
    private void addMessageListenerToFolder(Folder folder, EmailFolderBean<String> item) {
        folder.addMessageCountListener(new MessageCountAdapter() {
            @Override
            //MessageCountEvent e is litening new msgs on imap
            public void messagesAdded(MessageCountEvent e) {
                for(int i = 0; i< e.getMessages().length; i++){

                    try {
                        Message currentMessage = folder.getMessage(folder.getMessageCount() - i);
                        item.addEmail(1, currentMessage);
                    } catch (MessagingException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

    }
}
