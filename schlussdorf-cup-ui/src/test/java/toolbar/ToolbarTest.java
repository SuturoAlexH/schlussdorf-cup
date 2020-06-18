package toolbar;

import com.jPdfUnit.asserts.PdfAssert;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Result;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openjfx.App;
import org.openjfx.constants.Folders;
import org.testfx.framework.junit.ApplicationTest;
import resultDialog.ResultDialogAddTest;
import service.LoadService;
import util.TestUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;

public class ToolbarTest extends ApplicationTest {

    private static final String CERTIFICATE_FOLDER = "./urkunden";

    private Button addButton;

    private Button deleteButton;

    private Button editButton;

    private Button imageButton;

    private Button certificateButton;

    private TableView<Result> resultTable;

    private TableRow<Result> firstRow;

    private LoadService loadService = new LoadService();

    @BeforeClass
    public static void initialize() throws IOException {
        TestUtil.clearFolders();
    }
    
    @Before
    public void setUp() throws Exception {
        TestUtil.loadTestSetup2();

        File certificateFolder = new File(CERTIFICATE_FOLDER);
        certificateFolder.mkdir();

        TestUtil.setClipBoardContent(certificateFolder.getAbsolutePath());

        launch(App.class);

        addButton = lookup("#addButton").query();
        deleteButton = lookup("#deleteButton").query();
        editButton = lookup("#editButton").query();
        imageButton = lookup("#imageButton").query();
        certificateButton = lookup("#certificateButton").query();

        resultTable = lookup("#table").query();
        firstRow = lookup(".table-row-cell").nth(0).query();
    }

