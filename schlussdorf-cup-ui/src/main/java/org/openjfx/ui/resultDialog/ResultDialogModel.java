package org.openjfx.ui.resultDialog;

import com.javafxMvc.annotations.ValidationProperty;
import com.javafxMvc.model.ValidatableProperty;
import com.javafxMvc.validator.DoubleValidator;
import com.javafxMvc.validator.NotEmptyValidator;
import com.javafxMvc.validator.NumberValidator;
import model.Result;
import javafx.scene.image.Image;
import com.javafxMvc.annotations.MVCModel;

import java.io.File;
import java.util.UUID;

@MVCModel
public class ResultDialogModel {

    UUID uuid;

    @ValidationProperty(validator = NotEmptyValidator.class, errorText="Dieses Feld darf nicht leer sein!")
    ValidatableProperty<String> fireDepartment;

    @ValidationProperty(validator = DoubleValidator.class, errorText="Das ist keine gültige Zahl!")
    ValidatableProperty<String> time;

    @ValidationProperty(validator = NumberValidator.class, errorText="Das ist keine gültige Zahl!")
    ValidatableProperty<String> mistakePoints;

    @ValidationProperty(validator = NotEmptyValidator.class, errorText="Es muss ein Bild gewählt werden!")
    ValidatableProperty<Image> image;

    public void setData(final Result result){
        if(result != null) {
            uuid = result.getUuid();
            fireDepartment.valueProperty().set(result.getFireDepartment());
            time.valueProperty().set(String.valueOf(result.getTime()));
            mistakePoints.valueProperty().set(String.valueOf(result.getMistakePoints()));

            File imageFile = new File(result.getImagePath());
            image.valueProperty().set(new Image(imageFile.toURI().toString()));
        }
    }

    void chooseImage(final String imagePath){
        image.valueProperty().set(new Image(imagePath));
    }
}