package util;

import com.google.common.collect.Iterables;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 *
 */
public class FileUtil {

    /**
     * 
     * @param filePath
     * @param replacementMap
     * @throws IOException
     */
    public static void replacePlaceHolder(final String filePath, final Map<String, String> replacementMap) throws IOException {
        String fileContent = Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
        String modifiedContent = StringUtils.replaceEach(fileContent, Iterables.toArray(replacementMap.keySet(), String.class), Iterables.toArray(replacementMap.values(), String.class));

        try(FileWriter fw = new FileWriter(filePath, StandardCharsets.UTF_8)){
            fw.write(modifiedContent);
        }
    }
}
