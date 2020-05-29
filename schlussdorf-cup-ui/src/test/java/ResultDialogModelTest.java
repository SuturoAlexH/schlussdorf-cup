import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import model.Result;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openjfx.ui.resultDialog.ResultDialogModel;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

import static junit.framework.TestCase.*;

public class ResultDialogModelTest {

//    private ResultDialogModel classUnderTest;
//
//    private static final UUID RESULT_UUID = UUID.randomUUID();
//    private static final int PLACE = 1;
//    private static final String FIRE_DEPARTMENT = "fireDepartment";
//    private static final double TIME = 10.1;
//    private static final int MISTAKE_POINTS = 5;
//    private static final double FINAL_SCORE = 100.5;
//
//    private static Result result;
//
//    @BeforeClass
//    public static void startUp() throws URISyntaxException {
//        URL resource = ResultDialogModelTest.class.getResource("/images/test_image.jpeg");
//        File image = Paths.get(resource.toURI()).toFile();
//        result = new Result(RESULT_UUID, PLACE, FIRE_DEPARTMENT, TIME, MISTAKE_POINTS, FINAL_SCORE, image.getAbsolutePath());
//    }
//
//    @Before
//    public void setUp(){
//        classUnderTest = new ResultDialogModel();
//    }
//
//    @Test
//    public void setData_resultIsNull_uuidIsNull(){
//        //arrange
//
//        //act
//        classUnderTest.setData(null, FXCollections.observableList(new ArrayList<>()));
//
//        //assert
//        assertNull(classUnderTest.getUuid());
//    }
//
//    @Test
//    public void setData_resultIsNull_fireDepartmentIsEmpty(){
//        //arrange
//
//        //act
//        classUnderTest.setData(null, FXCollections.observableList(new ArrayList<>()));
//
//        //assert
//        assertNull(classUnderTest.getFireDepartment());
//    }
//
//    @Test
//    public void setData_resultIsNull_timeIsEmpty(){
//        //arrange
//
//        //act
//        classUnderTest.setData(null, FXCollections.observableList(new ArrayList<>()));
//
//        //assert
//        assertNull(classUnderTest.getTime());
//    }
//
//    @Test
//    public void setData_resultIsNull_mistakePointsIsEmpty(){
//        //arrange
//
//        //act
//        classUnderTest.setData(null, FXCollections.observableList(new ArrayList<>()));
//
//        //assert
//        assertNull(classUnderTest.getMistakePoints());
//    }
//
//    @Test
//    public void setData_resultIsNull_imageIsNull(){
//        //arrange
//
//        //act
//        classUnderTest.setData(null, FXCollections.observableList(new ArrayList<>()));
//
//        //assert
//        assertNull(classUnderTest.getImage());
//    }
//
//    @Test
//    public void setData_resultIsNotNull_uuidIsCorrect(){
//        //arrange
//
//        //act
//        classUnderTest.setData(result, FXCollections.observableList(new ArrayList<>()));
//
//        //assert
//        assertEquals(RESULT_UUID, classUnderTest.getUuid());
//    }
//
//    @Test
//    public void setData_list_listIsSetCorrect(){
//        //arrange
//        ObservableList<Result> resultList = FXCollections.observableList(new ArrayList<>());
//
//        //act
//        classUnderTest.setData(result, resultList);
//
//        //assert
//        assertEquals(resultList, classUnderTest.getList());
//    }
//
//    // fireDepartment
//    @Test
//    public void resultDialogModel_initialState_fireDepartmentErrorTextIsInvisible(){
//        //arrange
//
//        //act
//
//        //assert
//        assertFalse(classUnderTest.isIsFireDepartmentErrorLabelVisible());
//    }
//
//    @Test
//    public void validate_emptyFireDepartment_fireDepartmentErrorTextIsVisible(){
//        //arrange
//
//        //act
//        classUnderTest.validate();
//
//        //assert
//        assertTrue(classUnderTest.isIsFireDepartmentErrorLabelVisible());
//    }
//
//    @Test
//    public void validate_emptyFireDepartment_fireDepartmentErrorTextCorrect(){
//        //arrange
//
//        //act
//        classUnderTest.validate();
//
//        //assert
//        assertEquals("Dieses Feld darf nicht leer sein!", classUnderTest.getFireDepartmentErrorLabel());
//    }
//
//    @Test
//    public void validate_NonEmptyFireDepartment_fireDepartmentErrorTextIsInvisible(){
//        //arrange
//        classUnderTest.fireDepartmentProperty().set("fireDepartment");
//
//        //act
//        classUnderTest.validate();
//
//        //assert
//        assertFalse(classUnderTest.isIsFireDepartmentErrorLabelVisible());
//    }
//
//    // time
//    @Test
//    public void resultDialogModel_initialState_timeErrorTextIsInvisible(){
//        //arrange
//
//        //act
//
//        //assert
//        assertFalse(classUnderTest.isIsTimeErrorLabelVisible());
//    }
//
//    @Test
//    public void validate_emptyTime_timeErrorTextIsVisible(){
//        //arrange
//
//        //act
//        classUnderTest.validate();
//
//        //assert
//        assertTrue(classUnderTest.isIsTimeErrorLabelVisible());
//    }
//
//    @Test
//    public void validate_emptyTime_timeErrorTextCorrect(){
//        //arrange
//
//        //act
//        classUnderTest.validate();
//
//        //assert
//        assertEquals("Dieses Feld darf nicht leer sein!", classUnderTest.getTimeErrorLabel());
//    }
//
//    @Test
//    public void validate_correctTimeCommaSeparated_timeErrorTextIsInvisible(){
//        //arrange
//        classUnderTest.timeProperty().set("10,1");
//
//        //act
//        classUnderTest.validate();
//
//        //assert
//        assertFalse(classUnderTest.isIsTimeErrorLabelVisible());
//    }
//
//    @Test
//    public void validate_correctTimeDotSeparated_timeErrorTextIsInvisible(){
//        //arrange
//        classUnderTest.timeProperty().set("10.1");
//
//        //act
//        classUnderTest.validate();
//
//        //assert
//        assertFalse(classUnderTest.isIsTimeErrorLabelVisible());
//    }
//
//    @Test
//    public void validate_containsLetters_timeErrorTextIsVisible(){
//        //arrange
//        classUnderTest.timeProperty().set("10.a1");
//
//        //act
//        classUnderTest.validate();
//
//        //assert
//        assertTrue(classUnderTest.isIsTimeErrorLabelVisible());
//    }
//
//    @Test
//    public void validate_containsLetters_timeErrorTextCorrect(){
//        //arrange
//        classUnderTest.timeProperty().set("10.a1");
//
//        //act
//        classUnderTest.validate();
//
//        //assert
//        assertEquals("Diese Eingabe ist keine Zahl!", classUnderTest.getTimeErrorLabel());
//    }
//
//    // mistakePoints
//    @Test
//    public void resultDialogModel_initialState_mistakePointsErrorTextIsInvisible(){
//        //arrange
//
//        //act
//
//        //assert
//        assertFalse(classUnderTest.isIsMistakePointsErrorLabelVisible());
//    }
//
//    @Test
//    public void validate_emptyMistakePoints_mistakePointsErrorTextIsInvisible(){
//        //arrange
//
//        //act
//        classUnderTest.validate();
//
//        //assert
//        assertFalse(classUnderTest.isIsMistakePointsErrorLabelVisible());
//    }
//
//    @Test
//    public void validate_mistakePointsContainsOnlyNumbers_mistakePointsErrorTextIsInvisible(){
//        //arrange
//        classUnderTest.mistakePointsProperty().set("10");
//
//        //act
//        classUnderTest.validate();
//
//        //assert
//        assertFalse(classUnderTest.isIsMistakePointsErrorLabelVisible());
//    }
//
//    @Test
//    public void validate_mistakePointsContainsLetters_mistakePointsErrorTextIsVisible(){
//        //arrange
//        classUnderTest.mistakePointsProperty().set("10a");
//
//        //act
//        classUnderTest.validate();
//
//        //assert
//        assertTrue(classUnderTest.isIsMistakePointsErrorLabelVisible());
//    }
//
//    //image
//    @Test
//    public void resultDialogModel_initialState_imageErrorTextIsInvisible(){
//        //arrange
//
//        //act
//
//        //assert
//        assertFalse(classUnderTest.isIsImageErrorLabelVisible());
//    }
//
//    @Test
//    public void validate_noImageSelected_imageErrorTextIsVisible(){
//        //arrange
//
//        //act
//        classUnderTest.validate();
//
//        //assert
//        assertTrue(classUnderTest.isIsImageErrorLabelVisible());
//    }
//
//    @Test
//    public void validate_noImageSelected_imageErrorTextIsCorrect(){
//        //arrange
//
//        //act
//        classUnderTest.validate();
//
//        //assert
//        assertEquals("Es muss ein Bild gewählt werden!", classUnderTest.getImageErrorLabel());
//    }
//
//    @Test
//    public void validate_imageIsValid_imageErrorTextIsInvisible(){
//        //arrange
//        classUnderTest.imageProperty().set(new Image(ResultDialogModelTest.class.getResourceAsStream("/images/test_image.jpeg")));
//
//        //act
//        classUnderTest.validate();
//
//        //assert
//        assertFalse(classUnderTest.isIsImageErrorLabelVisible());
//    }
}
