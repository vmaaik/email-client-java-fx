package com.gebarowski;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public TableView<EmailMessageBean> emailTableView;
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

    @Override
    //Called to initialize a controller after its root element has been completely processed
    public void initialize(URL location, ResourceBundle resources) {

    }

    final ObservableList<EmailMessageBean> data = FXCollections.observableArrayList(
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 12),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 400000),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 400),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 400000000),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 9000),
            new EmailMessageBean("Hello Michal", "michal.jako@op.aw", 40070)
    );

    public void button1Action(ActionEvent actionEvent) {

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

        emailTableView.setItems(data);
    }
}
