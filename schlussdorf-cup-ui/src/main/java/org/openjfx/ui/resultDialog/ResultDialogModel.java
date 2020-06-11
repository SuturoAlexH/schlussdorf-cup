package org.openjfx.ui.resultDialog;

import com.javafxMvc.annotations.ValidationProperty;
import com.javafxMvc.model.ValidatableProperty;
import com.javafxMvc.validator.DoubleValidator;
import com.javafxMvc.validator.NotEmptyValidator;
import com.javafxMvc.validator.NumberValidator;
import model.Result;
import javafx.scene.image.Image;
import com.javafxMvc.annotations.MVCModel;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@MVCModel
public class ResultDialogModel {

    private UUID uuid;

    @ValidationProperty(validator = NotEmptyValidator.class, errorText="Dieses Feld darf nicht leer sein!")
    private ValidatableProperty<String> fireDepartment;

    @ValidationProperty(validator = DoubleValidator.class, errorText="Das ist keine gültige Zahl!")
    private ValidatableProperty<String> time;

    @ValidationProperty(validator = NumberValidator.class, errorText="Das ist keine gültige Zahl!")
    private ValidatableProperty<String> mistakePoints;

    @ValidationProperty(validator = NotEmptyValidator.class, errorText="Es muss ein Bild gewählt werden!")
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