package org.openjfx.components;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageDialog {

    private Alert alert;

    private ImageView imageView ;

    public ImageDialog(){
        alert = new Alert(Alert.AlertType.NONE, "", ButtonType.CLOSE);
        imageView = new ImageView();
        initialize();
    }

    private void initialize(){
        alert.getDialogPane().setMaxSize(300, 300);
        imageView.setFitHeight(400);
        imageView.setFitWidth(400);
        alert.setGraphic(imageView);
    }

    public void setImageAndShow(final Image image){
        imageView.setImage(image);
        alert.showAndWait();
    }
}
