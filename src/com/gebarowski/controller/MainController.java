package com.gebarowski.controller;

import com.gebarowski.controller.services.CreateAndRegisterEmailAccountService;
import com.gebarowski.controller.services.FolderUpdaterService;
import com.gebarowski.controller.services.MessageRendererService;
import com.gebarowski.controller.services.SaveAttachmentService;
import com.gebarowski.model.EmailMessageBean;
import com.gebarowski.model.folder.EmailFolderBean;
import com.gebarowski.model.table.BoldRowFactory;
import com.gebarowski.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController extends AbstractController implements Initializable {


    @FXML
    public TreeView<String> emailFoldersTreeView;
    public TableView<EmailMessageBean> emailTableView;

    private MenuItem showDetails = new MenuItem("Show details");
    @FXML
    private TableColumn<EmailMessageBean, String> subjectCol;
    @FXML
    private TableColumn<EmailMessageBean, String> senderCol;
    @FXML
    private TableColumn<EmailMessageBean, String> sizeCol;
    @FXML
    private TableColumn<EmailMessageBean, Date> dateCol;
    @FXML
    private WebView messageRenderer;
    @FXML
    private Button downAttachButton;
    @FXML
    private Button newMessageBtn;
    @FXML
    private MessageRendererService messageRendererService;
    @FXML
    private Label downAttachLabel;
    @FXML
    private ProgressBar downAttachProgressBar;
    private SaveAttachmentService saveAttachmentService;

    public MainController(ModelAccess modelAccess) {
        super(modelAccess);
    }

    @FXML
    void setNewMessageBtnAction(ActionEvent event) {
        Scene scene = ViewFactory.defaultViewFactory.getComposeMessageScene();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void setDownAttachButtonAction(ActionEvent event) {
        EmailMessageBean message = emailTableView.getSelectionModel().getSelectedItem();
        if (message != null && message.hasAttachments()) {
            saveAttachmentService.setMessageToDownload(message);
            saveAttachmentService.restart();
        }

    }


    @Override
    //Called to initialize a controller after its root element has been completely processed
    public void initialize(URL location, ResourceBundle resources) {

        downAttachProgressBar.setVisible(false);

        downAttachLabel.setVisible(false);
        saveAttachmentService = new SaveAttachmentService(downAttachProgressBar, downAttachLabel);
        downAttachProgressBar.progressProperty().bind(saveAttachmentService.progressProperty());
        messageRendererService = new MessageRendererService(messageRenderer.getEngine());

        FolderUpdaterService folderUpdaterService = new FolderUpdaterService(getModelAccess().getFolderList());
        folderUpdaterService.start();


        emailTableView.setRowFactory(e -> new BoldRowFactory<>());
        ViewFactory viewFactory = ViewFactory.defaultViewFactory;
        subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("subject"));
        senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("sender"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("size"));
        dateCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, Date>("date"));

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
        CreateAndRegisterEmailAccountService newAccount = new CreateAndRegisterEmailAccountService("micgebak@gmail.com",
                "Test123!",
                root,
                getModelAccess());
        newAccount.start();

        emailTableView.setContextMenu(new ContextMenu(showDetails));

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
                messageRendererService.setMessageToRender(email);

                // The same as in Swing SwingUtilities.invokeLater();
                // Threads which changes the GUI need to work in JavaFX Application Thread
                messageRendererService.restart();

            }

        });

        showDetails.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setScene(viewFactory.getEMailContextMenuScene());
            stage.show();
        });


//        downAttachButton.setOnAction(e -> downAttachButtonAction());


    }
}


//TODO Set icon PART-4
//TODO Add log library PART-4
//TODO Try and catch/ Exceptions