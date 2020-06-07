package org.openjfx.components;

import javafx.scene.control.Alert;

public class ErrorDialog {

    private Alert alert;

    public ErrorDialog(){
        initialize();
    }

    private void initialize(){
        alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText("Es ist ein Fehler aufgetreten!");
    }

    public void show(final String errorText){
        alert.setContentText(errorText);
        alert.showAndWait();
    }
}
