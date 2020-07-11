import org.junit.Test;
import com.schlussdorf.service.FinalScoreCalculatorService;

import static org.junit.Assert.assertEquals;

public class FinalScoreCalculatorServiceTest {

    private FinalScoreCalculatorService classUnderTest = new FinalScoreCalculatorService();

   @Test
    public void calculate_10_4990(){
       //arrange

       //act
       double finalScore = classUnderTest.calculate(1, 0);

       //assert
       assertEquals(499, finalScore, 0.0);
   }

    @Test
    public void calculate_09_4991(){
        //arrange

        //act
        double finalScore = classUnderTest.calculate(0.9, 0);

        //assert
        assertEquals(499.1, finalScore, 0.0);
    }

    @Test
    public void calculate_08_4992(){
        //arrange

        //act
        double finalScore = classUnderTest.calculate(0.8, 0);

        //assert
        assertEquals(499.2, finalScore, 0.0);
    }

    @Test
    public void calculate_07_4993(){
        //arrange

        //act
        double finalScore = classUnderTest.calculate(0.7, 0);

        //assert
        assertEquals(499.3, finalScore, 0.0);
    }

    @Test
    public void calculate_06_4994(){
        //arrange

        //act
        double finalScore = classUnderTest.calculate(0.6, 0);

        //assert
        assertEquals(499.4, finalScore, 0.0);
    }

    @Test
    public void calculate_05_4995(){
        //arrange

        //act
        double finalScore = classUnderTest.calculate(0.5, 0);

        //assert
        assertEquals(499.5, finalScore, 0.0);
    }

    @Test
    public void calculate_04_4996(){
        //arrange

        //act
        double finalScore = classUnderTest.calculate(0.4, 0);

        //assert
        assertEquals(499.6, finalScore, 0.0);
    }

    @Test
    public void calculate_03_4997(){
        //arrange

        //act
        double finalScore = classUnderTest.calculate(0.3, 0);

        //assert
        assertEquals(499.7, finalScore, 0.0);
    }

    @Test
    public void calculate_02_4998(){
        //arrange

        //act
        double finalScore = classUnderTest.calculate(0.2, 0);

        //assert
        assertEquals(499.8, finalScore, 0.0);
    }

    @Test
    public void calculate_01_4999(){
        //arrange

        //act
        double finalScore = classUnderTest.calculate(0.1, 0);

        //assert
        assertEquals(499.9, finalScore, 0.0);
    }

    @Test
    public void calculate_00_500(){
        //arrange

        //act
        double finalScore = classUnderTest.calculate(0.0, 0);

        //assert
        assertEquals(500, finalScore, 0.0);
    }
}
