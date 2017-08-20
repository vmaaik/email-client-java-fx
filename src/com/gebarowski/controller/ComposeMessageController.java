package com.gebarowski.controller;

import com.gebarowski.controller.services.EmailSenderService;
import com.gebarowski.model.EmailConstants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ComposeMessageController extends AbstractController implements Initializable {


    @FXML
    private Label attachmentsLabel;
    @FXML
    private ChoiceBox<String> senderChoice;
    @FXML
    private TextField subjectTextField;
    @FXML
    private TextField recipientTextField;
    @FXML
    private Label errorLabel;

    @FXML
    private HTMLEditor htmlEditor;

    private List<File> attachements = new ArrayList<File>();

    public ComposeMessageController(ModelAccess modelAccess) {
        super(modelAccess);
    }


    @FXML
    void attachBtnAction() {

        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {

            attachements.add(selectedFile);
            attachmentsLabel.setText(attachmentsLabel.getText() + selectedFile.getName() + "; ");

        }

    }

    @FXML
    void sendBtnAction() {

        errorLabel.setText("");
        EmailSenderService emailSenderService = new
                EmailSenderService(getModelAccess().getEmailAccountByName(senderChoice.getValue()), subjectTextField.getText(), recipientTextField.getText(), htmlEditor.getHtmlText(), attachements);
        emailSenderService.restart();
        emailSenderService.setOnSucceeded(e -> {
            if (emailSenderService.getValue() == EmailConstants.MESSAGE_SENT_SUCCESSFULLY) {

                errorLabel.setText("Message sent successfully");
            } else {
                errorLabel.setText("Error!");
            }
        });


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        senderChoice.setItems(getModelAccess().getEmailAccountNames());
        senderChoice.setValue(getModelAccess().getEmailAccountNames().get(0));

    }
}
