package org.openjfx.components;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A dialog with an image as content and a close button.
 */
public class ImageDialog {

    private Alert alert;

    private ImageView imageView ;

    /**
     * Default constructor.
     */
    public ImageDialog(){
        alert = new Alert(Alert.AlertType.NONE, "", ButtonType.CLOSE);
        imageView = new ImageView();
        initialize();
    }

    private void initialize(){
        alert.getDialogPane().setMaxSize(300, 300);
        imageView.setFitHeight(300);
        imageView.setFitHeight(300);
        imageView.preserveRatioProperty().set(true);

        alert.setGraphic(imageView);
    }

    /**
     * Shows the dialog with the given image as content.
     *
     * @param image the image that should be shown
     */
    public void show(final Image image){
        imageView.setImage(image);
        alert.showAndWait();
    }
}
