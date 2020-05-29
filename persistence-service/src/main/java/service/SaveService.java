package service;

import constants.Constants;
import de.siegmar.fastcsv.writer.CsvAppender;
import de.siegmar.fastcsv.writer.CsvWriter;
import model.Result;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SaveService {

    private static final CsvWriter WRITER = new CsvWriter();

    public static void save(final List<Result> resultList, final String filePath) {
        if(resultList == null){
            throw new IllegalArgumentException("resultList is null");
        }

        if(filePath == null){
            throw new IllegalArgumentException("filePath is null");
        }

        File saveFile = new File(filePath);
        try (CsvAppender csvAppender = WRITER.append(saveFile, StandardCharsets.UTF_8)) {
            csvAppender.appendLine(Constants.HEADER);
            resultList.forEach(result -> appendLine(csvAppender, result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void appendLine(final CsvAppender csvAppender, final Result entry){
        try {
            csvAppender.appendField(String.valueOf(entry.getUuid()));
            csvAppender.appendField(String.valueOf(entry.getPlace()));
            csvAppender.appendField(entry.getFireDepartment());
            csvAppender.appendField(String.valueOf(entry.getTime()));
            csvAppender.appendField(String.valueOf(entry.getMistakePoints()));
            csvAppender.appendField(String.valueOf(entry.getFinalScore()));
            csvAppender.appendField(String.valueOf(entry.getImagePath()));
            csvAppender.endLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
