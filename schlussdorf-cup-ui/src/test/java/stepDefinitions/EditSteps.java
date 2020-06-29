package stepDefinitions;

import io.cucumber.java.en.When;

public class EditSteps extends BaseApplicationTest{

    @When("edit the {string} of the {int}. entry to {string}")
    public void edit(final String fieldName, final int rowIndex, final String value){
        selectRow(rowIndex);
        clickOnNode("edit button");
        writeInputToTextField(value, fieldName + " text field");
        clickOnNode("apply button");
    }

    @When("edit the image of the {int}. entry to {string}")
    public void edit(final int rowIndex, final String imageName){
        selectRow(rowIndex);
        clickOnNode("edit button");
        selectImage(imageName);
        clickOnNode("apply button");
    }
}
