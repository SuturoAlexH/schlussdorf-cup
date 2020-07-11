package com.schlussdorf.service;

import com.schlussdorf.constants.CsvConstants;
import de.siegmar.fastcsv.writer.CsvAppender;
import de.siegmar.fastcsv.writer.CsvWriter;
import com.schlussdorf.model.Result;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * This is a com.schlussdorf.service that writes to a csv file a list of results.
 */
public class SaveService {

    /**
     * The csv writer.
     */
    private final CsvWriter writer = new CsvWriter();

    /**
     * Takes a file path to a csv file and a list of results and writes the list of result
     * to the csv file. If the csv does not exists or folders inside the path not exists then
     * they are created.
     *
     * @param resultList the list of results
     * @param filePath the file path to the csv file
     * @throws IOException if the csv file is broken
     */
    public void save(final List<Result> resultList, final String filePath) throws IOException {
        Objects.requireNonNull(resultList, "resultList is null");
        Objects.requireNonNull(filePath, "filePath is null");

        //create save file if it not exists
        File saveFile = new File(filePath);
        if(!saveFile.exists()){

            //create folders if they doesn't exist
            if(saveFile.getParentFile() != null) {
                saveFile.getParentFile().mkdirs();
            }
            saveFile.createNewFile();
        }

        //set csv content
        try (CsvAppender csvAppender = writer.append(saveFile, StandardCharsets.UTF_8)) {
            csvAppender.appendLine(CsvConstants.HEADER);
            for(Result result: resultList){
                appendLine(csvAppender, result);
            }
        }
    }

    private void appendLine(final CsvAppender csvAppender, final Result entry) throws IOException {
            csvAppender.appendField(String.valueOf(entry.getUuid()));
            csvAppender.appendField(String.valueOf(entry.getPlace()));
            csvAppender.appendField(entry.getFireDepartment());
            csvAppender.appendField(String.valueOf(entry.getTime()));
            csvAppender.appendField(String.valueOf(entry.getMistakePoints()));
            csvAppender.appendField(String.valueOf(entry.getFinalScore()));
            csvAppender.appendField(String.valueOf(entry.getImage().getAbsoluteFile()));
            csvAppender.endLine();
    }
}