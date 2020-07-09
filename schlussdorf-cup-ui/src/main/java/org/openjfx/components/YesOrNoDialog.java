package org.openjfx.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;

/**
 *
 */
public class YesOrNoDialog {

    private Alert alert;

    /**
     * Default constructor.
     */
    public YesOrNoDialog(final String id){
        initialize(id);
    }

    private void initialize(final String id){
        alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.getDialogPane().setId(id);
        alert.getDialogPane().getStylesheets().add(YesOrNoDialog.class.getResource("/css/bootstrap3.css").toExternalForm());
        alert.setTitle(null);
        alert.initStyle(StageStyle.UTILITY);

        Button yesButton = (Button)alert.getDialogPane().lookupButton(ButtonType.YES);
        yesButton.setId("yesButton");
        yesButton.getStyleClass().add("success");
        yesButton.setGraphic(new ImageView(new Image(YesOrNoDialog.class.getResourceAsStream("/icons/check.png"))));

        Button noButton = (Button)alert.getDialogPane().lookupButton(ButtonType.NO);
        noButton.setId("noButton");
        noButton.getStyleClass().add("danger");
        noButton.setGraphic(new ImageView(new Image(YesOrNoDialog.class.getResourceAsStream("/icons/cross.png"))));
    }

    /**
     *
     * @param header
     * @param question
     * @return
     */
    public ButtonType show(final String header, final String question){
        alert.setHeaderText(header);
        alert.setContentText(question);
        alert.showAndWait();

        return alert.getResult();
    }
}