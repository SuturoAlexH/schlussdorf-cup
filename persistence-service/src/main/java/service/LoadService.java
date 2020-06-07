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
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class LoadService {

    private final CsvReader reader = new CsvReader();

    public LoadService(){
        initialize();
    }

    private void initialize(){
        reader.setContainsHeader(true);
    }

    public List<Result> load(final String filePath) throws IOException {
        Objects.requireNonNull(filePath, "filePath is null");

        List<Result> result = new ArrayList<>();
        File saveFile = new File(filePath);

        CsvContainer csv = reader.read(saveFile, StandardCharsets.UTF_8);
        if (csv != null) {
            result.addAll(csv.getRows().stream().map(this::rowToResult).collect(Collectors.toList()));
        }

        return result;
    }

    private Result rowToResult(final CsvRow row){
        UUID uuid = UUID.fromString(row.getField(Constants.UUID));
        int place = Integer.parseInt(row.getField(Constants.PLACE));
        String fireDepartment = row.getField(Constants.FIRE_DEPARTMENT);
        double time = Double.parseDouble(row.getField(Constants.TIME));
        int mistakePoints = Integer.parseInt(row.getField(Constants.MISTAKE_POINTS));
        double finalScore = Double.parseDouble(row.getField(Constants.FINAL_SCORE));
        String imagePath = row.getField(Constants.IMAGE_PATH);

       return new Result(uuid, place, fireDepartment, time, mistakePoints, finalScore, new File(imagePath));
    }
}