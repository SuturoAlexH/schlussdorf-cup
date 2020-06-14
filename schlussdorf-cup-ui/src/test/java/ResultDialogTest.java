import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Window;
import org.junit.Before;
import org.junit.Test;
import org.openjfx.App;
import org.testfx.api.FxRobotInterface;
import org.testfx.framework.junit.ApplicationTest;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;

import static org.hamcrest.Matchers.*;
import static org.testfx.assertions.api.Assertions.assertThat;

public class ResultDialogTest extends ApplicationTest {

    private Window window;

    private Button addButton;

    private TextField fireDepartmentTextField;

    private TextField mistakePointsTextField;

    private TextField timeTextField;

    private Label fireDepartmentErrorText;

    private Label timeErrorText;

    private Label mistakePointsErrorText;

    private Label imageErrorText;

    private ImageView imageView;

    private Button imageButton;

    private Button applyButton;

    private Button cancelButton;

    @Before
    public void setUp() throws Exception {
        launch(App.class);

        addButton = lookup("#addButton").query();
        clickOn(addButton);

        window = listWindows().get(1);

        fireDepartmentTextField = lookup("#fireDepartmentTextField").query();
        mistakePointsTextField = lookup("#mistakePointsTextField").query();
        timeTextField = lookup("#timeTextField").query();

        fireDepartmentErrorText = lookup("#fireDepartmentErrorLabel").query();
        timeErrorText = lookup("#timeErrorLabel").query();
        mistakePointsErrorText = lookup("#mistakePointsErrorLabel").query();
        imageErrorText = lookup("#imageErrorLabel").query();

        imageView = lookup("#image").query();

        imageButton = lookup("#imageButton").query();
        applyButton = lookup("#applyButton").query();
        cancelButton = lookup("#cancelButton").query();
    }

    private void pasteClipBoardAndPressEnter(final FxRobotInterface i, final File file){
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(file.getAbsolutePath());
        clipboard.setContents(stringSelection, stringSelection);

        i.press(KeyCode.CONTROL).
                press(KeyCode.V).
                release(KeyCode.V).
                release(KeyCode.CONTROL).
                push(KeyCode.ENTER);
    }

    @Test
    public void fireDepartment_initialState_textFieldIsFocused() {
        //arrange

        //act

        //assert
        assertThat(fireDepartmentTextField).isFocused();
    }

    @Test
    public void fireDepartment_initialState_textFieldIsEmpty() {
        //arrange

        //act

        //assert
        assertThat(fireDepartmentTextField.getText()).isNull();
    }

    @Test
    public void fireDepartment_initialState_errorTextIsInvisible() {
        //arrange

        //act

        //assert
        assertThat(fireDepartmentErrorText).isInvisible();
    }

    @Test
    public void mistakePoints_initialState_textFieldIsEmpty() {
        //arrange

        //act

        //assert
        assertThat(mistakePointsTextField.getText()).isNull();
    }

    @Test
    public void mistakePoints_initialState_errorTextIsInvisible() {
        //arrange

        //act

        //assert
        assertThat(mistakePointsTextField.getText()).isNull();
    }

    @Test
    public void time_initialState_textFieldIsEmpty() {
        //arrange

        //act

        //assert
        assertThat(timeTextField.getText()).isNull();
    }

    @Test
    public void time_initialState_errorTextIsInvisible() {
        //arrange

        //act

        //assert
        assertThat(timeErrorText).isInvisible();
    }

    @Test
    public void image_initialState_imageIsEmpty() {
        //arrange

        //act

        //assert
        assertThat(imageView.getImage()).isNull();
    }

    @Test
    public void image_initialState_errorTextIsInvisible() {
        //arrange

        //act

        //assert
        assertThat(imageErrorText).isInvisible();
    }

    // validation
    @Test
    public void fireDepartment_textFieldIsEmpty_errorTextIsVisible() {
        //arrange

        // Act
        clickOn(applyButton);

        // Assert
        assertThat(fireDepartmentErrorText).isVisible();
    }

    @Test
    public void time_textFieldIsEmpty_errorTextIsVisible() {
        //arrange

        //act
        clickOn(applyButton);

        //assert
        assertThat(timeErrorText).isVisible();
    }

    @Test
    public void time_letters_errorTextIsVisible() {
        //arrange
        clickOn(timeTextField).write("1a");

        //act
        clickOn(applyButton);

        //assert
        assertThat(timeErrorText).isVisible();
    }

    @Test
    public void mistakePoints_letters_errorTextIsVisible() {
        //arrange
        clickOn(mistakePointsTextField).write("1a");

        //act
        clickOn(applyButton);

        //assert
        assertThat(mistakePointsErrorText).isVisible();
    }

    @Test
    public void mistakePoints_floatingWithComma_errorTextIsVisible() {
        //arrange
        clickOn(mistakePointsTextField).write("1,1");

        //act
        clickOn(applyButton);

        //assert
        assertThat(mistakePointsErrorText).isVisible();
    }

    @Test
    public void mistakePoints_floatingWithDot_errorTextIsVisible() {
        //arrange
        clickOn(mistakePointsTextField).write("1.1");

        //act
        clickOn(applyButton);

        //assert
        assertThat(mistakePointsErrorText).isVisible();
    }

    @Test
    public void image_imageViewIsEmpty_errorTextIsVisible() {
        //arrange

        //act
        clickOn(applyButton);

        //assert
        assertThat(imageErrorText).isVisible();
    }

