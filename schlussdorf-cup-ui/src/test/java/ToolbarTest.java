import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Result;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.openjfx.App;
import org.openjfx.constants.Folders;
import org.testfx.framework.junit.ApplicationTest;
import service.LoadService;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class ToolbarTest extends ApplicationTest {

    private Button addButton;

    private Button deleteButton;

    private Button editButton;

    private Button imageButton;

    private Button certificateButton;

    private TableView<Result> resultTable;

    private LoadService loadService = new LoadService();

    @Before
    public void setUp() throws Exception {
        launch(App.class);

        addButton = lookup("#addButton").query();
        deleteButton = lookup("#deleteButton").query();
        editButton = lookup("#editButton").query();
        imageButton = lookup("#imageButton").query();
        certificateButton = lookup("#certificateButton").query();

        resultTable = lookup("#table").query();
    }

    private Stage getTopModalStage() {
        final List<Window> allWindows = listWindows();
        return (Stage) allWindows.get(1);
//        Collections.reverse(allWindows);
//
//        return (Stage) allWindows
//                .stream()
//                .filter(window -> window instanceof Stage)
//                .filter(window -> ((Stage) window).getModality() == Modality.APPLICATION_MODAL)
//                .findFirst()
//                .orElse(null);
    }

    @Test
    public void initial_normal_deleteButtonIsDisabled(){
        //arrange

        //act

        //assert
        assertTrue(deleteButton.isDisable());
    }

    @Test
    public void initial_normal_editButtonIsDisabled(){
        //arrange

        //act

        //assert
        assertTrue(editButton.isDisable());
    }

    @Test
    public void initial_normal_imageButtonIsDisabled(){
        //arrange

        //act

        //assert
        assertTrue(imageButton.isDisable());
    }

    @Test
    public void addButton_normal_resultDialogIsVisible(){
        //arrange

        //act
        clickOn(addButton);

        //assert
        assertEquals(2, listWindows().size());

        Window resultDialog = listWindows().get(1);
        assertTrue(resultDialog.isShowing());
    }

    @Test
    public void deleteButton_normal_correctText() throws IOException {
        //arrange
        File testSetupImageFile = new File(ResultDialogTest.class.getResource("/testSetup/746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg").getFile());
        File testSetupSaveFile = new File(ResultDialogTest.class.getResource("/testSetup/save.csv").getFile());

        FileUtils.copyFile(testSetupImageFile, new File(Folders.IMAGE_FOLDER + "746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg"));
        FileUtils.copyFile(testSetupSaveFile, new File(Folders.SAVE_FOLDER));

        TableRow firstRow = lookup(".table-row-cell").nth(0).query();

        //act
        clickOn(firstRow).clickOn(deleteButton);

        Stage actualAlertDialog = getTopModalStage();
        DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        String contentText = dialogPane.getContentText();

        //assert
        assertEquals("Soll das Ergebnis der Feuerwehr Feuerwehr wirklich gelöscht werden?", contentText);
    }

    @Test
    public void deleteButton_normal_confirmDialogIsVisible() throws IOException {
        //arrange
        File testSetupImageFile = new File(ResultDialogTest.class.getResource("/testSetup/746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg").getFile());
        File testSetupSaveFile = new File(ResultDialogTest.class.getResource("/testSetup/save.csv").getFile());

        FileUtils.copyFile(testSetupImageFile, new File(Folders.IMAGE_FOLDER + "746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg"));
        FileUtils.copyFile(testSetupSaveFile, new File(Folders.SAVE_FOLDER));

        TableRow firstRow = lookup(".table-row-cell").nth(0).query();

        //act
        clickOn(firstRow).clickOn(deleteButton);

        //assert
        assertEquals(2, listWindows().size());
    }

    @Test
    public void deleteButton_no_confirmDialogIsInvisible() throws IOException {
        //arrange
        File testSetupImageFile = new File(ResultDialogTest.class.getResource("/testSetup/746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg").getFile());
        File testSetupSaveFile = new File(ResultDialogTest.class.getResource("/testSetup/save.csv").getFile());

        FileUtils.copyFile(testSetupImageFile, new File(Folders.IMAGE_FOLDER + "746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg"));
        FileUtils.copyFile(testSetupSaveFile, new File(Folders.SAVE_FOLDER));

        TableRow firstRow = lookup(".table-row-cell").nth(0).query();

        //act
        clickOn(firstRow).clickOn(deleteButton);

        final Stage actualAlertDialog = getTopModalStage();
        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        Button noButton = (Button)dialogPane.lookupButton(ButtonType.NO);
        clickOn(noButton);

        //assert
        assertEquals(1, listWindows().size());
    }

    //hereasd
    @Test
    public void deleteButton_yes_confirmDialogIsInvisible() throws IOException {
        //arrange
        File testSetupImageFile = new File(ResultDialogTest.class.getResource("/testSetup/746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg").getFile());
        File testSetupSaveFile = new File(ResultDialogTest.class.getResource("/testSetup/save.csv").getFile());

        FileUtils.copyFile(testSetupImageFile, new File(Folders.IMAGE_FOLDER + "746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg"));
        FileUtils.copyFile(testSetupSaveFile, new File(Folders.SAVE_FOLDER));

        TableRow firstRow = lookup(".table-row-cell").nth(0).query();

        //act
        clickOn(firstRow).clickOn(deleteButton);

        final Stage actualAlertDialog = getTopModalStage();
        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        Button yesButton = (Button)dialogPane.lookupButton(ButtonType.YES);
        clickOn(yesButton);

        //assert
        assertEquals(1, listWindows().size());
    }

    @Test
    public void deleteButton_yes_imageIsDeleted() throws IOException {
        //arrange
        File testSetupImageFile = new File(ResultDialogTest.class.getResource("/testSetup/746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg").getFile());
        File testSetupSaveFile = new File(ResultDialogTest.class.getResource("/testSetup/save.csv").getFile());

        File testImageFile = new File(Folders.IMAGE_FOLDER + "746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg");
        FileUtils.copyFile(testSetupImageFile, testImageFile);
        FileUtils.copyFile(testSetupSaveFile, new File(Folders.SAVE_FOLDER));

        //act
        TableRow firstRow = lookup(".table-row-cell").nth(0).query();
        clickOn(firstRow).clickOn(deleteButton);

        final Stage actualAlertDialog = getTopModalStage();
        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        Button yesButton = (Button)dialogPane.lookupButton(ButtonType.YES);
        clickOn(yesButton);

        //assert
        assertFalse(testImageFile.exists());
    }

    @Test
    public void deleteButton_yes_rowIsRemovedFromTable() throws IOException {
        //arrange
        File testSetupImageFile = new File(ResultDialogTest.class.getResource("/testSetup/746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg").getFile());
        File testSetupSaveFile = new File(ResultDialogTest.class.getResource("/testSetup/save.csv").getFile());

        FileUtils.copyFile(testSetupImageFile, new File(Folders.IMAGE_FOLDER + "746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg"));
        FileUtils.copyFile(testSetupSaveFile, new File(Folders.SAVE_FOLDER));

        //act
        TableRow firstRow = lookup(".table-row-cell").nth(0).query();
        clickOn(firstRow).clickOn(deleteButton);

        final Stage actualAlertDialog = getTopModalStage();
        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        Button yesButton = (Button)dialogPane.lookupButton(ButtonType.YES);
        clickOn(yesButton);

        //assert
        assertTrue(resultTable.getItems().isEmpty());
    }

    //TODO: two entries
//    @Test
//    public void deleteButton_yes_placeIsUpdated() throws IOException {
//        //arrange
//        File testSetupImageFile = new File(ResultDialogTest.class.getResource("/testSetup/746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg").getFile());
//        File testSetupSaveFile = new File(ResultDialogTest.class.getResource("/testSetup/save.csv").getFile());
//
//        FileUtils.copyFile(testSetupImageFile, new File(Folders.IMAGE_FOLDER + "746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg"));
//        FileUtils.copyFile(testSetupSaveFile, new File(Folders.SAVE_FOLDER));
//
//        TableRow firstRow = lookup(".table-row-cell").nth(0).query();
//
//        //act
//        clickOn(firstRow).clickOn(deleteButton);
//
//        final Stage actualAlertDialog = getTopModalStage();
//        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
//        Button yesButton = (Button)dialogPane.lookupButton(ButtonType.YES);
//        clickOn(yesButton);
//
//        //assert
//        assertEquals(1, listWindows().size());
//    }

    @Test
    public void deleteButton_yes_saveFileIsUpdated() throws IOException {
        //arrange
        File testSetupImageFile = new File(ResultDialogTest.class.getResource("/testSetup/746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg").getFile());
        File testSetupSaveFile = new File(ResultDialogTest.class.getResource("/testSetup/save.csv").getFile());

        FileUtils.copyFile(testSetupImageFile, new File(Folders.IMAGE_FOLDER + "746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg"));
        FileUtils.copyFile(testSetupSaveFile, new File(Folders.SAVE_FOLDER));

        TableRow firstRow = lookup(".table-row-cell").nth(0).query();

        //act
        clickOn(firstRow).clickOn(deleteButton);

        final Stage actualAlertDialog = getTopModalStage();
        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        Button yesButton = (Button)dialogPane.lookupButton(ButtonType.YES);
        clickOn(yesButton);

        //assert
        assertTrue(loadService.load(Folders.SAVE_FOLDER).isEmpty());
    }

    @Test
    public void imageButton_normal_dialogIsVisible() throws IOException {
        //arrange
        File testSetupImageFile = new File(ResultDialogTest.class.getResource("/testSetup/746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg").getFile());
        File testSetupSaveFile = new File(ResultDialogTest.class.getResource("/testSetup/save.csv").getFile());

        FileUtils.copyFile(testSetupImageFile, new File(Folders.IMAGE_FOLDER + "746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg"));
        FileUtils.copyFile(testSetupSaveFile, new File(Folders.SAVE_FOLDER));

        TableRow firstRow = lookup(".table-row-cell").nth(0).query();

        //act
        clickOn(firstRow).clickOn(imageButton);

        //assert
        assertEquals(2, listWindows().size());
    }

    @Test
    public void imageButton_normal_imageIsShown() throws IOException {
        //arrange
        File testSetupImageFile = new File(ResultDialogTest.class.getResource("/testSetup/746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg").getFile());
        File testSetupSaveFile = new File(ResultDialogTest.class.getResource("/testSetup/save.csv").getFile());

        FileUtils.copyFile(testSetupImageFile, new File(Folders.IMAGE_FOLDER + "746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg"));
        FileUtils.copyFile(testSetupSaveFile, new File(Folders.SAVE_FOLDER));

        //act
        TableRow firstRow = lookup(".table-row-cell").nth(0).query();
        clickOn(firstRow).clickOn(imageButton);

        //assert
        final Stage actualAlertDialog = getTopModalStage();
        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        ImageView imageView = (ImageView) dialogPane.getGraphic();

        assertNotNull(imageView.getImage());
    }

    @Test
    public void imageButton_pressCloseButton_dialogIsInvisible() throws IOException {
        //arrange
        File testSetupImageFile = new File(ResultDialogTest.class.getResource("/testSetup/746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg").getFile());
        File testSetupSaveFile = new File(ResultDialogTest.class.getResource("/testSetup/save.csv").getFile());

        FileUtils.copyFile(testSetupImageFile, new File(Folders.IMAGE_FOLDER + "746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg"));
        FileUtils.copyFile(testSetupSaveFile, new File(Folders.SAVE_FOLDER));

        //act
        TableRow firstRow = lookup(".table-row-cell").nth(0).query();
        clickOn(firstRow).clickOn(imageButton);

        //assert
        final Stage actualAlertDialog = getTopModalStage();
        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        Button closeButton = (Button)dialogPane.lookupButton(ButtonType.CLOSE);
        clickOn(closeButton);

        //assert
        assertEquals(1, listWindows().size());
    }

    //TODO: test certificate progress
}