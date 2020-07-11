package com.schlussdorf;

import com.javafxMvc.application.MVCApplication;
import com.javafxMvc.l10n.L10n;
import com.schlussdorf.ui.table.ResultTableView;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import com.schlussdorf.components.YesOrNoDialog;
import com.schlussdorf.constants.ApplicationConstants;
import com.schlussdorf.ui.toolbar.ToolbarView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * This class is the entry point of this application.
 */
public class App extends MVCApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private Stage mainStage;

    /**
     * The entry point method.
     *
     * @param args This argument is empty.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Sets up the main window and initialize all necessary things.
     *
     * @param stage The main stage.
     */
    @Override
    public void initialize(Stage stage) {
        super.initialize(stage);

        mainStage = stage;

        stage.getIcons().add(new Image(App.class.getResourceAsStream("/icons/logo.png")));
        stage.setTitle(ApplicationConstants.TITLE);

        VBox root = new VBox();
        root.getChildren().addAll(mvcMap.getNodeByClass(ToolbarView.class), mvcMap.getNodeByClass(ResultTableView.class));
        Scene scene = new Scene(root, ApplicationConstants.WINDOW_WIDTH, ApplicationConstants.WINDOW_HEIGHT);

        stage.setScene(scene);
        stage.show();

        // call randomUUID, because the initial call is very slow
        Thread uuidThread = new Thread(UUID::randomUUID);
        uuidThread.start();

        LOGGER.info("started application #######");
    }

    /**
     * Opens a yes or no dialog before the application is closed.
     *
     * @param e the close window event
     */
    @Override
    public void onClose(WindowEvent e) {
        YesOrNoDialog closeAppDialog = new YesOrNoDialog("close application dialog");
        ButtonType closeAppResult = closeAppDialog.show(L10n.getInstance().get("close_application"), L10n.getInstance().get("close_application_question"));

        if (closeAppResult == ButtonType.YES) {
            LOGGER.info("closed application");
            mainStage.close();
        }else{
            e.consume();
        }
    }

    /**
     * Loads the resource bundle to specify the current language.
     *
     * @return the resource bundle
     */
    @Override
    public ResourceBundle loadResourceBundle() {
        return ResourceBundle.getBundle("language", Locale.GERMANY);
    }
}