    @Test
    public void fireDepartment_validInput_errorTextIsInvisible() {
        //arrange
        clickOn(fireDepartmentTextField).write("Feuerwehr");

        //act
        clickOn(applyButton);

        //assert
        assertThat(fireDepartmentErrorText).isInvisible();
    }

    @Test
    public void time_validInputComma_errorTextIsInvisible() {
        //arrange
        clickOn(timeTextField).write("1,1");

        //act
        clickOn(applyButton);

        //assert
        assertThat(timeErrorText).isInvisible();
    }

    @Test
    public void time_validInputDot_errorTextIsInvisible() {
        //arrange
        clickOn(timeTextField).write("1.1");

        //act
        clickOn(applyButton);

        // Assert
        assertThat(timeErrorText).isInvisible();
    }

    @Test
    public void mistakePoints_validInput_errorTextIsInvisible() {
        //arrange
        clickOn(mistakePointsTextField).write("10");

        //act
        clickOn(applyButton);

        //assert
        assertThat(mistakePointsErrorText).isInvisible();
    }

    @Test
    public void image_validInput_errorTextIsInvisible() {
        //arrange
        File testImage = new File(ResultDialogTest.class.getResource("/images/test_image_2.jpg").getFile());

        //act
        pasteClipBoardAndPressEnter(clickOn(imageButton), testImage);

        //arrange
        assertThat(imageErrorText).isInvisible();
    }

    @Test
    public void image_validInput_imageIsSet() {
        //arrange
        File testImage = new File(ResultDialogTest.class.getResource("/images/test_image_2.jpg").getFile());

        //act
        pasteClipBoardAndPressEnter(clickOn(imageButton), testImage);

        //arrange
        assertThat(imageView).isNotNull();
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
        clickOn(fireDepartmentTextField).write("Feuerwehr");
        clickOn(timeTextField).write("60.5");

        File testImage = new File(ResultDialogTest.class.getResource("/images/test_image_2.jpg").getFile());
        pasteClipBoardAndPressEnter(clickOn(imageButton), testImage);

        //act
        clickOn(applyButton);

        //arrange
        assertThat(window).isNotShowing();
    }

    @Test
    public void cancel_normal_windowIsClosed() {
        //arrange

        //act
        clickOn(cancelButton);

        //arrange
        assertThat(window).isNotShowing();
    }

    @Test
    public void windowCrossButton_normal_windowIsClosed() {
        //arrange

        //act
        closeCurrentWindow();

        //arrange
        assertThat(window).isNotShowing();
    }

    @Test
    public void reopenCancelButton_fireDepartment_textFieldIsEmpty() {
        //arrange

        //act
       clickOn(applyButton).clickOn(cancelButton).clickOn(addButton);

        //arrange
        assertThat(fireDepartmentTextField).hasText(isEmptyOrNullString());
    }

    @Test
    public void reopenCancelButton_fireDepartment_errorTextIsInvisible() {
        //arrange

        //act
        clickOn(applyButton).clickOn(cancelButton).clickOn(addButton);

        //arrange
        assertThat(fireDepartmentErrorText).isInvisible();
    }

    @Test
    public void reopenCancelButton_time_textFieldIsEmpty() {
        //arrange

        //act
        clickOn(timeTextField)
                .write("letters")
                .clickOn(applyButton)
                .clickOn(cancelButton)
                .clickOn(addButton);

        //arrange
        assertThat(fireDepartmentTextField).hasText(isEmptyOrNullString());
    }

    @Test
    public void reopenCancelButton_time_errorTextIsInvisible() {
        //arrange

        //act
        clickOn(timeTextField)
                .write("letters")
                .clickOn(applyButton)
                .clickOn(cancelButton)
                .clickOn(addButton);

        //arrange
        assertThat(timeErrorText).isInvisible();
    }

    @Test
    public void reopenCancelButton_mistakePoints_textFieldIsEmpty() {
        //arrange

        //act
        clickOn(mistakePointsTextField)
                .write("letters")
                .clickOn(applyButton)
                .clickOn(cancelButton)
                .clickOn(addButton);

        //arrange
        assertThat(mistakePointsTextField).hasText(isEmptyOrNullString());
    }

    @Test
    public void reopenCancelButton_mistakePoints_errorTextIsInvisible() {
        //arrange

        //act
        clickOn(mistakePointsTextField)
                .write("letters")
                .clickOn(applyButton)
                .clickOn(cancelButton)
                .clickOn(addButton);

        //arrange
        assertThat(mistakePointsErrorText).isInvisible();
    }

//    @Test
//    public void reopenCancelButton_image_imageViewIsEmpty() {
//        //arrange
//
//        //act
//        clickOn(timeTextField)
//                .write("letters")
//                .clickOn(applyButton)
//                .clickOn(cancelButton)
//                .clickOn(addButton);
//
//        //arrange
//        assertThat(imageView).isNull();
//    }
//
//    @Test
//    public void reopenCancelButton_image_errorTextIsInvisible() {
//        //arrange
//
//        //act
//        clickOn(timeTextField)
//                .write("letters")
//                .clickOn(applyButton)
//                .clickOn(cancelButton)
//                .clickOn(addButton);
//
//        //arrange
//        assertThat(imageErrorText).isInvisible();
//    }
}