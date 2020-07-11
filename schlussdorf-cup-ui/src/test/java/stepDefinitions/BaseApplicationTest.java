package stepDefinitions;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.Window;
import com.schlussdorf.model.Result;
import org.apache.commons.text.CaseUtils;
import com.schlussdorf.App;
import org.testfx.framework.junit.ApplicationTest;
import com.schlussdorf.util.TestUtil;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

public abstract class BaseApplicationTest extends ApplicationTest {

    public void launchApplication() throws Exception {
        launch(App.class);
    }

    public void selectImage(final String imageName) {
        File testImage = new File(BaseApplicationTest.class.getResource("/images/" + imageName).getFile());
        TestUtil.setClipBoardContent(testImage.getAbsolutePath());

        Button imageButton = lookup("#selectImageButton").query();
        clickOn(imageButton)
                .press(KeyCode.CONTROL, KeyCode.V)
                .release(KeyCode.V, KeyCode.CONTROL)
                .push(KeyCode.ENTER);
    }

    public void selectRow(final int rowIndex){
        await().atMost(60, TimeUnit.SECONDS).until(() -> Objects.nonNull(lookup(".table-row-cell")));
        TableRow<Result> row = lookup(".table-row-cell").nth(rowIndex-1).query();
        clickOn(row);
    }

    public void writeInputToTextField(final String input, final String textFieldName) {
        TextField textField = (TextField) findNodeByName(textFieldName);
        doubleClickOn(textField).clickOn(textField).write(input);
    }

    public void clickOnNode(final String name) {
        Node node = findNodeByName(name);
        clickOn(node);
    }

    public Node findNodeByName(final String name){
        String id = CaseUtils.toCamelCase(name, false);
        return lookup("#" + id).query();
    }

    public Stage getTopModalStage() {
        final List<Window> windowList = listWindows();
        if(windowList.isEmpty()){
            return null;
        }

        return (Stage) windowList.get(windowList.size()-1);
    }
}
