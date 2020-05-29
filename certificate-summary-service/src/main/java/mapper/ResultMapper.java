package mapper;

import model.Result;

public class ResultMapper {

    public static String[] toCertificateSummaryData(final Result result){
        if(result == null){
            throw new IllegalArgumentException("result is null");
        }

        return new String[]{String.valueOf(result.getPlace()), result.getFireDepartment(), String.valueOf(result.getFinalScore())};
    }
}
