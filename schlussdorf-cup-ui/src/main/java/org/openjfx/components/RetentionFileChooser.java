package org.openjfx.components;


import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

/**
 * A file chooser that remembers the directory of the last chosen file.
 */
public class RetentionFileChooser {

    private FileChooser fileChooser = new FileChooser();

    private SimpleObjectProperty<File> lastKnownDirectoryProperty = new SimpleObjectProperty<>();

    /**
     * Default constructor.
     */
    //TODO: hard coded filter
    public RetentionFileChooser(){
        fileChooser.initialDirectoryProperty().bindBidirectional(lastKnownDirectoryProperty);
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image files (*.jpg) (*.png) (*.jpeg)", "*.jpg", "*.png", "*jpeg");
        fileChooser.getExtensionFilters().setAll(imageFilter);
    }

    /**
     * Opens the default file chooser dialog.
     *
     * @param ownerWindow the parent window
     * @return if no file is selected null and the file otherwise
     */
    public File showOpenDialog(Window ownerWindow){
        File chosenFile = fileChooser.showOpenDialog(ownerWindow);
        if(chosenFile != null){
            lastKnownDirectoryProperty.setValue(chosenFile.getParentFile());
        }

        return chosenFile;
    }
}