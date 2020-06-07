package service;

import org.apache.commons.math3.util.Precision;

public class FinalScoreCalculatorService {

    private static final int START_POINTS = 500;

    private static final int DIGITS_COUNT = 2;

    public double calculate(final double time, final int mistakePoints){
        return Precision.round(START_POINTS - time - mistakePoints, DIGITS_COUNT);
    }
}
