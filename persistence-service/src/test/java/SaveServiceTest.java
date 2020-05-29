import com.google.common.collect.Lists;
import junit.framework.TestCase;
import model.Result;
import org.junit.After;
import org.junit.Test;
import service.LoadService;
import service.SaveService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SaveServiceTest {

    private static final String SAVE_FILE_PATH = "./save.csv";
    private static final String INVALID_SAVE_FILE_PATH = "INVALID_SAVE_FILE_PATH";

    @After
    public void tearDown(){
        File saveFile = new File(SAVE_FILE_PATH);
        saveFile.delete();

        File invalidSaveFile = new File(INVALID_SAVE_FILE_PATH);
        invalidSaveFile.delete();
    }

    @Test(expected = IllegalArgumentException.class)
    public void save_resultListIsnull_illegalArgumentException(){
        //arrange

        //act
        SaveService.save(null, SAVE_FILE_PATH);

        //assert
    }

    @Test(expected = IllegalArgumentException.class)
    public void save_filePathIsnull_illegalArgumentException(){
        //arrange

        //act
        SaveService.save(new ArrayList<>(), null);

        //assert
    }

    @Test
    public void save_filePathDoesNotExists_noError(){
        //arrange

        //act
        SaveService.save(new ArrayList<>(), INVALID_SAVE_FILE_PATH);

        //assert
    }

    @Test
    public void save_resultListIsEmpty_loadedResultListIsEmpty(){
        //arrange

        //act
        SaveService.save(new ArrayList<>(), SAVE_FILE_PATH);

        //assert
        List<Result> loadedResultList = LoadService.load(SAVE_FILE_PATH);
        assertTrue(loadedResultList.isEmpty());
    }

    @Test
    public void save_resultListHasOneEntry_loadedResultListHasOneEntry(){
        //arrange
        Result result = new Result(UUID.randomUUID(), 1, "fireDepartment1", 10.1, 5, 24.23, "imagePath1");
        List<Result> resultList = Lists.newArrayList(result);

        //act
        SaveService.save(resultList, SAVE_FILE_PATH);

        //assert
        List<Result> loadedResultList = LoadService.load(SAVE_FILE_PATH);
        assertEquals(1, loadedResultList.size());
        assertEquals(result, loadedResultList.get(0));
    }

    @Test
    public void save_resultListHasTwoEntry_loadedResultListHasTwoEntry(){
        //arrange
        Result result1 = new Result(UUID.randomUUID(), 1, "fireDepartment1", 10.1, 5, 24.23, "imagePath1");
        Result result2 = new Result(UUID.randomUUID(), 2, "fireDepartment2", 22.5, 10, 455.34, "imagePath2");
        List<Result> resultList = Lists.newArrayList(result1, result2);

        //act
        SaveService.save(resultList, SAVE_FILE_PATH);

        //assert
        List<Result> loadedResultList = LoadService.load(SAVE_FILE_PATH);
        assertEquals(2, loadedResultList.size());
        assertEquals(result1, loadedResultList.get(0));
        assertEquals(result2, loadedResultList.get(1));
    }

    @Test
    public void save_resultListHasEntryWithSpecialCharacters_loadedResultListHasProperEncoding(){
        //arrange
        Result result1 = new Result(UUID.randomUUID(), 1, "äüöß§$%&/()=?``#*+", 10.1, 5, 24.23, "imagePath1");
        List<Result> resultList = Lists.newArrayList(result1);

        //act
        SaveService.save(resultList, SAVE_FILE_PATH);

        //assert
        List<Result> loadedResultList = LoadService.load(SAVE_FILE_PATH);
        assertEquals(1, loadedResultList.size());
        assertEquals(result1, loadedResultList.get(0));
    }
}
