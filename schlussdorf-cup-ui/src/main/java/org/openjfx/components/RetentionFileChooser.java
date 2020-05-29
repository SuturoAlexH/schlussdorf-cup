package org.openjfx.components;


import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class RetentionFileChooser {

    private static FileChooser instance = null;

    private static SimpleObjectProperty<File> lastKnownDirectoryProperty = new SimpleObjectProperty<>();

    private RetentionFileChooser(){ }

    private static FileChooser getInstance(){
        if(instance == null) {
            instance = new FileChooser();
            instance.initialDirectoryProperty().bindBidirectional(lastKnownDirectoryProperty);
            FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image files (*.jpg) (*.png) (*.jpeg)", "*.jpg", "*.png", "*jpeg");
            instance.getExtensionFilters().setAll(imageFilter);
        }
        return instance;
    }

    public static File showOpenDialog(Window ownerWindow){
        File chosenFile = getInstance().showOpenDialog(ownerWindow);
        if(chosenFile != null){
            lastKnownDirectoryProperty.setValue(chosenFile.getParentFile());
        }
        return chosenFile;
    }
}