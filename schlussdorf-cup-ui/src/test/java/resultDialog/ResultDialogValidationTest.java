package resultDialog;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Window;
import org.junit.Before;
import org.junit.Test;
import org.openjfx.App;
import org.testfx.framework.junit.ApplicationTest;
import util.TestUtil;

import java.io.File;

import static org.testfx.assertions.api.Assertions.assertThat;

public class ResultDialogValidationTest extends ApplicationTest {

    private Window window;

    private TextField fireDepartmentTextField;

    private TextField mistakePointsTextField;

    private TextField timeTextField;

    private Label fireDepartmentErrorText;

    private Label timeErrorText;

    private Label mistakePointsErrorText;

    private Label imageErrorText;

    private Button imageButton;

    private Button applyButton;

    @Before
    public void setUp() throws Exception {
        TestUtil.deleteSaveFile();
        TestUtil.deleteImageFolder();

        launch(App.class);

        Button addButton = lookup("#addButton").query();
        clickOn(addButton);

        window = listWindows().get(1);

        fireDepartmentTextField = lookup("#fireDepartmentTextField").query();
        mistakePointsTextField = lookup("#mistakePointsTextField").query();
        timeTextField = lookup("#timeTextField").query();

        fireDepartmentErrorText = lookup("#fireDepartmentErrorLabel").query();
        timeErrorText = lookup("#timeErrorLabel").query();
        mistakePointsErrorText = lookup("#mistakePointsErrorLabel").query();
        imageErrorText = lookup("#imageErrorLabel").query();


        imageButton = lookup("#customImageButton").query();
        applyButton = lookup("#applyButton").query();

    }

    // validation
    @Test
    public void fireDepartment_initialToInvalid_errorTextIsVisible() {
        //arrange

        // Act
        clickOn(applyButton);

        // Assert
        assertThat(fireDepartmentErrorText).isVisible();
    }

    @Test
    public void time_initialToInvalidTextFieldIsEmpty_errorTextIsVisible() {
        //arrange

        //act
        clickOn(applyButton);

        //assert
        assertThat(timeErrorText).isVisible();
    }

    @Test
    public void time_initialToInvalidLetters_errorTextIsVisible() {
        //arrange
        clickOn(timeTextField).write("1a");

        //act
        clickOn(applyButton);

        //assert
        assertThat(timeErrorText).isVisible();
    }

    @Test
    public void mistakePoints_initialToInvalidLetters_errorTextIsVisible() {
        //arrange
        clickOn(mistakePointsTextField).write("1a");

        //act
        clickOn(applyButton);

        //assert
        assertThat(mistakePointsErrorText).isVisible();
    }

    @Test
    public void mistakePoints_initialToInvalidFloatingWithComma_errorTextIsVisible() {
        //arrange
        clickOn(mistakePointsTextField).write("1,1");

        //act
        clickOn(applyButton);

        //assert
        assertThat(mistakePointsErrorText).isVisible();
    }

    @Test
    public void mistakePoints_initialToInvalidFloatingWithDot_errorTextIsVisible() {
        //arrange
        clickOn(mistakePointsTextField).write("1.1");

        //act
        clickOn(applyButton);

        //assert
        assertThat(mistakePointsErrorText).isVisible();
    }

    @Test
    public void image_initialToInvalid_errorTextIsVisible() {
        //arrange

        //act
        clickOn(applyButton);

        //assert
        assertThat(imageErrorText).isVisible();
    }

    @Test
    public void fireDepartment_initialToValid_errorTextIsInvisible() {
        //arrange
        clickOn(fireDepartmentTextField).write("Feuerwehr");

        //act
        clickOn(applyButton);

        //assert
        assertThat(fireDepartmentErrorText).isInvisible();
    }

    @Test
    public void time_initialToValidComma_errorTextIsInvisible() {
        //arrange
        clickOn(timeTextField).write("1,1");

        //act
        clickOn(applyButton);

        //assert
        assertThat(timeErrorText).isInvisible();
    }

    @Test
    public void time_initialToValidDot_errorTextIsInvisible() {
        //arrange
        clickOn(timeTextField).write("1.1");

        //act
        clickOn(applyButton);

        // Assert
        assertThat(timeErrorText).isInvisible();
    }

    @Test
    public void mistakePoints_initialToValid_errorTextIsInvisible() {
        //arrange
        clickOn(mistakePointsTextField).write("10");

        //act
        clickOn(applyButton);

        //assert
        assertThat(mistakePointsErrorText).isInvisible();
    }

