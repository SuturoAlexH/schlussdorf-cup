import com.schlussdorf.builder.ResultBuilder;
import com.schlussdorf.model.Result;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResultBuilderTest {

    private ResultBuilder classUnderTest;

    @Before
    public void setUp(){
        classUnderTest = new ResultBuilder();
    }

    @Test
    public void uuid_normal_uuidIsSet(){
        //arrange
        UUID uuid = UUID.randomUUID();

        //act
        Result result = classUnderTest.uuid(uuid).build();

        //assert
        assertEquals(uuid, result.getUuid());
    }

    @Test
    public void place_normal_placeIsSet(){
        //arrange

        //act
        Result result = classUnderTest.place(1).build();

        //assert
        assertEquals(1, result.getPlace());
    }

    @Test
    public void fireDepartment_normal_fireDepartmentIsSet(){
        //arrange

        //act
        Result result = classUnderTest.fireDepartment("Feuerwehr").build();

        //assert
        assertEquals("Feuerwehr", result.getFireDepartment());
    }

    @Test
    public void time_commaSeparated_timeIsSet(){
        //arrange

        //act
        Result result = classUnderTest.time("20,2").build();

        //assert
        assertEquals(20.2, result.getTime(),0);
    }

    @Test
    public void time_dotSeparated_timeIsSet(){
        //arrange

        //act
        Result result = classUnderTest.time("20.2").build();

        //assert
        assertEquals(20.2, result.getTime(),0);
    }

    @Test
    public void time_double_timeIsSet(){
        //arrange

        //act
        Result result = classUnderTest.time(20.2).build();

        //assert
        assertEquals(20.2, result.getTime(),0);
    }

    @Test
    public void mistakePoints_null_mistakePointsAreSet(){
        //arrange

        //act
        Result result = classUnderTest.mistakePoints(null).build();

        //assert
        assertEquals(0, result.getMistakePoints());
    }

    @Test
    public void mistakePoints_empty_mistakePointsAreSet(){
        //arrange

        //act
        Result result = classUnderTest.mistakePoints("").build();

        //assert
        assertEquals(0, result.getMistakePoints());
    }

    @Test
    public void mistakePoints_emptyWithSpace_mistakePointsAreSet(){
        //arrange

        //act
        Result result = classUnderTest.mistakePoints(" ").build();

        //assert
        assertEquals(0, result.getMistakePoints());
    }

    @Test
    public void mistakePoints_string_mistakePointsAreSet(){
        //arrange

        //act
        Result result = classUnderTest.mistakePoints("5").build();

        //assert
        assertEquals(5, result.getMistakePoints());
    }

    @Test
    public void mistakePoints_int_mistakePointsAreSet(){
        //arrange

        //act
        Result result = classUnderTest.mistakePoints(5).build();

        //assert
        assertEquals(5, result.getMistakePoints());
    }

    @Test
    public void finalScore_setDirectly_finalScoreIsSet(){
        //arrange

        //act
        Result result = classUnderTest.finalScore(20.5).build();

        //assert
        assertEquals(20.5, result.getFinalScore(), 0);
    }

    @Test
    public void finalScore_setInDirectly_finalScoreIsSet(){
        //arrange

        //act
        Result result = classUnderTest.time(30).mistakePoints(10).build();

        //assert
        assertEquals(460, result.getFinalScore(), 0);
    }

    @Test
    public void image_normal_imageIsSet(){
        //arrange
        File imageFile = new File(ResultBuilderTest.class.getResource("/images/test_image_1.jpeg").getFile());

        //act
        Result result = classUnderTest.image(imageFile).build();

        //assert
        assertNotNull(result.getImage());
    }
}