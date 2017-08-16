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

    /**
     * NUMBER_OF_FETCHFOLDERSSERVICES_ACTIVE:
     * Once new service is started this variable will be incremented. This variable is used in order to inform
     * FetchUpdaterService that all folders have been already fetched and updater can start its work.
     */
    private static int NUMBER_OF_FETCHFOLDERSSERVICES_ACTIVE = 0;
    final Logger logger = LoggerFactory.getLogger(FetchFoldersService.class);
    private EmailFolderBean<String> foldersRoot;
    private EmailAccountBean emailAccountBean;
    private ModelAccess modelAccess;


    public FetchFoldersService(EmailFolderBean<String> foldersRoot, EmailAccountBean emailAccountBean, ModelAccess modelAccess) {
        this.foldersRoot = foldersRoot;
        this.emailAccountBean = emailAccountBean;
        this.modelAccess = modelAccess;
        // when thread is succeeded decrement active thread
        this.setOnSucceeded(e -> {
            NUMBER_OF_FETCHFOLDERSSERVICES_ACTIVE --;
        });

    }


    /**
     * Fetches all folders from gmail mailbox
     * Task is used in CreateAndRegisterEmailAccountService
     *
     * @return
     */
    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                NUMBER_OF_FETCHFOLDERSSERVICES_ACTIVE++;

                if (emailAccountBean != null) {

                    Folder[] folders = emailAccountBean.getStore().getDefaultFolder().list();
                    for (Folder folder : folders) {
                        modelAccess.addFolder(folder);
                        EmailFolderBean<String> item = new EmailFolderBean<String>(folder.getName(), folder.getFullName());
                        foldersRoot.getChildren().add(item);
                        item.setExpanded(true);
                        addMessageListenerToFolder(folder, item);
                        logger.info("Folder: {} has been added to the tree view", folder.getName());
                        FetchMessagesService fetchMessagesService = new FetchMessagesService(item, folder);
                        fetchMessagesService.start();
                        Folder[] subFolders = folder.list();
                        for (Folder subFolder : subFolders) {
                            modelAccess.addFolder(subFolder);
                            EmailFolderBean<String> subitem = new EmailFolderBean<String>(subFolder.getName(), subFolder.getFullName());
                            item.getChildren().add(subitem);
                            addMessageListenerToFolder(subFolder, subitem);
                            logger.info("Subfolder: {} has been added to the tree view. Full name: {}.", subFolder.getName(), subFolder.getFullName());
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
     * @param folder
     * @param item
     */
    private void addMessageListenerToFolder(Folder folder, EmailFolderBean<String> item) {
        folder.addMessageCountListener(new MessageCountAdapter() {
            @Override
            //MessageCountEvent e is litening new msgs on imap
            // getMessageCount() Get total number of messages in this Folder.
            public void messagesAdded(MessageCountEvent e) {
                for (int i = 0; i < e.getMessages().length; i++) {

                    try {
                        logger.info("iteracja {}", i);
                        logger.info("e.getMessages().length {} FOLDER NAME {}", e.getMessages().length, folder.getName());

                        Message currentMessage = folder.getMessage(folder.getMessageCount() - i);
                        item.addEmail(1, currentMessage);
                    } catch (MessagingException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

    }

    public static boolean noServicesActive(){
        /**
         * return true when no service is active
         */

        return NUMBER_OF_FETCHFOLDERSSERVICES_ACTIVE ==0;
    }

}
