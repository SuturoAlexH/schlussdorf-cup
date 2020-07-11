package com.schlussdorf.ui.resultDialog;

import com.javafxMvc.annotations.*;
import com.javafxMvc.l10n.L10n;
import com.javafxMvc.validator.CombinedValidator;
import com.schlussdorf.components.ErrorDialog;
import com.schlussdorf.components.RetentionFileChooser;
import com.schlussdorf.ui.table.ResultTableController;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import com.schlussdorf.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

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

    private final RetentionFileChooser retentionFileChooser;

    public ResultDialogController(){
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image files (*.jpg) (*.png) (*.jpeg)", "*.jpg", "*.png", "*jpeg");
        retentionFileChooser = new RetentionFileChooser(imageFilter);
    }

    @Bind
    private void bindModelAndView() {
        view.fireDepartmentTextField.textProperty().bindBidirectional(model.getFireDepartment().valueProperty());
        view.fireDepartmentErrorLabel.visibleProperty().bindBidirectional(model.getFireDepartment().isVisibleProperty());

        view.timeTextField.textProperty().bindBidirectional(model.getTime().valueProperty());
        view.timeErrorLabel.visibleProperty().bindBidirectional(model.getTime().isVisibleProperty());

        view.mistakePointsTextField.textProperty().bindBidirectional(model.getMistakePoints().valueProperty());
        view.mistakePointsErrorLabel.visibleProperty().bindBidirectional(model.getMistakePoints().isVisibleProperty());

        model.getImage().valueProperty().addListener((observableValue, oldImageFile, newImageFile) -> {
            if(newImageFile != null) {
                view.imageProgressIndicator.setVisible(true);

                try(InputStream imageInputStream = new FileInputStream(newImageFile)){
                    view.image.setImage(new Image(imageInputStream));
                    view.imageWrapper.setStyle("-fx-border-color:none");
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                }

                view.imageProgressIndicator.setVisible(false);
            }else{
                view.image.setImage(null);
                System.gc();
                view.imageWrapper.setStyle("-fx-border-color:black");
            }
        });
        view.imageErrorLabel.visibleProperty().bindBidirectional(model.getImage().isVisibleProperty());

        view.addCloseListener((e) -> model.clear());
    }

     void chooseImage() {
        LOGGER.info("open file chooser for image");

        File imageFile = retentionFileChooser.showOpenDialog(view.getRoot().getScene().getWindow());
        if (imageFile != null) {
            LOGGER.info("chosen image is located at " + imageFile.getAbsolutePath());
            model.getImage().valueProperty().set(imageFile);
        }
    }

     void apply(){
         LOGGER.info("tries to apply data");

        if(validator.validate()) {
            try {
                resultTableController.addResult(model.getUuid(), model.getFireDepartment().getValue(), model.getTime().getValue(), model.getMistakePoints().getValue(), model.getImage().getValue());
                view.hide();
                model.clear();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());

                ErrorDialog errorDialog = new ErrorDialog("apply result error dialog");
                String errorText = L10n.getInstance().get("table.add_or_edit_image_error", model.getImage().getValue().getAbsolutePath());
                errorDialog.show(L10n.getInstance().get("error_occured"), errorText);
            }
        }
    }

    void cancel() {
        LOGGER.info("canceled result dialog");

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
        LOGGER.info("opens result dialog with result: {}", result);

        model.setData(result);
        
        view.fireDepartmentTextField.requestFocus();
        view.show();
    }
}