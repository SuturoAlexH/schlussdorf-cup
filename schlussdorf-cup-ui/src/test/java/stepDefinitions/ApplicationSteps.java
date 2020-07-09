package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ApplicationSteps extends BaseApplicationTest{

    @Given("the close application dialog is opened")
    public void closeApplicationDialogIsOpened(){
        closeCurrentWindow();
    }

    @When("try to close the application")
    public void tryToCloseTheApplication(){
        closeCurrentWindow();
    }

    @Then("the application is still running")
    public void applicationIsStillRunning(){
        assertFalse(listWindows().isEmpty());
    }

    @Then("the application is terminated")
    public void applicationIsTerminated(){
        assertTrue(listWindows().isEmpty());
    }
}
