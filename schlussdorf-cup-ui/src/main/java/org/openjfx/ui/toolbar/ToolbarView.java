package org.openjfx.ui.toolbar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.javafxMvc.annotations.Inject;
import com.javafxMvc.annotations.MVCView;
import javafx.scene.layout.FlowPane;

/**
 * The view for the toolbar.
 */
@MVCView("/fxml/toolbar.fxml")
public class ToolbarView {

    @Inject
    private ToolbarController controller;

    @FXML
    FlowPane root;

    @FXML
    Button addButton;

    @FXML
    Button editButton;

    @FXML
    Button deleteButton;

    @FXML
    Button imageButton;

    @FXML
    Button certificateButton;

    /**
     * Opens the result dialog in insertion mode.
     */
    public void addNewResult(){
        controller.addNewResult();
    }

    /**
     * Opens the result dialog in edit mode with the currently selected result.
     */
    public void editResult(){
        controller.editResult();
    }

    /**
     * Deletes the currently selected result.
     */
    public void deleteResult(){
        controller.deleteResult();
    }

    /**
     * Opens an image dialog with the image of the currently selected result.
     */
    public void showImage(){
        controller.showImage();
    }

    /**
     * Creates the certificates for the results.
     */
    public void createCertificates(){
        controller.createCertificates();
    }
}