package org.openjfx.ui.resultDialog;

import com.javafxMvc.annotations.ValidationProperty;
import com.javafxMvc.model.ValidatableProperty;
import com.javafxMvc.validator.DoubleValidator;
import com.javafxMvc.validator.NotEmptyValidator;
import com.javafxMvc.validator.NumberValidator;
import model.Result;
import com.javafxMvc.annotations.MVCModel;

import java.io.File;
import java.util.UUID;

@MVCModel
public class ResultDialogModel {

    private UUID uuid;

    @ValidationProperty(NotEmptyValidator.class)
    private ValidatableProperty<String> fireDepartment;

    @ValidationProperty(DoubleValidator.class)
    private ValidatableProperty<String> time;

    @ValidationProperty(NumberValidator.class)
    private ValidatableProperty<String> mistakePoints;

    @ValidationProperty(NotEmptyValidator.class)
    private ValidatableProperty<File> image;

    public void setData(final Result result){
        if(result != null) {
            uuid = result.getUuid();
            fireDepartment.valueProperty().set(result.getFireDepartment());
            time.valueProperty().set(String.valueOf(result.getTime()));
            mistakePoints.valueProperty().set(String.valueOf(result.getMistakePoints()));
            image.valueProperty().set(result.getImage());
        }
    }

    public void clear(){
        uuid = null;
        fireDepartment.reset();
        time.reset();
        mistakePoints.reset();
        image.reset();
    }

    public UUID getUuid() {
        return uuid;
    }

    public ValidatableProperty<String> getFireDepartment() {
        return fireDepartment;
    }

    public ValidatableProperty<String> getTime() {
        return time;
    }

    public ValidatableProperty<String> getMistakePoints() {
        return mistakePoints;
    }

    public ValidatableProperty<File> getImage() {
        return image;
    }
}