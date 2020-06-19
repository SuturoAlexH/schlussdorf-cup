package mapper;

import constants.PlaceHolderConstants;
import model.Result;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A mapper that extract data from a result.
 */
public class CertificateReplacementMapper {

    /**
     * Extracts the needed data from the given result.
     *
     * @param result the result from where the data is extracted
     * @param date the date as additional information
     * @param year the year as additional information
     * @return a map that contains the place, fire department, final score, date and year
     */
    public Map<String, String> toReplacementMap(final Result result, final String date, final String year){
        Objects.requireNonNull(result, "result is null");
        Objects.requireNonNull(date, "currentDate is null");
        Objects.requireNonNull(year, "year is null");

        Map<String, String> replacementMap = new HashMap<>();

        replacementMap.put(PlaceHolderConstants.FIRE_DEPARTMENT, result.getFireDepartment());
        replacementMap.put(PlaceHolderConstants.FINAL_SCORE, String.valueOf(result.getFinalScore()));
        replacementMap.put(PlaceHolderConstants.PLACE, String.valueOf(result.getPlace()));
        replacementMap.put(PlaceHolderConstants.DATE, date);
        replacementMap.put(PlaceHolderConstants.YEAR, year);

        return replacementMap;
    }
}
