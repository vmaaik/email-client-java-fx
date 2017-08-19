package com.gebarowski.controller.services;

import com.gebarowski.model.EmailMessageBean;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;

public class SaveAttachmentService extends Service<Void> {

    private static final Logger logger = LoggerFactory.getLogger(SaveAttachmentService.class);
    private String LOCATION_OF_DOWNLOADS = System.getProperty("user.home") + "/Downloads/";

    private EmailMessageBean messageToDownload;
    private ProgressBar progress;
    private Label label;


    public SaveAttachmentService(ProgressBar progress, Label label) {
        this.progress = progress;
        this.label = label;
        this.setOnRunning(e -> {
            showDownloadProgress(true);
        });
        this.setOnSucceeded(e -> {
            showDownloadProgress(false);
        });
        logger.info("SaveAttachmentService has been created." );
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                try {
                    for (MimeBodyPart mimeBodyPart : messageToDownload.getAttachmentsList()) {
                        updateProgress(messageToDownload.getAttachmentsList().indexOf(mimeBodyPart),messageToDownload.getAttachmentsList().size() );
                        mimeBodyPart.saveFile(LOCATION_OF_DOWNLOADS + mimeBodyPart.getFileName());
                        logger.info("File {} has been saved", mimeBodyPart.getFileName());
                        logger.info("LOCATION {}", LOCATION_OF_DOWNLOADS);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

//                messageToDownload.getAttachmentsList().stream()
//                        .forEach(a -> {
//                            try {
//                                a.saveFile(LOCATION_OF_DOWNLOADS + a.getFileName());
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            } catch (MessagingException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                );


                return null;
            }
        };
    }

    //always call before starting
    public void setMessageToDownload(EmailMessageBean messageToDownload) {
        this.messageToDownload = messageToDownload;
    }

    private void showDownloadProgress(boolean show) {
        progress.setVisible(show);
        label.setVisible(show);
    }


}