    @After
    public void tearDown() throws IOException {
        TestUtil.clearFolders();

        File certificateFolder = new File(CERTIFICATE_FOLDER);
        certificateFolder.delete();
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
    public void deleteButton_normal_correctText() {
        //arrange

        //act
        clickOn(firstRow).clickOn(deleteButton);

        Stage actualAlertDialog = getTopModalStage();
        DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        String contentText = dialogPane.getContentText();

        //assert
        assertEquals("Soll das Ergebnis der Feuerwehr Feuerwehr1 wirklich gelöscht werden?", contentText);
    }

    @Test
    public void deleteButton_normal_confirmDialogIsVisible() {
        //arrange

        //act
        clickOn(firstRow).clickOn(deleteButton);

        //assert
        assertEquals(2, listWindows().size());
    }

    @Test
    public void deleteButton_no_confirmDialogIsInvisible() {
        //arrange

        //act
        clickOn(firstRow).clickOn(deleteButton);

        final Stage actualAlertDialog = getTopModalStage();
        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        Button noButton = (Button)dialogPane.lookupButton(ButtonType.NO);
        clickOn(noButton);

        //assert
        assertEquals(1, listWindows().size());
    }

    @Test
    public void deleteButton_yes_confirmDialogIsInvisible() {
        //arrange

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
    public void deleteButton_yes_imageIsDeleted() {
        //arrange

        //act
        clickOn(firstRow).clickOn(deleteButton);

        final Stage actualAlertDialog = getTopModalStage();
        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        Button yesButton = (Button)dialogPane.lookupButton(ButtonType.YES);
        clickOn(yesButton);

        //assert
        File testImageFile = new File(Folders.IMAGE_FOLDER + "746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg");
        assertFalse(testImageFile.exists());
    }

    @Test
    public void deleteButton_yes_rowIsRemovedFromTable() {
        //arrange

        //act
        clickOn(firstRow).clickOn(deleteButton);

        final Stage actualAlertDialog = getTopModalStage();
        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        Button yesButton = (Button)dialogPane.lookupButton(ButtonType.YES);
        clickOn(yesButton);

        //assert
        assertEquals(1, resultTable.getItems().size());
    }

    @Test
    public void deleteButton_yes_placeIsUpdated() {
        //arrange

        //act
        clickOn(firstRow).clickOn(deleteButton);

        final Stage actualAlertDialog = getTopModalStage();
        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        Button yesButton = (Button)dialogPane.lookupButton(ButtonType.YES);
        clickOn(yesButton);

        //assert
        assertEquals(1, resultTable.getItems().get(0).getPlace());
        assertEquals("Feuerwehr2", resultTable.getItems().get(0).getFireDepartment());
    }

    @Test
    public void deleteButton_yes_saveFileIsUpdated() throws IOException {
        //arrange

        //act
        clickOn(firstRow).clickOn(deleteButton);

        final Stage actualAlertDialog = getTopModalStage();
        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        Button yesButton = (Button)dialogPane.lookupButton(ButtonType.YES);
        clickOn(yesButton);

        //assert
        assertEquals(1, loadService.load(Folders.SAVE_FOLDER).size());
    }

    @Test
    public void imageButton_normal_dialogIsVisible() {
        //arrange

        //act
        clickOn(firstRow).clickOn(imageButton);

        //assert
        assertEquals(2, listWindows().size());
    }

    @Test
    public void imageButton_normal_imageIsShown() {
        //arrange

        //act
        clickOn(firstRow).clickOn(imageButton);

        //assert
        final Stage actualAlertDialog = getTopModalStage();
        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        ImageView imageView = (ImageView) dialogPane.getGraphic();

        assertNotNull(imageView.getImage());
    }

    @Test
    public void imageButton_pressCloseButton_dialogIsInvisible() {
        //arrange

        //act
        clickOn(firstRow).clickOn(imageButton);

        //assert
        final Stage actualAlertDialog = getTopModalStage();
        final DialogPane dialogPane = (DialogPane) actualAlertDialog.getScene().getRoot();
        Button closeButton = (Button)dialogPane.lookupButton(ButtonType.CLOSE);
        clickOn(closeButton);

        //assert
        assertEquals(1, listWindows().size());
    }

    @Test
    public void createCertificateButton_normal_certificateFolderIsCreated(){
        //arrange

        //act
        clickOn(certificateButton)
                .press(KeyCode.CONTROL, KeyCode.V).release(KeyCode.V, KeyCode.CONTROL)
                .press(KeyCode.ENTER).release(KeyCode.ENTER)
                .press(KeyCode.ENTER);

        await().atMost(60, TimeUnit.SECONDS).until(() -> getTopModalStage() != null);

        //assert
        File innerCertificateFolder = new File(CERTIFICATE_FOLDER + "/urkunden");
        assertTrue(innerCertificateFolder.exists());
    }

    @Test
    public void createCertificateButton_normal_fireDepartmentFoldersAreCreated(){
        //arrange

        //act
        clickOn(certificateButton)
                .press(KeyCode.CONTROL, KeyCode.V).release(KeyCode.V, KeyCode.CONTROL)
                .press(KeyCode.ENTER).release(KeyCode.ENTER)
                .press(KeyCode.ENTER);

        await().atMost(60, TimeUnit.SECONDS).until(() -> listWindows().size() == 1);

        //assert
        File fireDepartment1Folder1 = new File(CERTIFICATE_FOLDER + "/urkunden/1_Feuerwehr1");
        File fireDepartment1Folder2 = new File(CERTIFICATE_FOLDER + "/urkunden/2_Feuerwehr2");

        assertTrue(fireDepartment1Folder1.exists());
        assertTrue(fireDepartment1Folder2.exists());
    }

    @Test
    public void createCertificateButton_normal_certificatePdfsAreCorrect(){
        //arrange

        //act
        clickOn(certificateButton)
                .press(KeyCode.CONTROL, KeyCode.V).release(KeyCode.V, KeyCode.CONTROL)
                .press(KeyCode.ENTER).release(KeyCode.ENTER)
                .press(KeyCode.ENTER);

        await().atMost(60, TimeUnit.SECONDS).until(() -> listWindows().size() == 1);

        //assert
        File certificate1 = new File(ResultDialogAddTest.class.getResource("/referencePdfs/Feuerwehr1.pdf").getFile());
        File certificate2 = new File(ResultDialogAddTest.class.getResource("/referencePdfs/Feuerwehr2.pdf").getFile());

        PdfAssert.assertThat(new File(CERTIFICATE_FOLDER + "/urkunden/1_Feuerwehr1/Feuerwehr1.pdf")).hasSameAppearanceAs(certificate1);
        PdfAssert.assertThat(new File(CERTIFICATE_FOLDER + "/urkunden/2_Feuerwehr2/Feuerwehr2.pdf")).hasSameAppearanceAs(certificate2);
    }

    @Test
    public void createCertificateButton_normal_summaryPdfIsCorrect(){
        //arrange

        //act
        clickOn(certificateButton)
                .press(KeyCode.CONTROL, KeyCode.V).release(KeyCode.V, KeyCode.CONTROL)
                .press(KeyCode.ENTER).release(KeyCode.ENTER)
                .press(KeyCode.ENTER);

        await().atMost(60, TimeUnit.SECONDS).until(() -> listWindows().size() == 1);

        //assert
        File summary = new File(ResultDialogAddTest.class.getResource("/referencePdfs/zusammenfassung.pdf").getFile());
        PdfAssert.assertThat(new File(CERTIFICATE_FOLDER + "/urkunden/zusammenfassung.pdf")).hasSameAppearanceAs(summary);
    }

    @Test
    public void createCertificateButton_normal_mergedCertificatesAreCorrect(){
        //arrange

        //act
        clickOn(certificateButton)
                .press(KeyCode.CONTROL, KeyCode.V).release(KeyCode.V, KeyCode.CONTROL)
                .press(KeyCode.ENTER).release(KeyCode.ENTER)
                .press(KeyCode.ENTER);

        await().atMost(60, TimeUnit.SECONDS).until(() -> listWindows().size() == 1);

        //assert
        File summary = new File(ResultDialogAddTest.class.getResource("/referencePdfs/urkunden.pdf").getFile());
        PdfAssert.assertThat(new File(CERTIFICATE_FOLDER + "/urkunden/urkunden.pdf")).hasSameAppearanceAs(summary);
    }
}