package com.gebarowski.controller.services;

import com.gebarowski.model.EmailMessageBean;
import javafx.scene.web.WebEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;

public class MessageRendererService {
    private static final Logger logger = LoggerFactory.getLogger(MessageRendererService.class);

    private EmailMessageBean messageToRender;
    private WebEngine messageRendererEngine;
    private StringBuffer stringBuffer;


    private void renderMessage() {
        //clear the stringBuffer right before start rendering
        stringBuffer.setLength(0);
        Message message = messageToRender.getMessageReference();
        logger.info("Attempting to render message {}", messageToRender.toString());
        try {
            String messageType = message.getContentType();
            if (messageType.contains("TEXT/HTML") ||
                    messageType.contains(("TEXT/PLAIN")) ||
                    messageType.contains("text")) {
                stringBuffer.append(message.getContent().toString());
                logger.info("Message has been rendered. TYPE {}", messageType);
            } else if (messageType.contains("multipart")) {
                Multipart multipart = (Multipart) message.getContent();
                for (int i = multipart.getCount() - 1; i >= 0; i--) {
                    BodyPart bodyPart = multipart.getBodyPart(i);
                    String contentType = bodyPart.getContentType();
                    if (contentType.contains("TEXT/HTML") ||
                            contentType.contains(("TEXT/PLAIN")) ||
                            contentType.contains("text")) {
                        if (stringBuffer.length() == 0) {
                            stringBuffer.append(bodyPart.getParent().toString());
                            logger.info("Message has been rendered. TYPE {}", contentType);
                        }
                        //Attachments handling
                    } else if (contentType.toLowerCase().contains("application")) {
                        MimeBodyPart mimeBodyPart = (MimeBodyPart) bodyPart;

                    } else if (bodyPart.getContentType().contains("multipart")) {
                        Multipart multipart1 = (Multipart) bodyPart.getContent();
                        for (int j = multipart1.getCount() - 1; j >= 0; j--) {
                            BodyPart bodyPart1 = multipart1.getBodyPart(j);
                            if ((bodyPart1.getContentType()).contains("TEXT/HTML") ||
                                    bodyPart1.getContentType().contains("TEXT/PLAIN")) {
                                stringBuffer.append(bodyPart1.getContent().toString());
                                logger.info("Message has been rendered. TYPE {}", bodyPart1.getContentType());
                            }
                        }
                    }
                }
            }


        } catch (Exception e) {


        }


    }

}
