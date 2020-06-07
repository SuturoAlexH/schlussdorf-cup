package org.openjfx.ui.resultDialog;

import com.javafxMvc.annotations.Validator;
import com.javafxMvc.validator.CombinedValidator;
import factory.ResultBuilder;
import javafx.scene.image.Image;
import javafx.stage.Window;
import model.Result;
import com.javafxMvc.annotations.Bind;
import com.javafxMvc.annotations.Inject;
import com.javafxMvc.annotations.MVCController;
import org.apache.commons.io.FileUtils;
import org.openjfx.components.RetentionFileChooser;
import org.openjfx.constants.Folders;
import org.openjfx.ui.table.ResultTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.SaveService;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.function.Function;

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

    private ResultBuilder resultFactory = new ResultBuilder();

    private SaveService saveService = new SaveService();

    private RetentionFileChooser retentionFileChooser = new RetentionFileChooser();

    @Bind
    public void bindModelAndView() {
        view.fireDepartmentTextField.textProperty().bindBidirectional(model.getFireDepartment().valueProperty());
        view.fireDepartmentErrorLabel.visibleProperty().bindBidirectional(model.getFireDepartment().isVisibleProperty());
        view.fireDepartmentErrorLabel.textProperty().bindBidirectional(model.getFireDepartment().errorProperty());

        view.timeTextField.textProperty().bindBidirectional(model.getTime().valueProperty());
        view.timeErrorLabel.visibleProperty().bindBidirectional(model.getTime().isVisibleProperty());
        view.timeErrorLabel.textProperty().bindBidirectional(model.getTime().errorProperty());

        view.mistakePointsTextField.textProperty().bindBidirectional(model.getMistakePoints().valueProperty());
        view.mistakePointsErrorLabel.visibleProperty().bindBidirectional(model.getMistakePoints().isVisibleProperty());
        view.mistakePointsErrorLabel.textProperty().bindBidirectional(model.getMistakePoints().errorProperty());

        model.getImage().valueProperty().addListener((observableValue, oldImageFile, newImageFile) -> {
            if(newImageFile != null){
                view.imageWrapper.setStyle("-fx-border-color:none");
            }else{
                view.imageWrapper.setStyle("-fx-border-color:black");
            }
        });

        model.getImage().valueProperty().addListener((observableValue, oldImageFile, newImageFile) -> {
            try {
                view.image.setImage(new Image(FileUtils.openInputStream(newImageFile)));
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        });
        view.imageErrorLabel.visibleProperty().bindBidirectional(model.getImage().isVisibleProperty());
        view.imageErrorLabel.textProperty().bindBidirectional(model.getImage().errorProperty());
    }

    @Override
    public void chooseImage() {
        LOGGER.info("choose image");
        File imageFile = retentionFileChooser.showOpenDialog(view.getRoot().getScene().getWindow());
        if (imageFile != null) {
            LOGGER.info("choosen image is located at " + imageFile.getAbsolutePath());
            model.getImage().valueProperty().set(imageFile);
        }

        LOGGER.info("no image is chosen");
    }

    @Override
    public void apply(){
        if(validator.validate()) {
            try {
                addResult();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
            view.hide();
        }
    }

    @Override
    public void cancel() {
        view.hide();
    }

    public void show(final Result result){
        model.setData(result);
        view.show();
    }

    private void addResult() throws IOException {
        UUID uuid = model.getUuid() != null? model.getUuid(): UUID.randomUUID();

        //copy image if necessary
        File selectedImageFile = model.getImage().getValue();
        File resultImageFile = new File(Folders.IMAGE_FOLDER + model.getUuid() +  ".jpeg");
        if(!selectedImageFile.equals(resultImageFile)){
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
        Function<Result, Integer> getPlace = r -> resultTableModel.getResultList().indexOf(r) +1;
        resultTableModel.getResultList().stream().sorted().forEach(r -> {
                r.setPlace(getPlace.apply(r));
                System.out.println("res: " + r);
            }
        );

        //save to csv
        saveService.save(resultTableModel.getResultList(), Folders.SAVE_FOLDER);
    }
}