    @Test
    public void image_initialToValid_errorTextIsInvisible() {
        //arrange
        File testImage = new File(ResultDialogValidationTest.class.getResource("/images/test_image_2.jpg").getFile());
        TestUtil.setClipBoardContent(testImage.getAbsolutePath());

        //act
        clickOn(imageButton).press(KeyCode.CONTROL, KeyCode.V).release(KeyCode.V, KeyCode.CONTROL).push(KeyCode.ENTER)
                .clickOn(applyButton);

        //arrange
        assertThat(imageErrorText).isInvisible();
    }

    @Test
    public void fireDepartment_fromInvalidToValid_errorTextIsInvisible() {
        //arrange
        clickOn(applyButton);

        //act
        clickOn(fireDepartmentTextField).write("Feuerwehr")
                .clickOn(applyButton);

        //arrange
        assertThat(fireDepartmentErrorText).isInvisible();
    }

    @Test
    public void fireDepartment_fromValidToInvalid_errorTextIsVisible() {
        //arrange
        clickOn(fireDepartmentTextField).write("F")
                .clickOn(applyButton);

        //act
        clickOn(fireDepartmentTextField).press(KeyCode.BACK_SPACE)
                .clickOn(applyButton);

        //arrange
        assertThat(fireDepartmentErrorText).isVisible();
    }

    @Test
    public void time_fromInvalidToValid_errorTextIsInvisible() {
        //arrange
        clickOn(applyButton);

        //act
        clickOn(timeTextField).write("1")
                .clickOn(applyButton);

        //arrange
        assertThat(timeErrorText).isInvisible();
    }

    @Test
    public void time_fromValidToInvalidEmpty_errorTextIsVisible() {
        //arrange
        clickOn(timeTextField).write("1")
                .clickOn(applyButton);

        //act
        clickOn(timeTextField).press(KeyCode.BACK_SPACE)
                .clickOn(applyButton);

        //arrange
        assertThat(timeErrorText).isVisible();
    }

    @Test
    public void time_fromValidToInvalidLetters_errorTextIsVisible() {
        //arrange
        clickOn(timeTextField).write("1")
                .clickOn(applyButton);

        //act
        clickOn(timeTextField).write("a")
                .clickOn(applyButton);

        //arrange
        assertThat(timeErrorText).isVisible();
    }

    @Test
    public void mistakePoints_fromInvalidToValidEmpty_errorTextIsInvisible() {
        //arrange
        clickOn(mistakePointsTextField).write("a")
                .clickOn(applyButton);

        //act
        clickOn(mistakePointsTextField).press(KeyCode.BACK_SPACE)
                .clickOn(applyButton);

        //arrange
        assertThat(mistakePointsErrorText).isInvisible();
    }

    @Test
    public void mistakePoints_fromInvalidToValidNumber_errorTextIsInvisible() {
        //arrange
        clickOn(mistakePointsTextField).write("a")
                .clickOn(applyButton);

        //act
        clickOn(mistakePointsTextField).press(KeyCode.BACK_SPACE).write("1")
                .clickOn(applyButton);

        //arrange
        assertThat(mistakePointsErrorText).isInvisible();
    }

    @Test
    public void mistakePoints_fromValidToInvalidEmpty_errorTextIsVisible() {
        //arrange
        clickOn(mistakePointsTextField).write("1")
                .clickOn(applyButton);

        //act
        clickOn(mistakePointsTextField).press(KeyCode.BACK_SPACE)
                .clickOn(applyButton);

        //arrange
        assertThat(mistakePointsErrorText).isVisible();
    }

    @Test
    public void mistakePoints_fromValidToInvalidLetters_errorTextIsVisible() {
        //arrange
        clickOn(mistakePointsTextField).write("1")
                .clickOn(applyButton);

        //act
        clickOn(mistakePointsTextField).write("a")
                .clickOn(applyButton);

        //arrange
        assertThat(mistakePointsErrorText).isVisible();
    }

    @Test
    public void apply_oneInvalidInput_windowIsNotClosed() {
        //arrange
        clickOn(fireDepartmentTextField).write("Feuerwehr");
        clickOn(timeTextField).write("60.5");

        //act
        clickOn(applyButton);

        //arrange
        assertThat(window).isShowing();
    }

    @Test
    public void apply_allInputsAreValid_windowIsClosed() {
        //arrange
        File testImage = new File(ResultDialogValidationTest.class.getResource("/images/test_image_2.jpg").getFile());
        TestUtil.setClipBoardContent(testImage.getAbsolutePath());

        clickOn(fireDepartmentTextField).write("Feuerwehr")
                .clickOn(timeTextField).write("60.5")
                .clickOn(imageButton).press(KeyCode.CONTROL, KeyCode.V).release(KeyCode.V, KeyCode.CONTROL).push(KeyCode.ENTER);

        //act
        clickOn(applyButton);

        //arrange
        assertThat(window).isNotShowing();
    }
}
