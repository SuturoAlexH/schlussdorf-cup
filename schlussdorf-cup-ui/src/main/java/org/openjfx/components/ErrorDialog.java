package org.openjfx.components;

import com.javafxMvc.l10n.L10n;
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
    public ErrorDialog(){
        alert = new Alert(Alert.AlertType.ERROR);
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
