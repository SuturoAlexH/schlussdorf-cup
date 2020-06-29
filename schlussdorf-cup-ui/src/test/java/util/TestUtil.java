package util;

import org.apache.commons.io.FileUtils;
import org.openjfx.constants.FileConstants;
import org.openjfx.constants.FolderConstants;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;

public class TestUtil {

    public static void clearFolders() {
        deleteSaveFile();
        deleteImageFolder();
    }

    public static void deleteSaveFile() {
        File saveFile = new File(FolderConstants.SAVE_FOLDER + FileConstants.SAVE_FILE);
        saveFile.delete();
    }

    public static void deleteImageFolder() {
        File imagesFolder = new File(FolderConstants.IMAGE_FOLDER);
        if(imagesFolder.exists()){
            try {
                FileUtils.deleteDirectory(imagesFolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void setClipBoardContent(final String clipBoardContent){
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(clipBoardContent);
        clipboard.setContents(stringSelection, stringSelection);
    }
}
