package org.openjfx.ui.resultDialog;

import com.javafxMvc.annotations.*;
import com.javafxMvc.validator.CombinedValidator;
import factory.ResultBuilder;
import javafx.scene.image.Image;
import model.Result;
import org.apache.commons.io.FileUtils;
import org.openjfx.components.ErrorDialog;
import org.openjfx.components.RetentionFileChooser;
import org.openjfx.constants.Folders;
import org.openjfx.ui.table.ResultTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.SaveService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@MVCController
public class ResultDialogController implements ResultDialogActions {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultDialogController.class);

    @Inject
    private ResultDialogModel model;

    @Inject
    private ResultDialogView view;

    @Validator(ResultDialogModel.class)
    private CombinedValidator validator;

    @Inject
    private ResultTableModel resultTableModel;

    private SaveService saveService = new SaveService();

    private RetentionFileChooser retentionFileChooser = new RetentionFileChooser();

    private ErrorDialog errorDialog = new ErrorDialog();

    @Bind
    public void bindModelAndView() {
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
    }

    @PostConstruct
    public void initialize() {
        view.addCloseListener((e) -> model.clear());
    }

    @Override
    public void chooseImage() {
        LOGGER.info("choose image");
        File imageFile = retentionFileChooser.showOpenDialog(view.getRoot().getScene().getWindow());
        if (imageFile != null) {
            LOGGER.info("choosen image is located at " + imageFile.getAbsolutePath());
            model.getImage().valueProperty().set(imageFile);
        }
    }

    @Override
    public void apply(){
        if(validator.validate()) {
            try {
                addResult();
                view.hide();
                model.clear();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    @Override
    public void cancel() {
        view.hide();
        model.clear();
    }

    public void show(final Result result){
        model.setData(result);
        view.show();
    }

    private void addResult() throws IOException {
        UUID uuid = model.getUuid() != null? model.getUuid(): UUID.randomUUID();

        //copy image if necessary
        File selectedImageFile = model.getImage().getValue();
        File resultImageFile = new File(Folders.IMAGE_FOLDER + uuid +  ".jpeg");
        if(!selectedImageFile.getCanonicalPath().equals(resultImageFile.getCanonicalPath())){
            FileUtils.copyFile(selectedImageFile, resultImageFile);
        }

        //create result
        ResultBuilder resultBuilder = new ResultBuilder();
        Result result = resultBuilder.uuid(uuid)
                .fireDepartment(model.getFireDepartment().getValue())
                .time(model.getTime().getValue())
                .mistakePoints(model.getMistakePoints().getValue())
                .image(resultImageFile)
                .build();

        //insert or update result
        if(model.getUuid() != null){
            int index = resultTableModel.getResultList().indexOf(result);
            resultTableModel.getResultList().set(index, result);
        }else{
            resultTableModel.getResultList().add(result);
        }

        //update place
        List<Result> sortedResultList = resultTableModel.getResultList().stream().sorted().collect(Collectors.toList());
        sortedResultList.forEach(currentResult -> currentResult.setPlace(sortedResultList.indexOf(currentResult)+1));
        resultTableModel.resultListProperty().get().removeAll(sortedResultList);
        resultTableModel.resultListProperty().get().addAll(sortedResultList);

        //save to csv
        saveService.save(resultTableModel.getResultList(), Folders.SAVE_FOLDER);
    }
}