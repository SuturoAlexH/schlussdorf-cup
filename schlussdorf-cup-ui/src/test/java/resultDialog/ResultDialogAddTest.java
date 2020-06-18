package resultDialog;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import model.Result;
import org.junit.Before;
import org.junit.Test;
import org.openjfx.App;
import org.testfx.framework.junit.ApplicationTest;
import util.TestUtil;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class ResultDialogAddTest extends ApplicationTest {

    private Button addButton;

    private TextField fireDepartmentTextField;

    private TextField timeTextField;

    private Button imageButton;

    private Button applyButton;

    private TableView<Result> resultTable;

    @Before
    public void setUp() throws Exception {
        TestUtil.deleteSaveFile();
        //TestUtil.deleteImageFolder();

        launch(App.class);

        addButton = lookup("#addButton").query();
        clickOn(addButton);

        fireDepartmentTextField = lookup("#fireDepartmentTextField").query();
        timeTextField = lookup("#timeTextField").query();

        imageButton = lookup("#customImageButton").query();

        applyButton = lookup("#applyButton").query();

        resultTable = lookup("#table").query();

        File testImage = new File(ResultDialogAddTest.class.getResource("/images/test_image_2.jpg").getFile());
        TestUtil.setClipBoardContent(testImage.getAbsolutePath());
    }

    @Test
    public void add_emptyTable_tableContainsOneEntry() {
        //arrange

        //act
        clickOn(fireDepartmentTextField).write("Feuerwehr1")
                .clickOn(timeTextField).write("10")
                .clickOn(imageButton).press(KeyCode.CONTROL, KeyCode.V).release(KeyCode.V, KeyCode.CONTROL).push(KeyCode.ENTER)
                .clickOn(applyButton);

        //arrange
        assertEquals(1,resultTable.getItems().size());
    }

    @Test
    public void add_emptyTable_placeIsCorrectInTable() {
        //arrange

        //act
        clickOn(fireDepartmentTextField).write("Feuerwehr1")
                .clickOn(timeTextField).write("10")
                .clickOn(imageButton).press(KeyCode.CONTROL, KeyCode.V).release(KeyCode.V, KeyCode.CONTROL).push(KeyCode.ENTER)
                .clickOn(applyButton);

        //arrange
        assertEquals(1, resultTable.getItems().get(0).getPlace());
    }

    @Test
    public void add_emptyTable_fireDepartmentIsCorrectInTable() {
        //arrange

        //act
        clickOn(fireDepartmentTextField).write("Feuerwehr1")
                .clickOn(timeTextField).write("10")
                .clickOn(imageButton).press(KeyCode.CONTROL, KeyCode.V).release(KeyCode.V, KeyCode.CONTROL).push(KeyCode.ENTER)
                .clickOn(applyButton);

        //arrange
        assertEquals("Feuerwehr1", resultTable.getItems().get(0).getFireDepartment());
    }

    @Test
    public void add_emptyTable_mistakePointsIsCorrectInTable() {
        //arrange

        //act
        clickOn(fireDepartmentTextField).write("Feuerwehr1")
                .clickOn(timeTextField).write("10")
                .clickOn(imageButton).press(KeyCode.CONTROL, KeyCode.V).release(KeyCode.V, KeyCode.CONTROL).push(KeyCode.ENTER)
                .clickOn(applyButton);

        //arrange
        assertEquals(0, resultTable.getItems().get(0).getMistakePoints());
    }

    @Test
    public void add_emptyTable_timeIsCorrectInTable() {
        //arrange

        //act
        clickOn(fireDepartmentTextField).write("Feuerwehr1")
                .clickOn(timeTextField).write("10")
                .clickOn(imageButton).press(KeyCode.CONTROL, KeyCode.V).release(KeyCode.V, KeyCode.CONTROL).push(KeyCode.ENTER)
                .clickOn(applyButton);

        //arrange
        assertEquals(10, resultTable.getItems().get(0).getTime(), 0);
    }

    @Test
    public void add_emptyTable_finalScoreIsCorrectInTable() {
        //arrange

        //act
        clickOn(fireDepartmentTextField).write("Feuerwehr1")
                .clickOn(timeTextField).write("10")
                .clickOn(imageButton).press(KeyCode.CONTROL, KeyCode.V).release(KeyCode.V, KeyCode.CONTROL).push(KeyCode.ENTER)
                .clickOn(applyButton);

        //arrange
        assertEquals(490, resultTable.getItems().get(0).getFinalScore(), 0);
    }

    @Test
    public void add_emptyTableCreateTwoEntries_tableContainsTwoEntries() {
        //arrange

        //act
        clickOn(fireDepartmentTextField).write("Feuerwehr1")
                .clickOn(timeTextField).write("10")
                .clickOn(imageButton).press(KeyCode.CONTROL, KeyCode.V).release(KeyCode.V, KeyCode.CONTROL).push(KeyCode.ENTER)
                .clickOn(applyButton)

                .clickOn(addButton);

        clickOn(fireDepartmentTextField).write("Feuerwehr2")
                .clickOn(timeTextField).write("20")
                .clickOn(imageButton).press(KeyCode.CONTROL, KeyCode.V).release(KeyCode.V, KeyCode.CONTROL).push(KeyCode.ENTER)
                .clickOn(applyButton);

        //arrange
        assertEquals(2, resultTable.getItems().size());
    }

    @Test
    public void add_threeEntries_placesAreCorrect() {
        //arrange

        //act
        clickOn(fireDepartmentTextField).write("Feuerwehr1")
                .clickOn(timeTextField).write("10")
                .clickOn(imageButton).press(KeyCode.CONTROL, KeyCode.V).release(KeyCode.V, KeyCode.CONTROL).push(KeyCode.ENTER)
                .clickOn(applyButton)

                .clickOn(addButton)

        .clickOn(fireDepartmentTextField).write("Feuerwehr2")
                .clickOn(timeTextField).write("20")
                .clickOn(imageButton).press(KeyCode.CONTROL, KeyCode.V).release(KeyCode.V, KeyCode.CONTROL).push(KeyCode.ENTER)
                .clickOn(applyButton)

                .clickOn(addButton)

        .clickOn(fireDepartmentTextField).write("Feuerwehr3")
                .clickOn(timeTextField).write("5")
                .clickOn(imageButton).press(KeyCode.CONTROL, KeyCode.V).release(KeyCode.V, KeyCode.CONTROL).push(KeyCode.ENTER)
                .clickOn(applyButton);

        //arrange
        assertEquals(1, resultTable.getItems().get(0).getPlace());
        assertEquals("Feuerwehr3",  resultTable.getItems().get(0).getFireDepartment());

        assertEquals(2, resultTable.getItems().get(1).getPlace());
        assertEquals("Feuerwehr1",  resultTable.getItems().get(1).getFireDepartment());

        assertEquals(3, resultTable.getItems().get(2).getPlace());
        assertEquals("Feuerwehr2",  resultTable.getItems().get(2).getFireDepartment());
    }
}
