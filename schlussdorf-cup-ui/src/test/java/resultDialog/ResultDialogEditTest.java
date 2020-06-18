package resultDialog;

import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import model.Result;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openjfx.App;
import org.testfx.framework.junit.ApplicationTest;
import util.TestUtil;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ResultDialogEditTest extends ApplicationTest {

    private TextField fireDepartmentTextField;

    private TextField timeTextField;

    private TextField mistakePointsTextField;

    private Button imageButton;

    private Button applyButton;

    private TableView<Result> resultTable;

    @BeforeClass
    public static void initialize() throws IOException {
        TestUtil.clearFolders();
    }

    @Before
    public void setUp() throws Exception {
        TestUtil.loadTestSetup2();

        launch(App.class);

        resultTable = lookup("#table").query();
        TableRow<Result> firstRow = lookup(".table-row-cell").nth(0).query();

        Button editButton = lookup("#editButton").query();
        clickOn(firstRow).clickOn(editButton);

        fireDepartmentTextField = lookup("#fireDepartmentTextField").query();
        timeTextField = lookup("#timeTextField").query();
        mistakePointsTextField = lookup("#mistakePointsTextField").query();

        imageButton = lookup("#customImageButton").query();

        applyButton = lookup("#applyButton").query();
    }

    @After
    public void tearDown() throws IOException {
        TestUtil.clearFolders();
    }

    @Test
    public void edit_fireDepartment_tableIsCorrect() {
        //arrange

        //act
        clickOn(fireDepartmentTextField).write(" edit")
                .clickOn(applyButton);

        //arrange
        assertEquals("Feuerwehr1 edit", resultTable.getItems().get(0).getFireDepartment());
    }

    @Test
    public void edit_time_tableIsCorrect() {
        //arrange

        //act
        clickOn(timeTextField)
                .press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE)
                .press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE)
                .press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE)
                .press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE)
                .write("10")
                .clickOn(applyButton);

        //arrange
        assertEquals(10, resultTable.getItems().get(0).getTime(), 0);
    }

    @Test
    public void edit_mistakePoints_tableIsCorrect() {
        //arrange

        //act
        clickOn(mistakePointsTextField).press(KeyCode.BACK_SPACE).write("10")
                .clickOn(applyButton);

        //arrange
        assertEquals(10, resultTable.getItems().get(0).getMistakePoints());
    }

    @Test
    public void edit_image_tableIsCorrect() {
        //arrange

        //act
        clickOn(fireDepartmentTextField).write(" edit")
                .clickOn(applyButton);

        //arrange
        assertEquals("Feuerwehr1 edit", resultTable.getItems().get(0).getFireDepartment());
    }

    @Test
    public void edit_mistakePoints_finalScoreIsCorrect() {
        //arrange

        //act
        clickOn(mistakePointsTextField).press(KeyCode.BACK_SPACE).write("10")
                .clickOn(applyButton);

        //arrange
        assertEquals(430, resultTable.getItems().get(0).getFinalScore(), 0);
    }

    @Test
    public void edit_time_finalScoreIsCorrect() {
        //arrange

        //act
        clickOn(timeTextField)
                .press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE)
                .press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE)
                .press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE)
                .press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE)
                .write("10")
                .clickOn(applyButton);

        //arrange
        assertEquals(490, resultTable.getItems().get(0).getFinalScore(), 0);
    }

    @Test
    public void edit_image_imageIsUpdated() throws IOException {
        //arrange
        File testImage = new File(ResultDialogReopenTest.class.getResource("/images/test_image_1.jpeg").getFile());
        TestUtil.setClipBoardContent(testImage.getAbsolutePath());

        //act
        clickOn(imageButton).press(KeyCode.CONTROL, KeyCode.V).release(KeyCode.V, KeyCode.CONTROL).push(KeyCode.ENTER)
                .clickOn(applyButton);

        //arrange
        assertTrue(FileUtils.contentEquals(testImage, resultTable.getItems().get(0).getImage()));
    }

    @Test
    public void edit_finalScore_placeIsUpdated() {
        //arrange

        //act
        clickOn(mistakePointsTextField).press(KeyCode.BACK_SPACE).write("100")
                .clickOn(applyButton);

        //arrange
        assertEquals(1, resultTable.getItems().get(0).getPlace());
        assertEquals("Feuerwehr2", resultTable.getItems().get(0).getFireDepartment());

        assertEquals(2, resultTable.getItems().get(1).getPlace());
        assertEquals("Feuerwehr1", resultTable.getItems().get(1).getFireDepartment());
    }
}
