package service;

import constants.CsvConstants;
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

/**
 * This is a service that reads a file path to a csv file and return a list of results.
 */
public class LoadService {

    /**
     * The csv reader.
     */
    private final CsvReader reader = new CsvReader();

    /**
     * Default constructor set contains header to true.
     */
    public LoadService(){
        reader.setContainsHeader(true);
    }

    /**
     * Takes a file path to a csv file and converts reads the content and converts it to a list of results.
     *
     * @param filePath the file path to the csv file
     * @return the list of results
     * @throws IOException if the csv file is broken
     */
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
        UUID uuid = UUID.fromString(row.getField(CsvConstants.UUID));
        int place = Integer.parseInt(row.getField(CsvConstants.PLACE));
        String fireDepartment = row.getField(CsvConstants.FIRE_DEPARTMENT);
        double time = Double.parseDouble(row.getField(CsvConstants.TIME));
        int mistakePoints = Integer.parseInt(row.getField(CsvConstants.MISTAKE_POINTS));
        double finalScore = Double.parseDouble(row.getField(CsvConstants.FINAL_SCORE));
        String imagePath = row.getField(CsvConstants.IMAGE_PATH);

       return new Result(uuid, place, fireDepartment, time, mistakePoints, finalScore, new File(imagePath));
    }
}