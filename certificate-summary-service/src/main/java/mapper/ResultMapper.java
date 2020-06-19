package mapper;

import model.Result;

import java.util.Objects;

/**
 * A mapper that extract data from a result.
 */
public class ResultMapper {

    /**
     * Extracts the needed data from the given result.
     *
     * @param result the result from where the data is extracted
     * @return an array that contains the place, fire department and final score
     **/
    public String[] toCertificateSummaryData(final Result result){
        Objects.requireNonNull(result, "result is null");

        return new String[]{String.valueOf(result.getPlace()), result.getFireDepartment(), String.valueOf(result.getFinalScore())};
    }
}
