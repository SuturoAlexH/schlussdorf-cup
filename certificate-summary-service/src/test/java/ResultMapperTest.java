import factory.ResultBuilder;
import mapper.ResultMapper;
import model.Result;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ResultMapperTest {

    private static final int PLACE = 1;
    private static final String FIRE_DEPARTMENT = "fireDepartment";
    private static final double FINAL_SCORE = 100;

    private static Result result;

    @BeforeClass
    public static void startUp(){
        ResultBuilder resultBuilder = new ResultBuilder();
        result = resultBuilder.fireDepartment(FIRE_DEPARTMENT).place(PLACE).mistakePoints(400).build();
    }

    @Test(expected = NullPointerException.class)
    public void toCertificateSummaryData_null_illegalArgumentException(){
        //arrange

        //act
        ResultMapper.toCertificateSummaryData(null);

        //assert
    }

    @Test
    public void toCertificateSummaryData_normal_sizeIsThree(){
        //arrange

        //act
        String[] certificateSummaryData = ResultMapper.toCertificateSummaryData(result);

        //assert
        assertEquals(3, certificateSummaryData.length);
    }

    @Test
    public void toCertificateSummaryData_normal_firstEntryIsPlace(){
        //arrange

        //act
        String[] certificateSummaryData = ResultMapper.toCertificateSummaryData(result);

        //assert
        assertEquals(String.valueOf(PLACE), certificateSummaryData[0]);
    }

    @Test
    public void toCertificateSummaryData_normal_secondEntryIsFireDepartment(){
        //arrange

        //act
        String[] certificateSummaryData = ResultMapper.toCertificateSummaryData(result);

        //assert
        assertEquals(FIRE_DEPARTMENT, certificateSummaryData[1]);
    }

    @Test
    public void toCertificateSummaryData_normal_thirdEntryIsFinalScore(){
        //arrange

        //act
        String[] certificateSummaryData = ResultMapper.toCertificateSummaryData(result);

        //assert
        assertEquals(String.valueOf(FINAL_SCORE), certificateSummaryData[2]);
    }
}
