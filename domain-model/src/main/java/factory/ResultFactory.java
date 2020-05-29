package factory;

import model.Result;
import org.apache.commons.math3.util.Precision;

import java.util.UUID;

public class ResultFactory {

    private static final int START_POINTS = 500;

    public static Result create(UUID uuid, String fireDepartment, double time, int mistakePoints, final String imageFolder){
        double finalScore = Precision.round(START_POINTS - time - mistakePoints, 2);
        String imagePath = imageFolder + uuid + ".jpeg";

        return new Result(uuid, 0, fireDepartment, time, mistakePoints, finalScore, imagePath);
    }

    public static Result create(String fireDepartment, double time, int mistakePoints, final String imageFolder){
        UUID uuid = UUID.randomUUID();
        return create(uuid, fireDepartment, time, mistakePoints, imageFolder);
    }
}
