package stepDefinitions;

import com.google.common.io.Files;
import exception.CsvFormatException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import model.Result;
import org.apache.commons.io.FileUtils;
import org.openjfx.constants.FileConstants;
import org.openjfx.constants.FolderConstants;
import service.LoadService;
import service.SaveService;
import util.TestUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.fail;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ResultTableSteps extends BaseApplicationTest {

    private final SaveService saveService = new SaveService();

    private final LoadService loadService = new LoadService();

    @Given("the result table is empty")
    public void theResultTableIsEmpty(){
        TestUtil.clearFolders();
    }

    @Given("the result table has the following results")
    public void theResultTableHasTheFollowingResults(List<Result> resultList) throws IOException {
        saveService.save(resultList, FolderConstants.SAVE_FOLDER + FileConstants.SAVE_FILE);

        TableView<Result> resultTable = lookup("#table").query();
        resultTable.getItems().addAll(resultList);
    }

    @Given("the save csv file is invalid")
    public void invalidSaveCsvFile() throws IOException {
        File saveFolder = new File(FolderConstants.SAVE_FOLDER);
        if(!saveFolder.exists()){
            saveFolder.mkdir();
        }

        File invalidSaveFile = new File(ResultTableSteps.class.getResource("/save/invalid_save_file.csv").getFile());
        File saveFile = new File(FolderConstants.SAVE_FOLDER + FileConstants.SAVE_FILE);
//        if(!saveFile.exists()){
//            saveFile.createNewFile();
//        }
        Files.copy(invalidSaveFile, saveFile);
    }

    @Given("{string} has no image")
    public void hasNoImage(final String fireDepartment) throws IOException, CsvFormatException {
        List<Result> resultList = loadService.load(FolderConstants.SAVE_FOLDER + FileConstants.SAVE_FILE);
        resultList.stream()
                .filter(r -> r.getFireDepartment().equals(fireDepartment))
                .findFirst()
                .ifPresent(r -> r.getImage().delete());
    }

    @Given("(again )the {int}. row of the result table is selected")
    public void theRowOfTheResultTableIsSelected(final int rowIndex){
       selectRow(rowIndex);
    }

    @Given("the save csv file has the following results")
    public void theSaveCsvFileHasTheFollowingResults(List<Result> resultList) throws IOException {
        saveService.save(resultList, FolderConstants.SAVE_FOLDER + FileConstants.SAVE_FILE);
    }

    @Then("the {int}. row is not selected")
    public void isRowNotSelected(final int rowIndex){
        await().atMost(60, TimeUnit.SECONDS).until(() -> Objects.nonNull(lookup(".table-row-cell")));
        TableRow<Result> row = lookup(".table-row-cell").nth(rowIndex-1).query();
        assertFalse(row.isSelected());
    }

    @Then("the result table should look like")
    public void theRowInTheResultTableContainsResultWith(List<Result> resultList) {
        TableView<Result> resultTable = lookup("#table").query();
        List<Result> resultTableList = resultTable.getItems();

        if(resultTableList.size() != resultList.size()){
            fail("result table size and expected size are different");
        }else{
            for(int i = 0; i < resultTableList.size(); i++){
                Result tableResult = resultTableList.get(i);
                Result expectedResult = resultList.get(i);

                assertEquals(expectedResult.getPlace(), tableResult.getPlace());
                assertEquals(expectedResult.getFireDepartment(), tableResult.getFireDepartment());
                assertEquals(expectedResult.getTime(), tableResult.getTime(), 0);
                assertEquals(expectedResult.getMistakePoints(), tableResult.getMistakePoints());
                assertEquals(expectedResult.getFinalScore(), tableResult.getFinalScore(), 0);
            }
        }
    }
}