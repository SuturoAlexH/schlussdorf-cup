package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import static org.junit.Assert.assertNotNull;

public class ShowImageSteps extends BaseApplicationTest{

    @Given("the show image dialog is opened")
    public void showImageDialogIsOpened(){
        selectRow(1);
        clickOnNode("show image button");
    }

    @Then("the show image dialog shows {string}")
    public void showImageDialogShows(final String imageName){
        final Stage actualAlertDialog = getTopModalStage();
        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        ImageView imageView = (ImageView) dialogPane.getGraphic();

        assertNotNull(imageView.getImage());
    }
}
