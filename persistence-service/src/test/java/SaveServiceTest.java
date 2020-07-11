import com.schlussdorf.exception.CsvFormatException;
import com.schlussdorf.factory.ResultBuilder;
import com.schlussdorf.model.Result;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.schlussdorf.service.LoadService;
import com.schlussdorf.service.SaveService;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SaveServiceTest {

    private static final String SAVE_FILE_PATH = "save/invalid_uuid.csv";

    private static final String INVALID_SAVE_FILE_PATH = "INVALID_SAVE_FILE_PATH";

    private SaveService classUnderTest;

    private LoadService loadService;

    @Before
    public void setUp(){
        loadService = new LoadService();
        classUnderTest = new SaveService();
    }

    @After
    public void tearDown(){
        File saveFile = new File(SAVE_FILE_PATH);
        saveFile.delete();

        File invalidSaveFile = new File(INVALID_SAVE_FILE_PATH);
        invalidSaveFile.delete();
    }

    @Test(expected = NullPointerException.class)
    public void save_resultListIsnull_illegalArgumentException() throws IOException {
        //arrange

        //act
        classUnderTest.save(null, SAVE_FILE_PATH);

        //assert
    }

    @Test(expected = NullPointerException.class)
    public void save_filePathIsnull_illegalArgumentException() throws IOException {
        //arrange

        //act
        classUnderTest.save(new ArrayList<>(), null);

        //assert
    }

    @Test
    public void save_filePathDoesNotExists_noError() throws IOException {
        //arrange

        //act
        classUnderTest.save(new ArrayList<>(), INVALID_SAVE_FILE_PATH);

        //assert
    }

    @Test
    public void save_resultListIsEmpty_loadedResultListIsEmpty() throws IOException, CsvFormatException {
        //arrange

        //act
        classUnderTest.save(new ArrayList<>(), SAVE_FILE_PATH);

        //assert
        List<Result> loadedResultList = loadService.load(SAVE_FILE_PATH);
        assertTrue(loadedResultList.isEmpty());
    }

    @Test
    public void save_resultListHasOneEntry_loadedResultListHasOneEntry() throws IOException, CsvFormatException {
        //arrange
        ResultBuilder resultBuilder1 = new ResultBuilder();
        Result result = resultBuilder1.fireDepartment("firedepartment1").time(10.01).mistakePoints(5).image(new File(SaveServiceTest.class.getResource("/images/test_image.jpeg").getFile())).build();

        List<Result> resultList = Collections.singletonList(result);

        //act
        classUnderTest.save(resultList, SAVE_FILE_PATH);

        //assert
        List<Result> loadedResultList = loadService.load(SAVE_FILE_PATH);
        assertEquals(1, loadedResultList.size());
        assertEquals(result, loadedResultList.get(0));
    }

    @Test
    public void save_resultListHasTwoEntry_loadedResultListHasTwoEntry() throws IOException, CsvFormatException {
        //arrange
        ResultBuilder resultBuilder1 = new ResultBuilder();
        Result result1 = resultBuilder1.fireDepartment("firedepartment1").time(10.01).mistakePoints(5).image(new File(SaveServiceTest.class.getResource("/images/test_image.jpeg").getFile())).build();

        ResultBuilder resultBuilder2 = new ResultBuilder();
        Result result2 = resultBuilder2.fireDepartment("firedepartment2").time(22.01).mistakePoints(5).image(new File(SaveServiceTest.class.getResource("/images/test_image.jpeg").getFile())).build();

        List<Result> resultList = Lists.newArrayList(result1, result2);

        //act
        classUnderTest.save(resultList, SAVE_FILE_PATH);

        //assert
        List<Result> loadedResultList = loadService.load(SAVE_FILE_PATH);
        assertEquals(2, loadedResultList.size());
        assertEquals(result1, loadedResultList.get(0));
        assertEquals(result2, loadedResultList.get(1));
    }

    @Test
    public void save_resultListHasEntryWithSpecialCharacters_loadedResultListHasProperEncoding() throws IOException, CsvFormatException {
        //arrange
        ResultBuilder resultBuilder1 = new ResultBuilder();
        Result result = resultBuilder1.fireDepartment("äüöß§$%&/()=?``#*+").time(10.01).mistakePoints(5).image(new File(SaveServiceTest.class.getResource("/images/test_image.jpeg").getFile())).build();

        List<Result> resultList = Lists.newArrayList(result);

        //act
        classUnderTest.save(resultList, SAVE_FILE_PATH);

        //assert
        List<Result> loadedResultList = loadService.load(SAVE_FILE_PATH);
        assertEquals(1, loadedResultList.size());
        assertEquals(result, loadedResultList.get(0));
    }
}