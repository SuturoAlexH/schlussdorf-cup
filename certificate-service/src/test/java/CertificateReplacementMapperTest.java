import com.schlussdorf.constants.PlaceHolderConstants;
import com.schlussdorf.factory.ResultBuilder;
import com.schlussdorf.mapper.CertificateReplacementMapper;
import com.schlussdorf.model.Result;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CertificateReplacementMapperTest {

    private static final int PLACE = 1;
    private static final String FIRE_DEPARTMENT = "fireDepartment";
    private static final double FINAL_SCORE = 100.5;
    private static final String CURRENT_DATE = "06.06.2020";
    private static final String CURRENT_YEAR = "2020";

    private CertificateReplacementMapper classUnderTest;

    private Result result;

    @Before
    public void setUp(){
        classUnderTest = new CertificateReplacementMapper();

        ResultBuilder resultBuilder = new ResultBuilder();
        result = resultBuilder.place(PLACE)
                .fireDepartment(FIRE_DEPARTMENT)
                .finalScore(FINAL_SCORE)
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void toReplacementMap_resultIsNull_nullPointerException(){
        //arrange

        //act
        classUnderTest.toReplacementMap(null, CURRENT_DATE, CURRENT_YEAR);

        //assert
    }

    @Test(expected = NullPointerException.class)
    public void toReplacementMap_currentDateIsNull_nullPointerException(){
        //arrange

        //act
        classUnderTest.toReplacementMap(result, null, CURRENT_YEAR);

        //assert
    }

    @Test(expected = NullPointerException.class)
    public void toReplacementMap_currentYearIsNull_nullPointerException(){
        //arrange

        //act
        classUnderTest.toReplacementMap(result, CURRENT_DATE, null);

        //assert
    }

    @Test
    public void toReplacementMap_normal_sizeIs5(){
        //arrange

        //act
        Map<String, String> replacementMap = classUnderTest.toReplacementMap(result, CURRENT_DATE, CURRENT_YEAR);

        //assert
        assertEquals(5, replacementMap.size());
    }

    @Test
    public void toReplacementMap_normal_fireDepartmentContainsReplacementMap(){
        //arrange

        //act
        Map<String, String> replacementMap = classUnderTest.toReplacementMap(result, CURRENT_DATE, CURRENT_YEAR);

        //assert
        assertEquals(FIRE_DEPARTMENT, replacementMap.get(PlaceHolderConstants.FIRE_DEPARTMENT));
    }

    @Test
    public void toReplacementMap_normal_finalScoreContainsReplacementMap(){
        //arrange

        //act
        Map<String, String> replacementMap = classUnderTest.toReplacementMap(result, CURRENT_DATE, CURRENT_YEAR);

        //assert
        assertEquals(String.valueOf(FINAL_SCORE), replacementMap.get(PlaceHolderConstants.FINAL_SCORE));
    }

    @Test
    public void toReplacementMap_normal_placeContainsReplacementMap(){
        //arrange

        //act
        Map<String, String> replacementMap = classUnderTest.toReplacementMap(result, CURRENT_DATE, CURRENT_YEAR);

        //assert
        assertEquals(String.valueOf(PLACE), replacementMap.get(PlaceHolderConstants.PLACE));
    }

    @Test
    public void toReplacementMap_normal_currentDateContainsReplacementMap(){
        //arrange

        //act
        Map<String, String> replacementMap = classUnderTest.toReplacementMap(result, CURRENT_DATE, CURRENT_YEAR);

        //assert
        assertEquals(CURRENT_DATE, replacementMap.get(PlaceHolderConstants.DATE));
    }

    @Test
    public void toReplacementMap_normal_currentYearContainsReplacementMap(){
        //arrange

        //act
        Map<String, String> replacementMap = classUnderTest.toReplacementMap(result, CURRENT_DATE, CURRENT_YEAR);

        //assert
        assertEquals(CURRENT_YEAR, replacementMap.get(PlaceHolderConstants.YEAR));
    }
}
