package org.openjfx.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;

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
        initialize();
    }

    private void initialize(){
        alert = new Alert(Alert.AlertType.NONE, "", ButtonType.CLOSE);
        alert.getDialogPane().setMaxSize(300, 300);

        Button closeButton = (Button)alert.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.setId("closeButton");
        closeButton.setGraphic(new ImageView(new Image(ImageDialog.class.getResourceAsStream("/icons/cross.png"))));

        imageView = new ImageView();
        imageView.setFitHeight(300);
        imageView.setFitHeight(300);
        imageView.preserveRatioProperty().set(true);

        alert.setOnHidden(e -> {
            imageView.setImage(null);
            System.gc();
        });

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
