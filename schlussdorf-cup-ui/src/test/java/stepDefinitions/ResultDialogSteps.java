package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javafx.scene.image.ImageView;
import com.schlussdorf.model.Result;
import org.assertj.core.api.Assertions;

import java.util.List;

public class ResultDialogSteps extends BaseApplicationTest {

    @Given("result dialog is open")
    public void openResultDialog(){
        clickOnNode("add button");
    }

    @Given("The {word} with {string} is (in)valid")
    public void theTextFieldWithInvalidInputIsInvalid(final String textFieldId, final String invalidInput) {
        writeInputToTextField(invalidInput, textFieldId);
        clickOnNode("apply button");
    }

    @Given("image {string} is selected")
    public void imageIsSelected(final String imageName) {
        selectImage(imageName);
    }

    @Then("the image view is empty")
    public void theImageViewIsEmpty() {
        ImageView imageView = lookup("#image").query();
        Assertions.assertThat(imageView.getImage()).isNull();
    }

    @When("add the following result to the table")
    public void addTheFollowingResultToTheTable(List<Result> resultList) {
        resultList.forEach(result ->{
            clickOnNode("add button");

            writeInputToTextField(result.getFireDepartment(), "fire department text field");
            writeInputToTextField(String.valueOf(result.getTime()), "time text field");
            writeInputToTextField(String.valueOf(result.getMistakePoints()), "mistake points text field");
            selectImage("test_image_1.jpeg");

            clickOnNode("apply button");
        });

    }

    @When("reopen the result dialog")
    public void reopenTheResultDialog() {
        clickOnNode("cancel button");
        clickOnNode("add button");
    }
}
