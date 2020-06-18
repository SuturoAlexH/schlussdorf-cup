package resultTable;

import javafx.scene.control.*;
import model.Result;
import org.junit.Before;
import org.junit.Test;
import org.openjfx.App;
import org.testfx.framework.junit.ApplicationTest;
import util.TestUtil;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ResultTableTest extends ApplicationTest {

    private TableView<Result> resultTable;

    @Before
    public void setUp() throws IOException {
        TestUtil.deleteImageFolder();
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
}
