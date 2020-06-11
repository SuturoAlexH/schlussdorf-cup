package org.openjfx.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
        yesButton.setGraphic(new ImageView(new Image(YesOrNoDialog.class.getResourceAsStream("/icons/check.png"))));
        noButton.getStyleClass().add("danger");
        noButton.setGraphic(new ImageView(new Image(YesOrNoDialog.class.getResourceAsStream("/icons/cross.png"))));
    }

    public ButtonType show(final String contentText){
        alert.setContentText(contentText);
        alert.showAndWait();

        return alert.getResult();
    }
}