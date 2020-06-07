package factory;

import model.Result;
import org.apache.commons.math3.util.Precision;
import service.FinalScoreCalculatorService;

import java.io.File;
import java.util.UUID;

public class ResultBuilder {

    private UUID uuid = UUID.randomUUID();

    private int place = 0;

    private String fireDepartment;

    private double time;

    private int mistakePoints;

    private File image;

    private FinalScoreCalculatorService finalScoreCalculatorService = new FinalScoreCalculatorService();

    public ResultBuilder uuid(final UUID uuid){
        this.uuid = uuid;

        return this;
    }

    public ResultBuilder place(final int place){
        this.place = place;

        return this;
    }

    public ResultBuilder fireDepartment(final String fireDepartment){
        this.fireDepartment = fireDepartment;

        return this;
    }

    public ResultBuilder time(final double time){
        this.time = time;

        return this;
    }

    public ResultBuilder time(final String time){
        this.time = Double.valueOf(time);

        return this;
    }

    public ResultBuilder mistakePoints(final int mistakePoints){
        this.mistakePoints = mistakePoints;

        return this;
    }

    public ResultBuilder mistakePoints(final String mistakePoints){
        this.mistakePoints = Integer.valueOf(mistakePoints);

        return this;
    }

    public ResultBuilder image(final File image){
        this.image = image;

        return this;
    }

    public Result build(){
        double finalScore = finalScoreCalculatorService.calculate(time, mistakePoints);
        return new Result(uuid, place, fireDepartment, time, mistakePoints, finalScore, image);
    }
}
