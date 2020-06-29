package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

import static org.junit.Assert.assertEquals;

public class DeleteSteps extends BaseApplicationTest {

    @Given("the delete dialog is visible")
    public void clickDeleteButton(){
        Button applyButton = lookup("#deleteButton").query();
        clickOn(applyButton);
    }

    @When("delete the {int}. result")
    public void deleteTheResult(final int rowIndex) {
        tryToDelete(rowIndex);

        Button yesButton = lookup("#yesButton").query();
        clickOn(yesButton);
    }

    @Then("the dialog text is {string}")
    public void theDialogTextIs(String text) {
        Stage actualAlertDialog = getTopModalStage();
        DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        String contentText = dialogPane.getContentText();

        assertEquals(text, contentText);
    }

    @When("try to delete the {int}. row")
    public void tryToDelete(final int rowIndex) {
        selectRow(rowIndex);
        clickDeleteButton();
    }
}
