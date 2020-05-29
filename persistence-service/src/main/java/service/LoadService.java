package service;

import constants.Constants;
import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import model.Result;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class LoadService {

    private static final CsvReader READER = new CsvReader();

    static {
        READER.setContainsHeader(true);
    }

    public static List<Result> load(final String filePath) {
        if(filePath == null){
            throw new IllegalArgumentException("filePath is null");
        }

        List<Result> result = new ArrayList<>();
        File saveFile = new File(filePath);

        try {
            CsvContainer csv = READER.read(saveFile, StandardCharsets.UTF_8);
            if (csv != null) {
                result.addAll(csv.getRows().stream().map(LoadService::rowToResult).collect(Collectors.toList()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static Result rowToResult(final CsvRow row){
        UUID uuid = UUID.fromString(row.getField(Constants.UUID));
        int place = Integer.parseInt(row.getField(Constants.PLACE));
        String fireDepartment = row.getField(Constants.FIRE_DEPARTMENT);
        double time = Double.parseDouble(row.getField(Constants.TIME));
        int mistakePoints = Integer.parseInt(row.getField(Constants.MISTAKE_POINTS));
        double finalScore = Double.parseDouble(row.getField(Constants.FINAL_SCORE));
        String imagePath = row.getField(Constants.IMAGE_PATH);

       return new Result(uuid, place, fireDepartment, time, mistakePoints, finalScore, imagePath) ;
    }

}
