package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import org.assertj.core.api.Assertions;
import org.openjfx.App;
import util.TestUtil;

import static org.junit.Assert.*;
import static org.testfx.assertions.api.Assertions.assertThat;

public class GeneralSteps extends BaseApplicationTest{

    @Before
    public void setUp() throws Exception {
        TestUtil.clearFolders();
        launch(App.class);
    }

    @After
    public void after() {
        while(!listWindows().isEmpty()){
            closeCurrentWindow();
        }
    }

    @Given("the user is on the {string}")
    public void userIsOnMainPage(final String page){

    }

    @Given("the {string} has text {string}")
    public void textFieldHasText(final String textFieldName, final String text) {
        writeInputToTextField(text, textFieldName);
    }

    @Given("the {string} has bean clicked")
    public void hasBeanClicked(final String name){
        clickOnNode(name);
    }

    @When("click on {string}")
    public void clickOnNode2(final String name) {
       clickOnNode(name);
    }

    @Then("the {string} is enabled")
    public void theIsEnabled(final String name) {
        Node node = findNodeByName(name);
        assertFalse(node.isDisabled());
    }

    @Then("the {string} is disabled")
    public void theIsDisabled(final String name) {
        Node node = findNodeByName(name);
        assertTrue(node.isDisabled());
    }

    @Then("the {string} is visible")
    public void isVisible(final String name) {
        if(name.endsWith("dialog")){
            assertEquals(2, listWindows().size());
        }else{
            Node node = findNodeByName(name);
            assertThat(node).isVisible();
        }
    }

    @Then("the {string} is invisible")
    public void isInvisible(final String name) {
        if(name.endsWith("dialog")){
            assertEquals(1,listWindows().size());
        }else{
            Node node = findNodeByName(name);
            assertThat(node).isInvisible();
        }
    }

    @Then("the {string} is focused")
    public void theFireDepartmentTextFieldIsFocused(final String textFieldName) {
        TextField textField = (TextField) findNodeByName(textFieldName);
        assertThat(textField).isFocused();
    }

    @Then("The {string} is empty")
    public void theTextFieldIsEmpty(final String textFieldName) {
        TextField textField = (TextField) findNodeByName(textFieldName);
        Assertions.assertThat(textField.getText()).isNullOrEmpty();
    }
}