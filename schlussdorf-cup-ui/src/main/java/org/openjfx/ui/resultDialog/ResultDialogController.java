package org.openjfx.ui.resultDialog;

import com.javafxMvc.annotations.*;
import com.javafxMvc.validator.CombinedValidator;
import javafx.scene.image.Image;
import model.Result;
import org.openjfx.components.ErrorDialog;
import org.openjfx.components.RetentionFileChooser;
import org.openjfx.ui.table.ResultTableController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The controller for the result dialog.
 */
@MVCController
public class ResultDialogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultDialogController.class);

    @Inject
    private ResultDialogModel model;

    @Inject
    private ResultDialogView view;

    @Validator(ResultDialogModel.class)
    private CombinedValidator validator;

    @Inject
    private ResultTableController resultTableController;

    private RetentionFileChooser retentionFileChooser = new RetentionFileChooser();

    private ErrorDialog errorDialog = new ErrorDialog();

    @Bind
    private void bindModelAndView() {
        view.fireDepartmentTextField.textProperty().bindBidirectional(model.getFireDepartment().valueProperty());
        view.fireDepartmentErrorLabel.visibleProperty().bindBidirectional(model.getFireDepartment().isVisibleProperty());

        view.timeTextField.textProperty().bindBidirectional(model.getTime().valueProperty());
        view.timeErrorLabel.visibleProperty().bindBidirectional(model.getTime().isVisibleProperty());

        view.mistakePointsTextField.textProperty().bindBidirectional(model.getMistakePoints().valueProperty());
        view.mistakePointsErrorLabel.visibleProperty().bindBidirectional(model.getMistakePoints().isVisibleProperty());

        model.getImage().valueProperty().addListener((observableValue, oldImageFile, newImageFile) -> {
            try {
                if(newImageFile != null) {
                    view.image.setImage(new Image(new FileInputStream(newImageFile)));
                    view.imageWrapper.setStyle("-fx-border-color:none");
                }else{
                    view.image.setImage(null);
                    System.gc();
                    view.imageWrapper.setStyle("-fx-border-color:black");
                }
            } catch (FileNotFoundException e) {
                LOGGER.error(e.getMessage());
                errorDialog.show("Das Bild für die Ortsfeuerwehr " + model.getFireDepartment().valueProperty().get() + " konnte leider nicht geladen werden!");
            }
        });
        view.imageErrorLabel.visibleProperty().bindBidirectional(model.getImage().isVisibleProperty());

        view.addCloseListener((e) -> model.clear());
    }

     void chooseImage() {
        LOGGER.info("choose image");
        File imageFile = retentionFileChooser.showOpenDialog(view.getRoot().getScene().getWindow());
        if (imageFile != null) {
            LOGGER.info("choosen image is located at " + imageFile.getAbsolutePath());
            model.getImage().valueProperty().set(imageFile);
        }
    }

     void apply(){
        if(validator.validate()) {
            try {
                resultTableController.addResult(model.getUuid(), model.getFireDepartment().getValue(), model.getTime().getValue(), model.getMistakePoints().getValue(), model.getImage().getValue());
                view.hide();
                model.clear();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    void cancel() {
        view.hide();
        model.clear();
    }

    /**
     * Opens the dialog and shows the data of the result if its not null. If its null a blank/empty
     * dialog is shown.
     *
     * @param result the result that should be shown.
     */
    public void show(final Result result){
        model.setData(result);
        view.show();
    }
}