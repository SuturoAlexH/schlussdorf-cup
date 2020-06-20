package org.openjfx;

import com.javafxMvc.application.MVCApplication;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.openjfx.components.YesOrNoDialog;
import org.openjfx.constants.ApplicationConstants;
import org.openjfx.ui.table.ResultTableView;
import org.openjfx.ui.toolbar.ToolbarView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class is the entry point of this application.
 */
public class App extends MVCApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private YesOrNoDialog closeAppDialog = new YesOrNoDialog();

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
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/icons/logo.png")));
        stage.setTitle(ApplicationConstants.TITLE);

        VBox root = new VBox();
        root.getChildren().addAll(mvcMap.getNodeByClass(ToolbarView.class), mvcMap.getNodeByClass(ResultTableView.class));
        Scene scene = new Scene(root, ApplicationConstants.WINDOW_WIDTH, ApplicationConstants.WINDOW_HEIGHT);

        stage.setScene(scene);
        stage.show();

        LOGGER.info("started application ######################################################");
    }

    /**
     * Opens a yes or no dialog before the application is closed.
     *
     * @param e the close window event
     */
    @Override
    public void onClose(WindowEvent e) {
        ButtonType closeAppResult = closeAppDialog.show("Soll das Programm wirklich beendet werden?");

        if (closeAppResult == ButtonType.YES) {
            LOGGER.info("closed application");
            System.exit(0);
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