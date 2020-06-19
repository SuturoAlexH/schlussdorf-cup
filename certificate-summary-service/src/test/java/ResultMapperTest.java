import factory.ResultBuilder;
import mapper.ResultMapper;
import model.Result;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResultMapperTest {

    private static final int PLACE = 1;
    private static final String FIRE_DEPARTMENT = "fireDepartment";
    private static final double FINAL_SCORE = 100;

    private ResultMapper classUnderTest;

    private Result result;

    @Before
    public void setUp(){
        classUnderTest = new ResultMapper();

        ResultBuilder resultBuilder = new ResultBuilder();
        result = resultBuilder.fireDepartment(FIRE_DEPARTMENT)
                .place(PLACE)
                .finalScore(FINAL_SCORE)
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void toCertificateSummaryData_null_illegalArgumentException(){
        //arrange

        //act
        classUnderTest.toCertificateSummaryData(null);

        //assert
    }

    @Test
    public void toCertificateSummaryData_normal_sizeIsThree(){
        //arrange

        //act
        String[] certificateSummaryData = classUnderTest.toCertificateSummaryData(result);

        //assert
        assertEquals(3, certificateSummaryData.length);
    }

    @Test
    public void toCertificateSummaryData_normal_firstEntryIsPlace(){
        //arrange

        //act
        String[] certificateSummaryData = classUnderTest.toCertificateSummaryData(result);

        //assert
        assertEquals(String.valueOf(PLACE), certificateSummaryData[0]);
    }

    @Test
    public void toCertificateSummaryData_normal_secondEntryIsFireDepartment(){
        //arrange

        //act
        String[] certificateSummaryData = classUnderTest.toCertificateSummaryData(result);

        //assert
        assertEquals(FIRE_DEPARTMENT, certificateSummaryData[1]);
    }

    @Test
    public void toCertificateSummaryData_normal_thirdEntryIsFinalScore(){
        //arrange

        //act
        String[] certificateSummaryData = classUnderTest.toCertificateSummaryData(result);

        //assert
        assertEquals(String.valueOf(FINAL_SCORE), certificateSummaryData[2]);
    }
}
