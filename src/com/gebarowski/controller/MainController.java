package com.gebarowski.controller;

import com.gebarowski.model.EmailMessageBean;
import com.gebarowski.model.SampleData;
import com.gebarowski.model.Singleton;
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

public class MainController implements Initializable {

    //TODO Set icon PART-4
    //TODO Add log library PART-4

    @FXML
    public TableView<EmailMessageBean> emailTableView;
    @FXML
    public TreeView<String> emailFoldersTreeView;
    @FXML
    public TreeItem<String> root = new TreeItem<String>();
    @FXML
    private Button button1;
    @FXML
    private TableColumn<EmailMessageBean, String> subjectCol;
    @FXML
    private TableColumn<EmailMessageBean, String> senderCol;
    @FXML
    private TableColumn<EmailMessageBean, String> sizeCol;
    @FXML
    WebView messageRenderer;

    SampleData data = new SampleData();
    MenuItem showDetails = new MenuItem("Show details");
    private Singleton singleton;


    @Override
    //Called to initialize a controller after its root element has been completely processed
    public void initialize(URL location, ResourceBundle resources) {
        ViewFactory viewFactory = new ViewFactory();

        singleton = Singleton.getInstance();

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

        TreeItem<String> inbox = new TreeItem<String>("Inbox");
        TreeItem<String> sent = new TreeItem<String>("Sent");
        TreeItem<String> spam = new TreeItem<String>("Spam");
        TreeItem<String> trash = new TreeItem<String>("Trash");
        emailFoldersTreeView.setRoot(root);


        emailFoldersTreeView.setOnMouseClicked(e ->

                /**
                 * triggers actions in tableView based on item value and MAP in sampleData
                 */
        {
            TreeItem<String> item = emailFoldersTreeView.getSelectionModel().getSelectedItem();
            if (item != null) {

                emailTableView.setItems(data.emailFolders.get(item.getValue()));

            }
        });


        emailTableView.setOnMouseClicked(e -> {

            EmailMessageBean email = emailTableView.getSelectionModel().getSelectedItem();

            if (email != null) {

                messageRenderer.getEngine().loadContent(email.getContent());
                singleton.setMessage(email);
            }

        });

        showDetails.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setScene(viewFactory.getEMailContextMenuScene());
            stage.show();
        });


        root.setValue("test@gmail.com");
        emailTableView.setContextMenu(new ContextMenu(showDetails));
        root.getChildren().addAll(inbox, sent, spam, trash);
        root.setExpanded(true);

    }


}