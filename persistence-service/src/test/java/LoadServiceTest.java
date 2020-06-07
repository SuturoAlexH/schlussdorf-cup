import com.google.common.collect.Lists;
import factory.ResultBuilder;
import model.Result;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.LoadService;
import service.SaveService;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class LoadServiceTest {

    private static final String SAVE_FILE_PATH = "./save.csv";
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
    public void load_filePathIsNull_illegalArgumentException() throws IOException {
        //arrange

        //act
        classUnderTest.load(null);

        //assert
    }

    @Test(expected = NoSuchFileException.class)
    public void load_filePathDoesNotExists_resultListIsEmpty() throws IOException {
        //arrange

        //act
        List<Result> loadedResultList = classUnderTest.load(INVALID_SAVE_FILE_PATH);

        //assert
        assertTrue(loadedResultList.isEmpty());
    }

    @Test
    public void load_csvIsEmpty_resultListIsEmpty() throws IOException {
        //arrange
        saveService.save(new ArrayList<>(), SAVE_FILE_PATH);

        //act
        List<Result> loadedResultList = classUnderTest.load(SAVE_FILE_PATH);

        //assert
        assertTrue(loadedResultList.isEmpty());
    }

    @Test
    public void load_csvHasOneEntry_resultListHasOneEntry() throws IOException {
        //arrange
        ResultBuilder resultBuilder1 = new ResultBuilder();
        Result result = resultBuilder1.fireDepartment("firedepartment1").time(10.01).mistakePoints(5).image(new File(SaveServiceTest.class.getResource("/images/test_image.jpeg").getFile())).build();

        saveService.save(Lists.newArrayList(result), SAVE_FILE_PATH);

        //act
        List<Result> loadedResultList = classUnderTest.load(SAVE_FILE_PATH);

        //assert
        assertEquals(1, loadedResultList.size());
        assertEquals(result, loadedResultList.get(0));
    }

    @Test
    public void load_csvHasTwoEntry_loadedResultListHasTwoEntry() throws IOException {
        //arrange
        ResultBuilder resultBuilder1 = new ResultBuilder();
        Result result1 = resultBuilder1.fireDepartment("firedepartment1").time(10.01).mistakePoints(5).image(new File(SaveServiceTest.class.getResource("/images/test_image.jpeg").getFile())).build();

        ResultBuilder resultBuilder2 = new ResultBuilder();
        Result result2 = resultBuilder2.fireDepartment("firedepartment2").time(22.01).mistakePoints(5).image(new File(SaveServiceTest.class.getResource("/images/test_image.jpeg").getFile())).build();

        saveService.save( Lists.newArrayList(result1, result2), SAVE_FILE_PATH);

        //act
        List<Result> loadedResultList = classUnderTest.load(SAVE_FILE_PATH);

        //assert
        assertEquals(2, loadedResultList.size());
        assertEquals(result1, loadedResultList.get(0));
        assertEquals(result2, loadedResultList.get(1));
    }

    @Test
    public void load_csvHasEntryWithSpecialCharacters_loadedResultListHasProperEncoding() throws IOException {
        //arrange
        ResultBuilder resultBuilder1 = new ResultBuilder();
        Result result = resultBuilder1.fireDepartment("äüöß§$%&/()=?``#*+").time(10.01).mistakePoints(5).image(new File(SaveServiceTest.class.getResource("/images/test_image.jpeg").getFile())).build();

        saveService.save(Lists.newArrayList(result), SAVE_FILE_PATH);

        //act
        List<Result> loadedResultList = classUnderTest.load(SAVE_FILE_PATH);

        //assert
        assertEquals(1, loadedResultList.size());
        assertEquals(result, loadedResultList.get(0));
    }
}