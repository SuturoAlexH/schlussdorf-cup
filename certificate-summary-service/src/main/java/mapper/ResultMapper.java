package mapper;

import model.Result;

import java.util.Objects;

public class ResultMapper {

    public static String[] toCertificateSummaryData(final Result result){
        Objects.requireNonNull(result, "result is null");

        return new String[]{String.valueOf(result.getPlace()), result.getFireDepartment(), String.valueOf(result.getFinalScore())};
    }
}
