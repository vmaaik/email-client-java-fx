package com.gebarowski.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ComposeMessageController extends AbstractController implements Initializable {


    @FXML
    private Label attachmentsLabel;
    @FXML
    private ChoiceBox<?> senderChoice;
    @FXML
    private TextField subjectTextField;
    @FXML
    private TextField recipientTextField;
    @FXML
    private Label errorLabel;

    public ComposeMessageController(ModelAccess modelAccess) {
        super(modelAccess);
    }

    @FXML
    void attachBtnAction() {

    }

    @FXML
    void sendBtnAction() {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
