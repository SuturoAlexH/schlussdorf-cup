package org.openjfx;

import com.javafxMvc.application.MVCApplication;
import javafx.scene.layout.VBox;
import com.javafxMvc.annotations.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class is the entry point of this application.
 */
@MVCApp("/fxml/main.fxml")
public class App extends MVCApplication {

    /**
     * The title of this application.
     */
    private static final String TITLE = "SchlußdorfCup";

    /**
     * The initial main window width.
     */
    private static final int WINDOW_WIDTH = 750;

    /**
     * The initial main window height.
     */
    private static final int WINDOW_HEIGHT = 450;

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
     *
     */

    @Override
    public void initialize(Stage stage) {
        //stage.getIcons().add(new Image(ui.App.class.getResourceAsStream("/images/logo.png")));
        stage.setResizable(false);

        VBox root = new VBox();
        root.getChildren().addAll(mvcMap.getNodeByName("Toolbar"), mvcMap.getNodeByName("ResultTable"));
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        Platform.setImplicitExit(false);
        setupMainWindowCloseListener(stage);
        stage.setTitle(TITLE);
        stage.setScene(scene);

        stage.show();
    }

    /**
     * Adds a listener to the cross button in the upper right corner of the main window.
     * If the listener is triggered a yes or no dialog appear and asks the user if the
     * application should be terminated.
     *
     * @param stage The main stage.
     */
    private void setupMainWindowCloseListener(final Stage stage){
//        stage.setOnCloseRequest(e -> {
//            Alert alert = YesOrNoDialog.getAlert("Soll das Programm wirklich beendet werden?");
//            alert.showAndWait();
//
//            if (alert.getResult() == ButtonType.NO) {
//                e.consume();
//            }else{
//                System.exit(0);
//            }
//        });
    }
}
