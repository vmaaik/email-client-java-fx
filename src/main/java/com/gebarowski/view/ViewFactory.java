package com.gebarowski.view;


//return different scenes for different Layouts

import com.gebarowski.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javax.naming.OperationNotSupportedException;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


public class ViewFactory {
    //    final Logger logger = LoggerFactory.getLogger(ViewFactory.class);
    // one instance for all controllers
    public static ViewFactory defaultViewFactory = new ViewFactory();
    private static boolean mainSceneFlag = false;

    private final String DEFAULT_CSS = "/css/style.css";
    private final String EMAIL_CONTEXT_MENU_FXML = "fxml/EmailContextMenuLayout.fxml";
    private final String MAIN_LAYOUT_FXML = "fxml/Main_Layout.fxml";
    private final String COMPOSE_MESSAGE_CONTROLLER = "fxml/ComposeMessageLayout.fxml";

    private ModelAccess modelAccess = new ModelAccess();

    private MainController mainController;
    private EmailContextMenuController emailContextMenuController;


    /**
     * Both controllers have the same modelAccess
     * object
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

    public Scene getComposeMessageScene() {
        AbstractController composeMessageController = new ComposeMessageController(modelAccess);
//        logger.info("Attempting to initialize MessageScene...");
        return initializeScene(COMPOSE_MESSAGE_CONTROLLER, composeMessageController);


    }

    private Scene initializeScene(String fxmlPath, AbstractController controller) {
        /**
         * instead of in fxml file, controllers are set here
         */
        FXMLLoader loader;
        Parent parent;
        Scene scene;

        try {
            loader = new FXMLLoader();
            loader.setLocation(ViewFactory.class.getClassLoader().getResource(fxmlPath));
            loader.setController(controller);
            parent = loader.load();

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
        scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource(DEFAULT_CSS).toExternalForm());
        return scene;
    }
}
