package util;

import com.google.common.io.Files;
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

    public static void deleteSaveFile(){
        File saveFile = new File(FolderConstants.SAVE_FOLDER);
        saveFile.delete();
    }

    public static void deleteImageFolder() {
        File imagesFolder = new File(FolderConstants.IMAGE_FOLDER);
        if(imagesFolder.exists()){
            imagesFolder.delete();
        }

    }

    public static void setClipBoardContent(final String clipBoardContent){
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(clipBoardContent);
        clipboard.setContents(stringSelection, stringSelection);
    }

    public static void loadTestSetup0() throws IOException {
        File testSetupSaveFile = new File(TestUtil.class.getResource("/test_setup_0/save.csv").getFile());

        createFolderIfNotExits(FolderConstants.SAVE_FOLDER);
        Files.copy(testSetupSaveFile, new File(FolderConstants.SAVE_FOLDER + FileConstants.SAVE_FILE));
    }

    public static void loadTestSetup1() throws IOException {
        File testSetupImageFile = new File(TestUtil.class.getResource("/test_setup_1/746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg").getFile());
        File testSetupSaveFile = new File(TestUtil.class.getResource("/test_setup_1/save.csv").getFile());

        createFolderIfNotExits(FolderConstants.IMAGE_FOLDER);
        Files.copy(testSetupImageFile, new File(FolderConstants.IMAGE_FOLDER + "746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg"));

        createFolderIfNotExits(FolderConstants.SAVE_FOLDER);
        Files.copy(testSetupSaveFile, new File(FolderConstants.SAVE_FOLDER + FileConstants.SAVE_FILE));
    }

    public static void loadTestSetup2() throws IOException {
        File testSetupImageFile1 = new File(TestUtil.class.getResource("/test_setup_2/8990cd5b-78e4-414c-b12c-8fa2879388fe.jpeg").getFile());
        File testSetupImageFile2 = new File(TestUtil.class.getResource("/test_setup_2/61571ea3-2741-4b2d-8930-34bfcdb68b54.jpeg").getFile());
        File testSetupSaveFile = new File(TestUtil.class.getResource("/test_setup_2/save.csv").getFile());

        createFolderIfNotExits(FolderConstants.IMAGE_FOLDER);
        Files.copy(testSetupImageFile1, new File(FolderConstants.IMAGE_FOLDER + "8990cd5b-78e4-414c-b12c-8fa2879388fe.jpeg"));
        Files.copy(testSetupImageFile2, new File(FolderConstants.IMAGE_FOLDER + "61571ea3-2741-4b2d-8930-34bfcdb68b54.jpeg"));

        createFolderIfNotExits(FolderConstants.SAVE_FOLDER);
        Files.copy(testSetupSaveFile, new File(FolderConstants.SAVE_FOLDER + FileConstants.SAVE_FILE));
    }

    private static void createFolderIfNotExits(final String folderPath){
        File saveFolder = new File(folderPath);
        if(!saveFolder.exists()){
            saveFolder.mkdir();
        }
    }
}
