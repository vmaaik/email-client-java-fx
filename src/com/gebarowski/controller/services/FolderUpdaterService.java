package com.gebarowski.controller.services;


import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Folder;
import java.util.List;

/**
 * Service which is listening to new messages. Starts and never end.
 */
public class FolderUpdaterService extends Service<Void> {

    final Logger logger = LoggerFactory.getLogger(FolderUpdaterService.class);
    private List<Folder> folderList;

    public FolderUpdaterService(List<Folder> folderList) {
        this.folderList = folderList;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (; ; ) {
                    try {
                        Thread.sleep(10000);
                        //check whether any fetch folder service is running, if not starts checking new messages
                        if (FetchFoldersService.noServicesActive()) {
                            for (Folder folder : folderList) {
                                if (folder.getType() != Folder.HOLDS_FOLDERS && folder.isOpen()) {
                                    folder.getMessageCount();
                                    logger.info("Get Message Count {} in folder {} ", folder.getMessageCount(), folder.getName());
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        };
    }
}
