import com.google.common.collect.Lists;
import model.Result;
import org.junit.After;
import org.junit.Test;
import service.LoadService;
import service.SaveService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class LoadServiceTest {

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
    public void load_filePathIsNull_illegalArgumentException(){
        //arrange

        //act
        LoadService.load(null);

        //assert
    }

    @Test
    public void load_filePathDoesNotExists_resultListIsEmpty(){
        //arrange

        //act
        List<Result> loadedResultList = LoadService.load(INVALID_SAVE_FILE_PATH);

        //assert
        assertTrue(loadedResultList.isEmpty());
    }

    @Test
    public void load_csvIsEmpty_resultListIsEmpty(){
        //arrange
        SaveService.save(new ArrayList<>(), SAVE_FILE_PATH);

        //act
        List<Result> loadedResultList = LoadService.load(SAVE_FILE_PATH);

        //assert
        assertTrue(loadedResultList.isEmpty());
    }

    @Test
    public void load_csvHasOneEntry_resultListHasOneEntry(){
        //arrange
        Result result = new Result(UUID.randomUUID(), 1, "fireDepartment1", 10.1, 5, 24.23, "imagePath1");
        SaveService.save(Lists.newArrayList(result), SAVE_FILE_PATH);

        //act
        List<Result> loadedResultList = LoadService.load(SAVE_FILE_PATH);

        //assert
        assertEquals(1, loadedResultList.size());
        assertEquals(result, loadedResultList.get(0));
    }

    @Test
    public void load_csvHasTwoEntry_loadedResultListHasTwoEntry(){
        //arrange
        Result result1 = new Result(UUID.randomUUID(), 1, "fireDepartment1", 10.1, 5, 24.23, "imagePath1");
        Result result2 = new Result(UUID.randomUUID(), 2, "fireDepartment2", 22.5, 10, 455.34, "imagePath2");
        SaveService.save( Lists.newArrayList(result1, result2), SAVE_FILE_PATH);

        //act
        List<Result> loadedResultList = LoadService.load(SAVE_FILE_PATH);

        //assert
        assertEquals(2, loadedResultList.size());
        assertEquals(result1, loadedResultList.get(0));
        assertEquals(result2, loadedResultList.get(1));
    }

    @Test
    public void load_csvHasEntryWithSpecialCharacters_loadedResultListHasProperEncoding(){
        //arrange
        Result result1 = new Result(UUID.randomUUID(), 1, "äüöß§$%&/()=?``#*+", 10.1, 5, 24.23, "imagePath1");
        SaveService.save(Lists.newArrayList(result1), SAVE_FILE_PATH);

        //act
        List<Result> loadedResultList = LoadService.load(SAVE_FILE_PATH);

        //assert
        assertEquals(1, loadedResultList.size());
        assertEquals(result1, loadedResultList.get(0));
    }
}
