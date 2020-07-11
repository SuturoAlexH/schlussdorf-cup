package com.schlussdorf.components;

import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 * An error dialog that should inform the user that something went wrong.
 */
public class ErrorDialog {

    private final Alert alert;

    /**
     * Default constructor.
     */
    public ErrorDialog(final String id){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().setId(id);
        alert.setTitle(null);
        alert.initStyle(StageStyle.UTILITY);
    }

    /**
     * Shows an error dialog with the given text.
     *
     * @param errorText the text that should be shown
     */
    public void show(final String header, final String errorText){
        alert.setHeaderText(header);
        alert.setContentText(errorText);
        alert.showAndWait();
    }
}
