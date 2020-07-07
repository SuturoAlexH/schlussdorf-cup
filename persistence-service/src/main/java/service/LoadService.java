package service;

import constants.CsvConstants;
import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import exception.CsvFormatException;
import model.Result;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * This is a service that reads a file path to a csv file and return a list of results.
 */
public class LoadService {

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
    public List<Result> load(final String filePath) throws IOException, CsvFormatException {
        Objects.requireNonNull(filePath, "filePath is null");

        List<Result> resultList = new ArrayList<>();
        File saveFile = new File(filePath);
        if(!saveFile.exists()){
            return resultList;
        }

        CsvContainer csv = reader.read(saveFile, StandardCharsets.UTF_8);
        if(csv != null) {
            checkHeaders(csv.getHeader());

            for (int i = 0; i < csv.getRows().size(); i++) {
                CsvRow currentCsvRow = csv.getRows().get(i);
                resultList.add(rowToResult(currentCsvRow, i));
            }
        }

        return resultList;
    }

    private void checkHeaders(final List<String> header) throws CsvFormatException {
        if(CsvConstants.HEADER.length != header.size()){
            throw new CsvFormatException("Header size is wrong! Expected " + CsvConstants.HEADER.length + ", but actual " + header.size());
        }

        for(int i = 0; i < header.size(); i++){
            if(!CsvConstants.HEADER[i].equals(header.get(i))){
                throw new CsvFormatException("Header at position " + i + " is wrong! Expected " + CsvConstants.HEADER[i] + ", but actual " + header.get(i));
            }
        }
    }

    private Result rowToResult(final CsvRow row, final int index) throws CsvFormatException {
        if(CsvConstants.ROW_SIZE != row.getFieldCount()){
            throw new CsvFormatException("Invalid row count in row " + (index+1) + "! Expected " + CsvConstants.ROW_SIZE + ", but actual " + row.getFieldCount());
        }

        UUID uuid;
        try {
             uuid = UUID.fromString(row.getField(CsvConstants.UUID));
        }catch(Exception e){
            throw new CsvFormatException("Invalid uuid in row " + (index+1) + " because: " + e.getMessage());
        }

        int place;
        try {
            place = Integer.parseInt(row.getField(CsvConstants.PLACE));
        }catch(Exception e){
            throw new CsvFormatException("Invalid place in row " + (index+1) + " because: " + e.getMessage());
        }

        String fireDepartment = row.getField(CsvConstants.FIRE_DEPARTMENT);

        double time;
        try {
            time = Double.parseDouble(row.getField(CsvConstants.TIME));
        }catch(Exception e){
            throw new CsvFormatException("Invalid time in row " + (index+1) + " because: " + e.getMessage());
        }

        int mistakePoints;
        try {
            mistakePoints = Integer.parseInt(row.getField(CsvConstants.MISTAKE_POINTS));
        }catch(Exception e){
            throw new CsvFormatException("Invalid mistake points in row " + (index+1) + " because: " + e.getMessage());
        }

        double finalScore;
        try {
            finalScore = Double.parseDouble(row.getField(CsvConstants.FINAL_SCORE));
        }catch(Exception e){
            throw new CsvFormatException("Invalid final score in row " + (index+1) + " because: " + e.getMessage());
        }

        String imagePath = row.getField(CsvConstants.IMAGE_PATH);

        return new Result(uuid, place, fireDepartment, time, mistakePoints, finalScore, new File(imagePath));
    }
}