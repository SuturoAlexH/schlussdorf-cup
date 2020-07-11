import org.junit.Test;
import com.schlussdorf.service.FinalScoreCalculatorService;

import static org.junit.Assert.assertEquals;

public class FinalScoreCalculatorServiceTest {

    private FinalScoreCalculatorService classUnderTest = new FinalScoreCalculatorService();

   @Test
    public void test(){
       //arrange

       //act
       double finalScore = classUnderTest.calculate(1, 0);

       //assert
       assertEquals(499, finalScore, 0.0);
   }
}
