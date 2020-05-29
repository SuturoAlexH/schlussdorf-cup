package org.openjfx.ui.toolbar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.javafxMvc.annotations.Inject;
import com.javafxMvc.annotations.MVCView;
import javafx.scene.layout.FlowPane;

@MVCView("/fxml/toolbar.fxml")
public class ToolbarView implements ToolbarActions {

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

    public void addNewResult(){
        controller.addNewResult();
    }

    public void editResult(){
        controller.editResult();
    }

    public void deleteResult(){
        controller.deleteResult();
    }

    public void showImage(){
        controller.showImage();
    }

    public void createCertificates(){
        //controller.createCertificates();
    }
}