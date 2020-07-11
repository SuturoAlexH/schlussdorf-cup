import com.schlussdorf.exception.CsvFormatException;
import com.schlussdorf.factory.ResultBuilder;
import com.schlussdorf.model.Result;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.schlussdorf.service.LoadService;
import com.schlussdorf.service.SaveService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LoadServiceTest {

    private static final String SAVE_FILE_PATH = "save/invalid_uuid.csv";
    private static final String INVALID_SAVE_FILE_PATH = "INVALID_SAVE_FILE_PATH";

    private SaveService saveService;

    private LoadService classUnderTest;

    @Before
    public void setUp(){
        classUnderTest = new LoadService();
        saveService = new SaveService();
    }

    @After
    public void tearDown(){
        File saveFile = new File(SAVE_FILE_PATH);
        saveFile.delete();

        File invalidSaveFile = new File(INVALID_SAVE_FILE_PATH);
        invalidSaveFile.delete();
    }

    @Test(expected = NullPointerException.class)
    public void load_filePathIsNull_illegalArgumentException() throws IOException, CsvFormatException {
        //arrange

        //act
        classUnderTest.load(null);

        //assert
    }

    @Test
    public void load_filePathDoesNotExists_resultListIsEmpty() throws IOException, CsvFormatException {
        //arrange

        //act
        List<Result> loadedResultList = classUnderTest.load(INVALID_SAVE_FILE_PATH);

        //assert
        assertTrue(loadedResultList.isEmpty());
    }

    @Test
    public void load_invalidCsvFileHeaderName_csvFormatException() throws IOException {
        //arrange
        File saveFile = new File(SaveServiceTest.class.getResource("/save/invalid_header_name.csv").getFile());

        //act
        try {
            classUnderTest.load(saveFile.getAbsolutePath());
            fail("csv format com.schlussdorf.exception not thrown");
        }catch(CsvFormatException e){
            //assert
            assertEquals("Header at position 2 is wrong! Expected fire_department, but actual invalid", e.getMessage());
        }
    }

    @Test
    public void load_invalidCsvFileHeaderCount_csvFormatException() throws IOException {
        //arrange
        File saveFile = new File(SaveServiceTest.class.getResource("/save/invalid_header_count.csv").getFile());

        //act
        try {
            classUnderTest.load(saveFile.getAbsolutePath());
            fail("csv format com.schlussdorf.exception not thrown");
        }catch(CsvFormatException e){
            //assert
            assertEquals("Header size is wrong! Expected 7, but actual 6", e.getMessage());
        }
    }

    @Test
    public void load_invalidCsvFileRowCount_csvFormatException() throws IOException {
        //arrange
        File saveFile = new File(SaveServiceTest.class.getResource("/save/invalid_row_count.csv").getFile());

        //act
        try {
            classUnderTest.load(saveFile.getAbsolutePath());
            fail("csv format com.schlussdorf.exception not thrown");
        }catch(CsvFormatException e){
            //assert
            assertEquals("Invalid row count in row 1! Expected 7, but actual 6", e.getMessage());
        }
    }

    @Test
    public void load_invalidCsvFileUuid_csvFormatException() throws IOException {
        //arrange
        File saveFile = new File(SaveServiceTest.class.getResource("/save/invalid_uuid.csv").getFile());

        //act
        try {
            classUnderTest.load(saveFile.getAbsolutePath());
            fail("csv format com.schlussdorf.exception not thrown");
        }catch(CsvFormatException e){
            //assert
            assertEquals("Invalid uuid in row 1 because: Invalid UUID string: aa", e.getMessage());
        }
    }

    @Test
    public void load_invalidCsvFilePlace_csvFormatException() throws IOException {
        //arrange
        File saveFile = new File(SaveServiceTest.class.getResource("/save/invalid_place.csv").getFile());

        //act
        try {
            classUnderTest.load(saveFile.getAbsolutePath());
            fail("csv format com.schlussdorf.exception not thrown");
        }catch(CsvFormatException e){
            //assert
            assertEquals("Invalid place in row 1 because: For input string: \"aa\"", e.getMessage());
        }
    }

    @Test
    public void load_invalidCsvFileTime_csvFormatException() throws IOException {
        //arrange
        File saveFile = new File(SaveServiceTest.class.getResource("/save/invalid_time.csv").getFile());

        //act
        try {
            classUnderTest.load(saveFile.getAbsolutePath());
            fail("csv format com.schlussdorf.exception not thrown");
        }catch(CsvFormatException e){
            //assert
            System.out.println(e.getMessage());
            assertEquals("Invalid time in row 1 because: For input string: \"aa\"", e.getMessage());
        }
    }

    @Test
    public void load_invalidCsvFileMistakePoints_csvFormatException() throws IOException {
        //arrange
        File saveFile = new File(SaveServiceTest.class.getResource("/save/invalid_mistake_points.csv").getFile());

        //act
        try {
            classUnderTest.load(saveFile.getAbsolutePath());
            fail("csv format com.schlussdorf.exception not thrown");
        }catch(CsvFormatException e){
            //assert
            System.out.println(e.getMessage());
            assertEquals("Invalid mistake points in row 1 because: For input string: \"aa\"", e.getMessage());
        }
    }

    @Test
    public void load_invalidCsvFileFinalScore_csvFormatException() throws IOException {
        //arrange
        File saveFile = new File(SaveServiceTest.class.getResource("/save/invalid_final_score.csv").getFile());

        //act
        try {
            classUnderTest.load(saveFile.getAbsolutePath());
            fail("csv format com.schlussdorf.exception not thrown");
        }catch(CsvFormatException e){
            //assert
            System.out.println(e.getMessage());
            assertEquals("Invalid final score in row 1 because: For input string: \"aa\"", e.getMessage());
        }
    }

    @Test
    public void load_csvIsEmpty_resultListIsEmpty() throws IOException, CsvFormatException {
        //arrange
        saveService.save(new ArrayList<>(), SAVE_FILE_PATH);

        //act
        List<Result> loadedResultList = classUnderTest.load(SAVE_FILE_PATH);

        //assert
        assertTrue(loadedResultList.isEmpty());
    }

    @Test
    public void load_csvHasOneEntry_resultListHasOneEntry() throws IOException, CsvFormatException {
        //arrange
        File imageFile = new File(SaveServiceTest.class.getResource("/images/test_image.jpeg").getFile());

        ResultBuilder resultBuilder1 = new ResultBuilder();
        Result result = resultBuilder1.fireDepartment("firedepartment1")
                .time(10.01)
                .mistakePoints(5)
                .image(imageFile)
                .build();

        saveService.save(Collections.singletonList(result), SAVE_FILE_PATH);

        //act
        List<Result> loadedResultList = classUnderTest.load(SAVE_FILE_PATH);

        //assert
        assertEquals(1, loadedResultList.size());
        assertEquals(result, loadedResultList.get(0));
    }

    @Test
    public void load_csvHasTwoEntry_loadedResultListHasTwoEntry() throws IOException, CsvFormatException {
        //arrange
        File imageFile = new File(SaveServiceTest.class.getResource("/images/test_image.jpeg").getFile());

        ResultBuilder resultBuilder1 = new ResultBuilder();
        Result result1 = resultBuilder1.fireDepartment("firedepartment1")
                .time(10.01)
                .mistakePoints(5)
                .image(imageFile)
                .build();

        ResultBuilder resultBuilder2 = new ResultBuilder();
        Result result2 = resultBuilder2.fireDepartment("firedepartment2")
                .time(22.01)
                .mistakePoints(5)
                .image(imageFile)
                .build();

        saveService.save(Arrays.asList(result1, result2), SAVE_FILE_PATH);

        //act
        List<Result> loadedResultList = classUnderTest.load(SAVE_FILE_PATH);

        //assert
        assertEquals(2, loadedResultList.size());
        assertEquals(result1, loadedResultList.get(0));
        assertEquals(result2, loadedResultList.get(1));
    }

    @Test
    public void load_csvHasEntryWithSpecialCharacters_loadedResultListHasProperEncoding() throws IOException, CsvFormatException {
        //arrange
        File imageFile = new File(SaveServiceTest.class.getResource("/images/test_image.jpeg").getFile());

        ResultBuilder resultBuilder1 = new ResultBuilder();
        Result result = resultBuilder1.fireDepartment("äüöß§$%&/()=?``#*+")
                .time(10.01)
                .mistakePoints(5)
                .image(imageFile)
                .build();

        saveService.save(Collections.singletonList(result), SAVE_FILE_PATH);

        //act
        List<Result> loadedResultList = classUnderTest.load(SAVE_FILE_PATH);

        //assert
        assertEquals(1, loadedResultList.size());
        assertEquals(result, loadedResultList.get(0));
    }
}