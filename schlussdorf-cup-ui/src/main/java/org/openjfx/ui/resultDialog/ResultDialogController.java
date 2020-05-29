package org.openjfx.ui.resultDialog;

import com.javafxMvc.dialog.DialogController;
import com.javafxMvc.annotations.Validator;
import com.javafxMvc.validator.CombinedValidator;
import factory.ResultFactory;
import javafx.scene.Parent;
import model.Result;
import com.javafxMvc.annotations.Bind;
import com.javafxMvc.annotations.Inject;
import com.javafxMvc.annotations.MVCController;
import org.openjfx.components.RetentionFileChooser;
import org.openjfx.constants.Folders;
import org.openjfx.ui.table.ResultTableController;
import util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;

@MVCController
public class ResultDialogController extends DialogController implements ResultDialogActions {

    @Inject
    private ResultDialogModel model;

    @Inject
    private ResultDialogView view;

    @Validator(ResultDialogModel.class)
    private CombinedValidator validator;

    @Inject
    private ResultTableController resultTableController;

    public ResultDialogController(Parent dialog) {
        super(dialog);
    }

    @Bind
    public void bindModelAndView() {
        view.fireDepartmentTextField.textProperty().bindBidirectional(model.fireDepartment.valueProperty());
        view.fireDepartmentErrorLabel.visibleProperty().bindBidirectional(model.fireDepartment.isVisibleProperty());
        view.fireDepartmentErrorLabel.textProperty().bindBidirectional(model.fireDepartment.errorProperty());

        view.timeTextField.textProperty().bindBidirectional(model.time.valueProperty());
        view.timeErrorLabel.visibleProperty().bindBidirectional(model.time.isVisibleProperty());
        view.timeErrorLabel.textProperty().bindBidirectional(model.time.errorProperty());

        view.mistakePointsTextField.textProperty().bindBidirectional(model.mistakePoints.valueProperty());
        view.mistakePointsErrorLabel.visibleProperty().bindBidirectional(model.mistakePoints.isVisibleProperty());
        view.mistakePointsErrorLabel.textProperty().bindBidirectional(model.mistakePoints.errorProperty());

        view.image.imageProperty().bindBidirectional(model.image.valueProperty());
        view.imageErrorLabel.visibleProperty().bindBidirectional(model.image.isVisibleProperty());
        view.imageErrorLabel.textProperty().bindBidirectional(model.image.errorProperty());

        model.image.valueProperty().addListener((observableValue, image1, t1) -> view.imageWrapper.setStyle("-fx-border-color:none"));
    }

    public void show(Result result){
        model.setData(result);
        super.show();
    }

    @Override
    public void chooseImage() {
        File file = RetentionFileChooser.showOpenDialog(view.rootPane.getScene().getWindow());
        if (file != null) {
            model.chooseImage(file.toURI().toString());
        }
    }

    @Override
    public void apply(){
        if(validator.validate()) {
            addResult();
            hide();
        }
    }

    @Override
    public void cancel() {
        hide();
    }

    @Override
    public void clear() {
        model.fireDepartment.valueProperty().set("");
        model.fireDepartment.isVisibleProperty().set(false);

        model.time.valueProperty().set("");
        model.time.isVisibleProperty().set(false);

        model.mistakePoints.valueProperty().set("");
        model.mistakePoints.isVisibleProperty().set(false);

        model.image.valueProperty().set(null);
        model.image.isVisibleProperty().set(false);
    }

    private void addResult(){
        Result result;
        String imageFolderPath = FileSystems.getDefault().getPath("").toAbsolutePath() + Folders.IMAGE_FOLDER;
        if(model.uuid != null){
            result = ResultFactory.create(model.uuid, model.fireDepartment.getValue(), Double.valueOf(model.time.getValue()), Integer.valueOf(model.mistakePoints.getValue()), imageFolderPath);
        }else{
            result= ResultFactory.create(model.fireDepartment.getValue(), Double.valueOf(model.time.getValue()), Integer.valueOf(model.mistakePoints.getValue()), imageFolderPath);
        }

        resultTableController.insertOrUpdate(result);

        try {
            String imagePath = new URI(model.image.getValue().getUrl()).getPath();
            if(!imagePath.equals(result.getImagePath())){
                FileUtil.copyFile(imagePath, result.getImagePath());
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}