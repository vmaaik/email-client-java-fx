package com.gebarowski;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class EmailContextMenuController implements Initializable{

    @FXML
    public WebView menuMessageWebView;
    @FXML
    public Label subjectLabel;
    @FXML
    public Label senderLabel;

    private Singleton singleton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        singleton = Singleton.getInstance();
       subjectLabel.setText("Subject: " + singleton.getMessage().getSubject());
       senderLabel.setText("Sender: "+ singleton.getMessage().getSender());
       menuMessageWebView.getEngine().loadContent(singleton.getMessage().getContent());





    }
}
