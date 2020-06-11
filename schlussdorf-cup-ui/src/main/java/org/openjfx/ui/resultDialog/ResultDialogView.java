package org.openjfx.ui.resultDialog;

import com.javafxMvc.dialog.AbstractDialogView;
import javafx.scene.layout.Pane;
import com.javafxMvc.annotations.Inject;
import com.javafxMvc.annotations.MVCView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

@MVCView("/fxml/result.fxml")
public class ResultDialogView extends AbstractDialogView implements ResultDialogActions {

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
    Label imageErrorLabel;

    @Override
    public void chooseImage() {
        controller.chooseImage();
    }

    @Override
    public void apply() {
        controller.apply();
    }

    @Override
    public void cancel() {
        controller.cancel();
    }
}