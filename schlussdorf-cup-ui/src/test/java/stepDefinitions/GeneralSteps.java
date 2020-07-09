package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javafx.scene.Node;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.assertj.core.api.Assertions;
import util.TestUtil;

import static org.junit.Assert.*;
import static org.testfx.assertions.api.Assertions.assertThat;

public class GeneralSteps extends BaseApplicationTest{

    @Before("@normal")
    public void setUp() throws Exception {
        TestUtil.clearFolders();
        launchApplication();
    }

    @Before("@launchAfter")
    public void setUp2() {
        TestUtil.clearFolders();
    }

    @After
    public void after() {
        while(!listWindows().isEmpty()){
            closeCurrentWindow();

            Stage topModalStage = getTopModalStage();
            if(topModalStage != null && topModalStage.getScene().getRoot() instanceof DialogPane) {
                DialogPane dialogPane = (DialogPane) topModalStage.getScene().getRoot();

                if ("close application dialog".equals(dialogPane.getId())) {
                    clickOnNode("yes button");
                }
            }
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

    @When("application has started")
    public void applicationHasStarted() throws Exception {
        launchApplication();
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
            Stage actualAlertDialog = getTopModalStage();
            Node node = actualAlertDialog.getScene().getRoot();

            assertEquals(name, node.getId());
        }else{
            Node node = findNodeByName(name);
            assertThat(node).isVisible();
        }
    }

    @Then("the {string} is invisible")
    public void isInvisible(final String name) {
        if(name.endsWith("dialog")){
            Stage actualAlertDialog = getTopModalStage();
            Node node = actualAlertDialog.getScene().getRoot();

            assertNotEquals(name, node.getId());
        }else{
            Node node = findNodeByName(name);
            assertThat(node).isInvisible();
        }
    }

    @Then("the dialog text is {string}")
    public void theDialogTextIs(String text) {
        Stage actualAlertDialog = getTopModalStage();
        DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        String contentText = dialogPane.getContentText();

        assertEquals(text, contentText);
    }

    @Then("the dialog text starts with {string}")
    public void theDialogTextStartsWith(String text) {
        Stage actualAlertDialog = getTopModalStage();
        DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        String contentText = dialogPane.getContentText();

        assertTrue(contentText.startsWith(text));
    }

    @Then("the {string} is focused")
    public void textFieldIsFocused(final String textFieldName) {
        TextField textField = (TextField) findNodeByName(textFieldName);
        assertThat(textField).isFocused();
    }

    @Then("The {string} is empty")
    public void theTextFieldIsEmpty(final String textFieldName) {
        TextField textField = (TextField) findNodeByName(textFieldName);
        Assertions.assertThat(textField.getText()).isNullOrEmpty();
    }
}