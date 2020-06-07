package org.openjfx.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class YesOrNoDialog {

    private Alert alert;

    public YesOrNoDialog(){
        alert = new Alert(Alert.AlertType.NONE, "", ButtonType.YES, ButtonType.NO);
        initialize();
    }

    private void initialize(){
        alert.getDialogPane().getStylesheets().add(YesOrNoDialog.class.getResource("/css/bootstrap3.css").toExternalForm());

        Button yesButton = (Button)alert.getDialogPane().lookupButton(ButtonType.YES);
        Button noButton = (Button)alert.getDialogPane().lookupButton(ButtonType.NO);

        yesButton.getStyleClass().add("success");
        noButton.getStyleClass().add("danger");
    }

    public ButtonType show(final String contentText){
        alert.setContentText(contentText);
        alert.showAndWait();

        return alert.getResult();
    }
}