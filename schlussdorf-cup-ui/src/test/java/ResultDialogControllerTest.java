import com.javafxMvc.validator.CombinedValidator;
import factory.ResultBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Result;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openjfx.components.RetentionFileChooser;
import org.openjfx.constants.Folders;
import org.openjfx.ui.resultDialog.ResultDialogController;
import org.openjfx.ui.resultDialog.ResultDialogModel;
import org.openjfx.ui.resultDialog.ResultDialogView;

import org.openjfx.ui.table.ResultTableModel;
import service.SaveService;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResultDialogControllerTest {

    @InjectMocks
    private ResultDialogController classUnderTest;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ResultDialogModel model;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ResultDialogView view;

    @Mock
    private CombinedValidator validator;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ResultTableModel resultTableModel;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RetentionFileChooser retentionFileChooser;

    @Mock
    private SaveService saveService;

    private ObservableList<Result> resultList;

    private static final UUID RESULT_UUID = UUID.randomUUID();

    private static final String FIRE_DEPARTMENT = "fireDepartmentEdit";

    private static final String TIME = "60.55";

    private static final String MISTAKE_POINTS = "0";

    private static final File IMAGE = new File(ResultDialogControllerTest.class.getResource("/images/test_image_3.jpeg").getFile());

    @Before
    public void setUp() throws IOException {
        File testImage1 = new File(ResultDialogControllerTest.class.getResource("/images/test_image_1.jpeg").getFile());
        File testImage2 = new File(ResultDialogControllerTest.class.getResource("/images/test_image_1.jpeg").getFile());

        UUID resultUuid1 = UUID.randomUUID();
        UUID resultUuid2 = UUID.randomUUID();

        File image1 = new File(Folders.IMAGE_FOLDER + resultUuid1 + ".jpeg");
        File image2 = new File(Folders.IMAGE_FOLDER + resultUuid2 + ".jpeg");
        FileUtils.copyFile(testImage1, image1);
        FileUtils.copyFile(testImage2, image2);

        ResultBuilder resultBuilder1 = new ResultBuilder();
        Result result1 = resultBuilder1.uuid(resultUuid1).fireDepartment("fireDepartment1").place(1).time(10).mistakePoints(5).image(image1).build();
        ResultBuilder resultBuilder2 = new ResultBuilder();
        Result result2 = resultBuilder2.uuid(resultUuid1).fireDepartment("fireDepartment2").place(2).time(20).mistakePoints(5).image(image2).build();

        resultList = FXCollections.observableArrayList(result1, result2);
        when(resultTableModel.getResultList()).thenReturn(resultList);

        when(model.getUuid()).thenReturn(RESULT_UUID);
        when(model.getFireDepartment().getValue()).thenReturn(FIRE_DEPARTMENT);
        when(model.getTime().getValue()).thenReturn(TIME);
        when(model.getMistakePoints().getValue()).thenReturn(MISTAKE_POINTS);
        when(model.getImage().getValue()).thenReturn(IMAGE);

        when(validator.validate()).thenReturn(true);
    }

    @After
    public void tearDown(){
        resultList.forEach(result -> result.getImage().delete());
    }

    @Test
    public void show_null_dataIsSet(){
        //arrange

        //act
        classUnderTest.show(null);

        //assert
        verify(model).setData(null);
    }

    @Test
    public void show_result_dataIsSet(){
        //arrange
        ResultBuilder resultBuilder1 = new ResultBuilder();
        Result result = resultBuilder1.fireDepartment("fireDepartment1").place(1).time(10).mistakePoints(5).image(new File("")).build();

        //act
        classUnderTest.show(result);

        //assert
        verify(model).setData(result);
    }

    @Test
    public void show_result_viewIsShown(){
        //arrange
        ResultBuilder resultBuilder1 = new ResultBuilder();
        Result result = resultBuilder1.fireDepartment("fireDepartment1").place(1).time(10).mistakePoints(5).image(new File("")).build();

        //act
        classUnderTest.show(result);

        //assert
        verify(view).show();
    }

    @Test
    public void cancel_normal_viewIsHidden(){
        //arrange

        //act
        classUnderTest.cancel();

        //assert
        verify(view).hide();
    }

    @Test
    public void chooseImage_imageSelected_imagePathInModelIsUpdated(){
        //arrange
        File imageFile = new File("test.png");
        when(retentionFileChooser.showOpenDialog(view.getRoot().getScene().getWindow())).thenReturn(imageFile);

        //act
        classUnderTest.chooseImage();

        //assert
       // verify(model).chooseImage(imageFile);
    }

    @Test
    public void chooseImage_noImageSelected_modelIsNotUpdated(){
        //arrange
        when(retentionFileChooser.showOpenDialog(view.getRoot().getScene().getWindow())).thenReturn(null);

        //act
        classUnderTest.chooseImage();

        //assert
        verifyNoInteractions(model);
    }


    @Test
    public void apply_inputInvalid_dialogIsStillVisible(){
        //arrange
        when(validator.validate()).thenReturn(false);

        //act
        classUnderTest.apply();

        //assert
        verify(view, never()).hide();
    }

    @Test
    public void apply_inputInvalid_modelIsNotUpdated(){
        //arrange
        when(validator.validate()).thenReturn(false);

        //act
        classUnderTest.apply();

        //assert
        verifyNoInteractions(model, resultTableModel);
    }

    @Test
    public void apply_inputValidInsertMode_insertNewResult(){
        //arrange
        when(model.getUuid()).thenReturn(null);

        //act
        classUnderTest.apply();

        //assert
        assertEquals(3, resultList.size());
    }

    @Test
    public void apply_newResult_fireDepartmentIsCorrect(){
        //arrange
        when(model.getUuid()).thenReturn(null);

        //act
        classUnderTest.apply();

        //assert
        Optional<Result> resultOptional = resultList.stream().filter(r -> r.getFireDepartment().equals(FIRE_DEPARTMENT)).findFirst();
        assertTrue(resultOptional.isPresent());
        assertEquals(FIRE_DEPARTMENT, resultOptional.get().getFireDepartment());
    }

    @Test
    public void apply_inputValidInsertMode_timeIsCorrect(){
        //arrange
        when(model.getUuid()).thenReturn(null);

        //act
        classUnderTest.apply();

        //assert
        Optional<Result> resultOptional = resultList.stream().filter(r -> r.getFireDepartment().equals(FIRE_DEPARTMENT)).findFirst();
        assertTrue(resultOptional.isPresent());
        assertEquals(TIME, String.valueOf(resultOptional.get().getTime()));
    }

    @Test
    public void apply_inputValidInsertMode_mistakePointsIsCorrect(){
        //arrange
        when(model.getUuid()).thenReturn(null);

        //act
        classUnderTest.apply();

        //assert
        Optional<Result> resultOptional = resultList.stream().filter(r -> r.getFireDepartment().equals(FIRE_DEPARTMENT)).findFirst();
        assertTrue(resultOptional.isPresent());
        assertEquals(MISTAKE_POINTS, String.valueOf(resultOptional.get().getMistakePoints()));
    }

    @Test
    public void apply_newResult_imageIsCorrect() throws IOException {
        //arrange
        when(model.getUuid()).thenReturn(null);

        //act
        classUnderTest.apply();

        //assert
        Optional<Result> resultOptional = resultList.stream().filter(r -> r.getFireDepartment().equals(FIRE_DEPARTMENT)).findFirst();
        assertTrue(resultOptional.isPresent());
        assertTrue(FileUtils.contentEquals(IMAGE, resultOptional.get().getImage()));
    }

    @Test
    public void apply_inputValidEditMode_updateResult(){
        //arrange
        when(model.getUuid()).thenReturn(resultList.get(0).getUuid());

        //act
        classUnderTest.apply();

        //assert
        assertEquals(2, resultList.size());
    }

    @Test
    public void apply_inputValidEditMode_fireDepartmentIsUpdated(){
        //arrange
        when(model.getUuid()).thenReturn(resultList.get(0).getUuid());

        //act
        classUnderTest.apply();

        //assert
        assertEquals(FIRE_DEPARTMENT, resultList.get(0).getFireDepartment());
    }

    @Test
    public void apply_inputValidEditMode_timeIsUpdated(){
        //arrange
        when(model.getUuid()).thenReturn(resultList.get(0).getUuid());

        //act
        classUnderTest.apply();

        //assert
        assertEquals(TIME, String.valueOf(resultList.get(0).getTime()));
    }

    @Test
    public void apply_inputValidEditMode_mistakePointsIsUpdated(){
        //arrange
        when(model.getUuid()).thenReturn(resultList.get(0).getUuid());

        //act
        classUnderTest.apply();

        //assert
        assertEquals(MISTAKE_POINTS, String.valueOf(resultList.get(0).getMistakePoints()));
    }

    @Test
    public void apply_existingResult_imageIsUpdated(){
        //arrange
        when(model.getUuid()).thenReturn(resultList.get(0).getUuid());

        //act
        classUnderTest.apply();

        //assert
        assertEquals(IMAGE, resultList.get(0).getImage());
    }

    @Test
    public void apply_newResult_resultListIsReordered(){
        //arrange
        when(model.getUuid()).thenReturn(null);

        //act
        classUnderTest.apply();

        //assert
        for(int i = 0; i < resultList.size()-1; i++){
            assertTrue(resultList.get(i).getFinalScore() >= resultList.get(i+1).getFinalScore());
        }
    }

    @Test
    public void apply_inputValid_placeIsUpdated(){
        //arrange
        when(model.getUuid()).thenReturn(null);

        //act
        classUnderTest.apply();

        //assert
        for(int i = 0; i < resultList.size(); i++){
            assertEquals(i+1, resultList.get(i).getPlace());
        }
    }

    @Test
    public void apply_inputValid_saveFileIsUpdated() throws IOException {
        //arrange
        when(model.getUuid()).thenReturn(null);

        //act
        classUnderTest.apply();

        //assert
        verify(saveService).save(resultList, Folders.SAVE_FOLDER);
    }

    @Test
    public void apply_newResult_imageFileIsCopied(){
        //arrange
        when(model.getUuid()).thenReturn(null);
        when(model.getImage().getValue()).thenReturn(IMAGE);

        //act
        classUnderTest.apply();

        //assert
        assertTrue(IMAGE.exists());
    }

    @Test
    public void apply_existingResultImageIsSame_imageFileNotIsCopied(){
        //arrange
        when(model.getUuid()).thenReturn(resultList.get(0).getUuid());
        when(model.getImage().getValue()).thenReturn(resultList.get(0).getImage());

        //act
        classUnderTest.apply();

        //assert
        //TODO: check image is not copied twice
        assertTrue(resultList.get(0).getImage().exists());
    }

    @Test
    public void apply_existingResultImageChanged_imageFileIsCopied(){
        //arrange
        when(model.getUuid()).thenReturn(resultList.get(0).getUuid());
        when(model.getImage().getValue()).thenReturn(IMAGE);

        //act
        classUnderTest.apply();

        //assert
        //TODO: check image changed
        assertEquals(IMAGE, resultList.get(0).getImage());
    }
}