package com.schlussdorf.components;

import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

/**
 * A file chooser that remembers the directory of the last chosen file.
 */
public class RetentionFileChooser {

    private final FileChooser fileChooser;

    private final SimpleObjectProperty<File> lastChosenFolderProperty;

    /**
     * Default constructor.
     */
    public RetentionFileChooser(final FileChooser.ExtensionFilter extensionFilter){
        fileChooser = new FileChooser();
        lastChosenFolderProperty = new SimpleObjectProperty<>();

        fileChooser.initialDirectoryProperty().bindBidirectional(lastChosenFolderProperty);
        fileChooser.getExtensionFilters().setAll(extensionFilter);
    }

    /**
     * Opens the default file chooser dialog.
     *
     * @param ownerWindow the parent window
     * @return if no file is selected null and the file otherwise
     */
    public File showOpenDialog(Window ownerWindow){
        File lastChosenFolder = lastChosenFolderProperty.get();
        if(lastChosenFolder != null && !lastChosenFolder.exists()){
            lastChosenFolderProperty.set(null);
        }

        File chosenFile = fileChooser.showOpenDialog(ownerWindow);
        if(chosenFile != null){
            lastChosenFolderProperty.setValue(chosenFile.getParentFile());
        }

        return chosenFile;
    }
}