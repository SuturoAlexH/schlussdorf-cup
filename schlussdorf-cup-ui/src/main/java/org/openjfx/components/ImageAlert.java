package org.openjfx.components;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageAlert {

    private final Alert alert = new Alert(Alert.AlertType.NONE, "", ButtonType.CLOSE);

    private ImageView imageView = new ImageView();

    public ImageAlert(){
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
