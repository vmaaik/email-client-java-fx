package com.gebarowski.controller.services;

import com.gebarowski.model.EmailMessageBean;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;

public class MessageRendererService extends Service<Void> {
    private static final Logger logger = LoggerFactory.getLogger(MessageRendererService.class);

    private EmailMessageBean messageToRender;
    private WebEngine messageRendererEngine;
    private StringBuffer stringBuffer = new StringBuffer();

    public MessageRendererService(WebEngine messageRendererEngine) {
        this.messageRendererEngine = messageRendererEngine;
        // Invoked in MainJavaFx Thread
        this.setOnSucceeded(e -> showMessage());
    }


    public void setMessageToRender(EmailMessageBean messageToRender) {
        this.messageToRender = messageToRender;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                renderMessage();
                return null;
            }
        };
    }

    private void renderMessage() {
        //clear the stringBuffer right before start rendering
        stringBuffer.setLength(0);
        messageToRender.clearAttachmentList();
        Message message = messageToRender.getMessageReference();
        logger.info("Attempting to render message {}", messageToRender.toString());
        try {

            //check if the content is plain text or HTML
            String messageType = message.getContentType();
            if (messageType.contains("TEXT/HTML") ||
                    messageType.contains(("TEXT/PLAIN")) ||
                    messageType.contains("text")) {
                stringBuffer.append(message.getContent().toString());
                logger.info("Message has been rendered. No multipart, no attachments.  TYPE {}", messageType);

                //check if the content is a nested message
            } else if (messageType.contains("multipart")) {
                logger.info("Attepmting to render message which contains MULTIPART");
                Multipart multipart = (Multipart) message.getContent();
                for (int i = multipart.getCount() - 1; i >= 0; i--) {
                    BodyPart bodyPart = multipart.getBodyPart(i);
                    String contentType = bodyPart.getContentType();

                    //check if the part of nested message is plain text or HTML
                    if (contentType.contains("TEXT/HTML") ||
                            contentType.contains(("TEXT/PLAIN")) ||
                            contentType.contains("text")) {
                        if (stringBuffer.length() == 0) {
                            stringBuffer.append(bodyPart.getContent().toString());
                            logger.info("Message has been rendered. Multipart TYPE {}", contentType);
                        }
                        /** Check if the content has attachment
                         *  MIME It is not a mail transfer protocol.
                         *  Instead, it defines the content of what is transferred:
                         *  the format of the messages, attachments, and so on.
                         */
                    } else if (contentType.toLowerCase().contains("application")) {
                        logger.info("Attepmting to render message which contains attachments");
                        MimeBodyPart mimeBodyPart = (MimeBodyPart) bodyPart;
                        messageToRender.addAttachment(mimeBodyPart);
                        logger.info("Attachment {} has been added to the attachment list.", mimeBodyPart.getFileName());
                    } else if (bodyPart.getContentType().contains("multipart")) {
                        logger.info("Attepmting to render nested message which contains MULTIPARTS");
                        Multipart multipart1 = (Multipart) bodyPart.getContent();
                        for (int j = multipart1.getCount() - 1; j >= 0; j--) {
                            BodyPart bodyPart1 = multipart1.getBodyPart(j);
                            if ((bodyPart1.getContentType()).contains("TEXT/HTML") ||
                                    bodyPart1.getContentType().contains("TEXT/PLAIN")) {
                                stringBuffer.append(bodyPart1.getContent().toString());
                                logger.info("Message has been rendered. Nested multipart TYPE {}", bodyPart1.getContentType());
                            }
                        }
                    }
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Rendering message {} FAILED", messageToRender.toString());
        }
    }

    /**
     * This fragment of code was previously placed in renderMessage() method. Now is
     * divided in order to use renderMessage() in different thread than JavaFx Main Thread.
     * Rendering big emails in Main JavaFx Thread frozen the application.
     * Once succeeded this method is called.
     * Handles the info about attachments
     */
    private void showMessage() {
        messageRendererEngine.loadContent(stringBuffer.toString());
        logger.info("Message has been shown in WebView");

    }

}
