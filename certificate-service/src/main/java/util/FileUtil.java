package util;

import com.google.common.collect.Iterables;
import logger.CustomLogger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FileUtil {

    public static void copyFolder(final String sourceFolderPath, final String targetFolderPath) throws IOException {
        File sourceFolder = new File(sourceFolderPath);
        File targetFolder = new File(targetFolderPath);
        if(targetFolder.exists()){
            targetFolder.mkdirs();
        }

        FileUtils.copyDirectory(sourceFolder, targetFolder);
    }

    public static void copyFile(final String filePath, final String targetFilePath) throws IOException {
        File file = new File(filePath);
        File targetFile = new File(targetFilePath);

        FileUtils.copyFile(file, targetFile);
    }

    public static void clearFolder(final String folderPath) throws IOException {
        File folder = new File(folderPath);

        FileUtils.cleanDirectory(folder);
    }

    public static void replacePlaceHolder(final String filePath, final Map<String, String> replacementMap) {
        FileWriter fw = null;
        try{
            String fileContent = Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
            String modifiedContent = StringUtils.replaceEach(fileContent, Iterables.toArray(replacementMap.keySet(), String.class), Iterables.toArray(replacementMap.values(), String.class));

            fw = new FileWriter(filePath, StandardCharsets.UTF_8);
            fw.write(modifiedContent);
        }catch (IOException e){
            e.printStackTrace();
            CustomLogger.LOGGER.info(e.getMessage());
        }finally {
            if(fw != null){
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
