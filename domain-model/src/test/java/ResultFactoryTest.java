import factory.ResultFactory;
import model.Result;
import org.junit.Test;

public class ResultFactoryTest {

    @Test
    public void create_finalScore_(){
        //arrange

        for(int i = 0; i <= 499; i++){
            for(int j = 0; j <= 9; j++){
                for(int k = 0; k <= 9; k++){
                    double time = Double.parseDouble(i + "." + j + k);
                    Result result = ResultFactory.create(null,time, 0, null);
                    System.out.println("time :" +  time + " finalScore: " + result.getFinalScore());
                }
            }
        }
        //act


        //arrange

    }
}
