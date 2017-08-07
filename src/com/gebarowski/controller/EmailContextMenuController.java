package com.gebarowski.controller;

import com.gebarowski.model.EmailMessageBean;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class EmailContextMenuController extends AbstractController implements Initializable {

    public EmailContextMenuController(ModelAccess modelAccess) {
        super(modelAccess);
    }

    @FXML
    public WebView menuMessageWebView;
    @FXML
    public Label subjectLabel;
    @FXML
    public Label senderLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        EmailMessageBean selectedMessage = getModelAccess().getSelectedMessage();
        subjectLabel.setText("Subject: " + selectedMessage.getSubject());
        senderLabel.setText("Sender: " + selectedMessage.getSender());
        menuMessageWebView.getEngine().loadContent(selectedMessage.getContent());


    }
}
