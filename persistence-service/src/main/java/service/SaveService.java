package service;

import constants.Constants;
import de.siegmar.fastcsv.writer.CsvAppender;
import de.siegmar.fastcsv.writer.CsvWriter;
import model.Result;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.InvalidPathException;
import java.util.List;
import java.util.Objects;

public class SaveService {

    private final CsvWriter writer = new CsvWriter();

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
            csvAppender.appendLine(Constants.HEADER);
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