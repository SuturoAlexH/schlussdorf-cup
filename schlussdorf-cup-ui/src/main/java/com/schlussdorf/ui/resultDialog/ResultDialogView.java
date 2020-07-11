package com.schlussdorf.ui.resultDialog;

import com.javafxMvc.dialog.AbstractDialogView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import com.javafxMvc.annotations.Inject;
import com.javafxMvc.annotations.MVCView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * The view for the result dialog.
 */
@MVCView("/fxml/result.fxml")
public class ResultDialogView extends AbstractDialogView {

    @Inject
    private ResultDialogController controller;

    @FXML
    TextField fireDepartmentTextField;

    @FXML
    Label fireDepartmentErrorLabel;

    @FXML
    TextField timeTextField;

    @FXML
    Label timeErrorLabel;

    @FXML
    TextField mistakePointsTextField;

    @FXML
    Label mistakePointsErrorLabel;

    @FXML
    Pane imageWrapper;

    @FXML
    ImageView image;

    @FXML
    ProgressIndicator imageProgressIndicator;
    @FXML
    Label imageErrorLabel;

    /**
     * Opens a default file chooser and sets the selected image as result image.
     */
    public void chooseImage() {
        controller.chooseImage();
    }

    /**
     * If the input is valid the a new result is created/edited and added to the table.
     */
    public void apply() {
        controller.apply();
    }

    /**
     * Closes the dialog without any changes.
     */
    public void cancel() {
        controller.cancel();
    }
}