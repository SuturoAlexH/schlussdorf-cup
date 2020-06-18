package resultTable;

import javafx.scene.control.*;
import model.Result;
import org.junit.Before;
import org.junit.Test;
import org.openjfx.App;
import org.testfx.framework.junit.ApplicationTest;
import util.TestUtil;

import static org.junit.Assert.*;

public class ResultTableTest extends ApplicationTest {

    private TableView<Result> resultTable;

    @Before
    public void setUp() {
        //TestUtil.deleteImageFolder();
        TestUtil.deleteSaveFile();
    }

    @Test
    public void initialState_saveFileDoesNotExists_tableIsEmpty() throws Exception {
        //arrange

        //act
        launch(App.class);

        //assert
        resultTable = lookup("#table").query();
        assertEquals(0, resultTable.getItems().size());
    }

    @Test
    public void initialState_saveFileIsEmpty_tableIsEmpty() throws Exception {
        //arrange
        TestUtil.loadTestSetup0();

        //act
        launch(App.class);

        //assert
        resultTable = lookup("#table").query();
        assertEquals(0, resultTable.getItems().size());
    }

    @Test
    public void initialState_singleEntryInSaveFile_tableContainsOneEntry() throws Exception {
        //arrange
        TestUtil.loadTestSetup1();

        //act
        launch(App.class);

        //assert
        resultTable = lookup("#table").query();
        assertEquals(1, resultTable.getItems().size());
    }

    @Test
    public void initialState_twoEntriesInSaveFile_tableContainsTwoEntries() throws Exception {
        //arrange
        TestUtil.loadTestSetup2();

        //act
        launch(App.class);

        //assert
        resultTable = lookup("#table").query();
        assertEquals(2, resultTable.getItems().size());
    }

    @Test
    public void table_select_resultIsSelected() throws Exception {
        //arrange
        TestUtil.loadTestSetup1();

        launch(App.class);

        TableRow<Result> firstRow = lookup(".table-row-cell").nth(0).query();

        //act
        clickOn(firstRow);

        //assert
        assertTrue(firstRow.isSelected());
    }

    @Test
    public void table_deselect_resultIsDeselected() throws Exception {
        //arrange
        TestUtil.loadTestSetup1();

        launch(App.class);

        TableRow<Result> firstRow = lookup(".table-row-cell").nth(0).query();

        clickOn(firstRow);

        //act
        clickOn(firstRow);

        //assert
        assertFalse(firstRow.isSelected());
    }

    @Test
    public void table_selectOtherResult_otherResultIsSelected() throws Exception {
        //arrange
        TestUtil.loadTestSetup2();

        launch(App.class);

        TableRow<Result> firstRow = lookup(".table-row-cell").nth(0).query();
        TableRow<Result> secondRow = lookup(".table-row-cell").nth(1).query();

        clickOn(firstRow);

        //act
        clickOn(secondRow);

        //assert
        assertFalse(firstRow.isSelected());
        assertTrue(secondRow.isSelected());
    }
}
