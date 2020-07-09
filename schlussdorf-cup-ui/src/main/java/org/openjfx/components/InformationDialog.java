package org.openjfx.components;

import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

public class InformationDialog {

    private final Alert alert;

    /**
     * Default constructor.
     */
    public InformationDialog(final String id){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.initStyle(StageStyle.UTILITY);
        alert.getDialogPane().setId(id);
    }

    /**
     * Shows an error dialog with the given text.
     *
     * @param informationText the text that should be shown
     */
    public void show(final String header, final String informationText){
        alert.setHeaderText(header);
        alert.setContentText(informationText);
        alert.showAndWait();
    }
}
