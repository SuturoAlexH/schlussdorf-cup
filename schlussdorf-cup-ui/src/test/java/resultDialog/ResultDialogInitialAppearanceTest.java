package resultDialog;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openjfx.App;
import org.testfx.framework.junit.ApplicationTest;
import util.TestUtil;

import java.io.IOException;

import static org.testfx.assertions.api.Assertions.assertThat;

public class ResultDialogInitialAppearanceTest extends ApplicationTest {

    private TextField fireDepartmentTextField;

    private TextField mistakePointsTextField;

    private TextField timeTextField;

    private Label fireDepartmentErrorText;

    private Label timeErrorText;

    private Label mistakePointsErrorText;

    private Label imageErrorText;

    private ImageView imageView;

    @BeforeClass
    public static void initialize() throws IOException {
        TestUtil.clearFolders();
    }

    @Before
    public void setUp() throws Exception {
        launch(App.class);

        Button addButton = lookup("#addButton").query();
        clickOn(addButton);

        fireDepartmentTextField = lookup("#fireDepartmentTextField").query();
        mistakePointsTextField = lookup("#mistakePointsTextField").query();
        timeTextField = lookup("#timeTextField").query();

        fireDepartmentErrorText = lookup("#fireDepartmentErrorLabel").query();
        timeErrorText = lookup("#timeErrorLabel").query();
        mistakePointsErrorText = lookup("#mistakePointsErrorLabel").query();
        imageErrorText = lookup("#imageErrorLabel").query();

        imageView = lookup("#image").query();
    }

    @After
    public void tearDown() throws IOException {
        TestUtil.clearFolders();
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
        Assertions.assertThat(fireDepartmentTextField.getText()).isNull();
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
        Assertions.assertThat(mistakePointsTextField.getText()).isNull();
    }

    @Test
    public void mistakePoints_initialState_errorTextIsInvisible() {
        //arrange

        //act

        //assert
        assertThat(mistakePointsErrorText).isInvisible();
    }

    @Test
    public void time_initialState_textFieldIsEmpty() {
        //arrange

        //act

        //assert
        Assertions.assertThat(timeTextField.getText()).isNull();
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
        Assertions.assertThat(imageView.getImage()).isNull();
    }

    @Test
    public void image_initialState_errorTextIsInvisible() {
        //arrange

        //act

        //assert
        assertThat(imageErrorText).isInvisible();
    }
}