package com.gebarowski.view;


//return different scenes for different Layouts

import com.gebarowski.controller.AbstractController;
import com.gebarowski.controller.EmailContextMenuController;
import com.gebarowski.controller.MainController;
import com.gebarowski.controller.ModelAccess;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;


public class ViewFactory {


    private ModelAccess modelAccess = new ModelAccess();
    private MainController mainController;
    private EmailContextMenuController emailContextMenuController;
    private final String DEFAULT_CSS = "style.css";
    private final String EMAIL_CONTEXT_MENU_FXML= "EmailContextMenuLayout.fxml";
    private final String MAIN_LAYOUT_FXML= "MainLayout.fxml";


    // one instance for all controllers
    public static ViewFactory defaultViewFactory = new ViewFactory();
    private static boolean mainSceneFlag = false;


    /**
     * Both controllers have the same modelAccess
     * object
     *
     */
    public Scene getMainScene() throws OperationNotSupportedException {
        if (!mainSceneFlag) {
            mainController = new MainController(modelAccess);
            mainSceneFlag = true;
            return initializeScene(MAIN_LAYOUT_FXML, mainController);
        } else {
            throw new OperationNotSupportedException("Main Class already initialized!");
        }
    }

    public Scene getEMailContextMenuScene() {

        emailContextMenuController = new EmailContextMenuController(modelAccess);
        return initializeScene(EMAIL_CONTEXT_MENU_FXML, emailContextMenuController);
    }


    private Scene initializeScene(String fxmlPath, AbstractController controller ){
        /**
         * instead of in fxml file, controllers are set here
         */
        FXMLLoader loader;
        Parent parent;
        Scene scene;

        try {
            loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setController(controller);
            parent = loader.load();

        } catch (Exception e) {
           return null;
        }
        scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource(DEFAULT_CSS).toExternalForm());
        return scene;
    }


}
