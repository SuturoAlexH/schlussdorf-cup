package org.openjfx.components;

import javafx.scene.control.Alert;

/**
 * An error dialog that should inform the user that something went wrong.
 */
public class ErrorDialog {

    private Alert alert;

    /**
     * Default constructor.
     */
    public ErrorDialog(){
        initialize();
    }

    private void initialize(){
        alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText("Es ist ein Fehler aufgetreten!");
    }

    /**
     * Shows an error dialog with the given text.
     *
     * @param errorText the text that should be shown
     */
    public void show(final String errorText){
        alert.setContentText(errorText);
        alert.showAndWait();
    }
}
