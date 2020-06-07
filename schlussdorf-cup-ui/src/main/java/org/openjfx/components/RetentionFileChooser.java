package org.openjfx.components;


import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class RetentionFileChooser {

    private FileChooser fileChooser = new FileChooser();

    private SimpleObjectProperty<File> lastKnownDirectoryProperty = new SimpleObjectProperty<>();

    public RetentionFileChooser(){
        fileChooser.initialDirectoryProperty().bindBidirectional(lastKnownDirectoryProperty);
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image files (*.jpg) (*.png) (*.jpeg)", "*.jpg", "*.png", "*jpeg");
        fileChooser.getExtensionFilters().setAll(imageFilter);
    }

    public File showOpenDialog(Window ownerWindow){
        File chosenFile = fileChooser.showOpenDialog(ownerWindow);
        if(chosenFile != null){
            lastKnownDirectoryProperty.setValue(chosenFile.getParentFile());
        }

        return chosenFile;
    }
}