package com.gebarowski.controller;

import com.gebarowski.model.EmailMessageBean;
import com.gebarowski.model.SampleData;
import com.gebarowski.model.folder.EmailFolderBean;
import com.gebarowski.model.table.BoldRowFactory;
import com.gebarowski.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class MainController extends AbstractController implements Initializable {


    @FXML
    public TreeView<String> emailFoldersTreeView;
    public TableView<EmailMessageBean> emailTableView;
    private SampleData data = new SampleData();
    private MenuItem showDetails = new MenuItem("Show details");
    @FXML
    private TableColumn<EmailMessageBean, String> subjectCol;
    @FXML
    private TableColumn<EmailMessageBean, String> senderCol;
    @FXML
    private TableColumn<EmailMessageBean, String> sizeCol;
    @FXML
    private WebView messageRenderer;
    @FXML
    private Button button1;
    @FXML
    private Button button2;

    public MainController(ModelAccess modelAccess) {
        super(modelAccess);
    }

    @FXML
    public void changeReadAction() {
        EmailMessageBean message = getModelAccess().getSelectedMessage();

        if (message != null) {
            boolean value = message.isRead();
            message.setRead(!value);
            EmailFolderBean<String> folder = getModelAccess().getSelectedFolder();
            if (folder != null) {
                if (value) {
                    folder.increaseUnreadMessageCounter(1);
                } else
                    folder.decreaseUnreadMessageCounter();
            }
        }

    }

    @Override
    //Called to initialize a controller after its root element has been completely processed
    public void initialize(URL location, ResourceBundle resources) {

        ViewFactory viewFactory = ViewFactory.defaultViewFactory;
        emailTableView.setRowFactory(e -> new BoldRowFactory<>());
        subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("sender"));
        senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("subject"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("size"));

        sizeCol.setComparator(new Comparator<String>() {
            Integer int1, int2;

            @Override
            /**
             * Standard comparator is overridden in order to handle String size
             * format which is expressed in B,KB,MB
             */
            public int compare(String o1, String o2) {
                int1 = EmailMessageBean.formattedValues.get(o1);
                int2 = EmailMessageBean.formattedValues.get(o2);
                return int1.compareTo(int2);
            }
        });


        EmailFolderBean<String> root = new EmailFolderBean<>("");
        emailFoldersTreeView.setRoot(root);
        emailFoldersTreeView.setShowRoot(false);

        EmailFolderBean<String> gebarowski = new EmailFolderBean<>("michal.gebarowski@gmail.com");
        root.getChildren().add(gebarowski);

        //folder structure
        EmailFolderBean<String> Inbox = new EmailFolderBean<>("Inbox", "CompleteInbox");
        EmailFolderBean<String> Sent = new EmailFolderBean<>("Sent", "CompleteInbox");
        Sent.getChildren().add(new EmailFolderBean<>("Subfolder", "SubfolderComplete"));
        Sent.getChildren().add(new EmailFolderBean<>("Subfolder2", "Subfolder2Complete"));
        EmailFolderBean<String> Spam = new EmailFolderBean<>("Spam", "CompleteInbox");

        gebarowski.getChildren().addAll(Inbox, Sent, Spam);
        Inbox.getData().addAll(SampleData.Inbox);
        Sent.getData().addAll(SampleData.Sent);
        Spam.getData().addAll(SampleData.Spam);


        /**
         * triggers actions in tableView based on EmailFolderBean methods
         * model is informed at the end by adding selected folder
         */
        emailFoldersTreeView.setOnMouseClicked(e -> {

            EmailFolderBean<String> item = (EmailFolderBean<String>) emailFoldersTreeView.getSelectionModel().getSelectedItem();
            if (item != null && !item.isTopElement()) {

                emailTableView.setItems(item.getData());
                getModelAccess().setSelectedFolder(item);
                //clear the selected message:
                getModelAccess().setSelectedMessage(null);

            }
        });


        emailTableView.setOnMouseClicked(e -> {

            EmailMessageBean email = emailTableView.getSelectionModel().getSelectedItem();

            if (email != null) {
                getModelAccess().setSelectedMessage(email);
                messageRenderer.getEngine().loadContent(email.getContent());

            }

        });

        showDetails.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setScene(viewFactory.getEMailContextMenuScene());
            stage.show();
        });


        emailTableView.setContextMenu(new ContextMenu(showDetails));

        button2.setOnAction(e -> changeReadAction()
        );
    }
}


//TODO Set icon PART-4
//TODO Add log library PART-4
//TODO Try and catch/ Exceptions