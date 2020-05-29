import constants.ReplacementConstants;
import mapper.CertificateReplacementMapper;
import model.Result;
import org.junit.BeforeClass;
import org.junit.Test;
import util.DateUtil;

import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CertificateReplacementMapperTest {

    private static final int PLACE = 1;
    private static final String FIRE_DEPARTMENT = "fireDepartment";
    private static final double FINAL_SCORE = 100.5;

    private static Result result;

    @BeforeClass
    public static void startUp(){
        result = new Result(UUID.randomUUID(), PLACE, FIRE_DEPARTMENT, 10.1, 5, FINAL_SCORE, "imagePath");
    }

    @Test(expected = IllegalArgumentException.class)
    public void toReplacementMap_null_illegalArgumentException(){
        CertificateReplacementMapper.toReplacementMap(null, DateUtil.getCurrentDateAsString(), DateUtil.getCurrentYearAsString());
    }

    @Test
    public void toReplacementMap_normal_sizeIs5(){
        //act
        Map<String, String> replacementMap = CertificateReplacementMapper.toReplacementMap(result, DateUtil.getCurrentDateAsString(), DateUtil.getCurrentYearAsString());

        //assert
        assertEquals(5, replacementMap.size());
    }

    @Test
    public void toReplacementMap_normal_fireDepartmentContainsReplacementMap(){
        //act
        Map<String, String> replacementMap = CertificateReplacementMapper.toReplacementMap(result, DateUtil.getCurrentDateAsString(), DateUtil.getCurrentYearAsString());

        //assert
        assertEquals(FIRE_DEPARTMENT, replacementMap.get(ReplacementConstants.FIRE_DEPARTMENT));
    }

    @Test
    public void toReplacementMap_normal_finalScoreContainsReplacementMap(){
        //act
        Map<String, String> replacementMap = CertificateReplacementMapper.toReplacementMap(result, DateUtil.getCurrentDateAsString(), DateUtil.getCurrentYearAsString());

        //assert
        assertEquals(String.valueOf(FINAL_SCORE), replacementMap.get(ReplacementConstants.FINAL_SCORE));
    }

    @Test
    public void toReplacementMap_normal_placeContainsReplacementMap(){
        //act
        Map<String, String> replacementMap = CertificateReplacementMapper.toReplacementMap(result, DateUtil.getCurrentDateAsString(), DateUtil.getCurrentYearAsString());

        //assert
        assertEquals(String.valueOf(PLACE), replacementMap.get(ReplacementConstants.PLACE));
    }

    @Test
    public void toReplacementMap_normal_currentDateContainsReplacementMap(){
        //act
        Map<String, String> replacementMap = CertificateReplacementMapper.toReplacementMap(result, DateUtil.getCurrentDateAsString(), DateUtil.getCurrentYearAsString());

        //assert
        assertEquals(DateUtil.getCurrentDateAsString(), replacementMap.get(ReplacementConstants.DATE));
    }

    @Test
    public void toReplacementMap_normal_currentYearContainsReplacementMap(){
        //act
        Map<String, String> replacementMap = CertificateReplacementMapper.toReplacementMap(result, DateUtil.getCurrentDateAsString(), DateUtil.getCurrentYearAsString());

        //assert
        assertEquals(DateUtil.getCurrentYearAsString(), replacementMap.get(ReplacementConstants.YEAR));
    }
}
