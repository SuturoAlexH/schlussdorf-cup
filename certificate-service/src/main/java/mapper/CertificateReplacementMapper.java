package mapper;

import constants.ReplacementConstants;
import model.Result;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CertificateReplacementMapper {

    public static Map<String, String> toReplacementMap(final Result result, final String currentDate, final String currentYear){
        Objects.requireNonNull(result, "result is null");
        Objects.requireNonNull(currentDate, "currentDate is null");
        Objects.requireNonNull(currentYear, "currentYear is null");

        Map<String, String> replacementMap = new HashMap<>();

        replacementMap.put(ReplacementConstants.FIRE_DEPARTMENT, result.getFireDepartment());
        replacementMap.put(ReplacementConstants.FINAL_SCORE, String.valueOf(result.getFinalScore()));
        replacementMap.put(ReplacementConstants.PLACE, String.valueOf(result.getPlace()));
        replacementMap.put(ReplacementConstants.DATE, currentDate);
        replacementMap.put(ReplacementConstants.YEAR, currentYear);

        return replacementMap;
    }
}
