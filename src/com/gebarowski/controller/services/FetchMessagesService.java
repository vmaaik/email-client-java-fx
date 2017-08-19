package com.gebarowski.controller.services;

import com.gebarowski.model.folder.EmailFolderBean;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Folder;
import javax.mail.Message;

public class FetchMessagesService extends Service<Void> {
    private static final Logger logger = LoggerFactory.getLogger(FetchMessagesService.class.getName());
    private EmailFolderBean<String> emailFolder;
    private Folder folder;

    public FetchMessagesService(EmailFolderBean<String> emailFolder, Folder folder) {
        this.emailFolder = emailFolder;
        this.folder = folder;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if(folder.getType() != Folder.HOLDS_FOLDERS){
                    folder.open(Folder.READ_ONLY);
                }
                int folderSize = folder.getMessageCount();
                for(int i = folderSize; i>0; i--){
                    Message currentMessage = folder.getMessage(i);
                    emailFolder.addEmail(-1, currentMessage);
                    logger.info("Email FROM {} with SUBJECT {} , SENT {} has been added to the FOLDER {} ", currentMessage.getFrom(), currentMessage.getSubject(), currentMessage.getSentDate(), folder.getName() );
                }

                return null;
            }
        };
    }
}
