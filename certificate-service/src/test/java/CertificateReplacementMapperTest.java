import constants.ReplacementConstants;
import mapper.CertificateReplacementMapper;
import model.Result;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CertificateReplacementMapperTest {

    private static final int PLACE = 1;
    private static final String FIRE_DEPARTMENT = "fireDepartment";
    private static final double FINAL_SCORE = 100.5;
    private static final String CURRENT_DATE = "06.06.2020";
    private static final String CURRENT_YEAR = "2020";

    private static Result result;

    @BeforeClass
    public static void startUp(){
        result = new Result(UUID.randomUUID(), PLACE, FIRE_DEPARTMENT, 10.1, 5, FINAL_SCORE, new File(CertificateReplacementMapperTest.class.getResource("/images/test_image.jpeg").getFile()));
    }

    @Test(expected = NullPointerException.class)
    public void toReplacementMap_resultIsNull_nullPointerException(){
        CertificateReplacementMapper.toReplacementMap(null, CURRENT_DATE, CURRENT_YEAR);
    }

    @Test(expected = NullPointerException.class)
    public void toReplacementMap_currentDateIsNull_nullPointerException(){
        CertificateReplacementMapper.toReplacementMap(result, null, CURRENT_YEAR);
    }

    @Test(expected = NullPointerException.class)
    public void toReplacementMap_currentYearIsNull_nullPointerException(){
        CertificateReplacementMapper.toReplacementMap(result, CURRENT_DATE, null);
    }

    @Test
    public void toReplacementMap_normal_sizeIs5(){
        //act
        Map<String, String> replacementMap = CertificateReplacementMapper.toReplacementMap(result, CURRENT_DATE, CURRENT_YEAR);

        //assert
        assertEquals(5, replacementMap.size());
    }

    @Test
    public void toReplacementMap_normal_fireDepartmentContainsReplacementMap(){
        //act
        Map<String, String> replacementMap = CertificateReplacementMapper.toReplacementMap(result, CURRENT_DATE, CURRENT_YEAR);

        //assert
        assertEquals(FIRE_DEPARTMENT, replacementMap.get(ReplacementConstants.FIRE_DEPARTMENT));
    }

    @Test
    public void toReplacementMap_normal_finalScoreContainsReplacementMap(){
        //act
        Map<String, String> replacementMap = CertificateReplacementMapper.toReplacementMap(result, CURRENT_DATE, CURRENT_YEAR);

        //assert
        assertEquals(String.valueOf(FINAL_SCORE), replacementMap.get(ReplacementConstants.FINAL_SCORE));
    }

    @Test
    public void toReplacementMap_normal_placeContainsReplacementMap(){
        //act
        Map<String, String> replacementMap = CertificateReplacementMapper.toReplacementMap(result, CURRENT_DATE, CURRENT_YEAR);

        //assert
        assertEquals(String.valueOf(PLACE), replacementMap.get(ReplacementConstants.PLACE));
    }

    @Test
    public void toReplacementMap_normal_currentDateContainsReplacementMap(){
        //act
        Map<String, String> replacementMap = CertificateReplacementMapper.toReplacementMap(result, CURRENT_DATE, CURRENT_YEAR);

        //assert
        assertEquals(CURRENT_DATE, replacementMap.get(ReplacementConstants.DATE));
    }

    @Test
    public void toReplacementMap_normal_currentYearContainsReplacementMap(){
        //act
        Map<String, String> replacementMap = CertificateReplacementMapper.toReplacementMap(result, CURRENT_DATE, CURRENT_YEAR);

        //assert
        assertEquals(CURRENT_YEAR, replacementMap.get(ReplacementConstants.YEAR));
    }
}
