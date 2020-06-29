package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import javafx.scene.control.TableView;
import model.Result;
import org.openjfx.constants.FileConstants;
import org.openjfx.constants.FolderConstants;
import service.SaveService;
import util.TestUtil;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;

public class ResultTableSteps extends BaseApplicationTest {

    private final SaveService saveService = new SaveService();

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

    @Given("the {int}. row of the result table is selected")
    public void theRowOfTheResultTableIsSelected(final int rowIndex){
       selectRow(rowIndex);
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