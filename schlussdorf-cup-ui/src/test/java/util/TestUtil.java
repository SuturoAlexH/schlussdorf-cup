package util;

import org.apache.commons.io.FileUtils;
import org.openjfx.constants.Folders;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;

public class TestUtil {

    public static void deleteSaveFile(){
        File saveFile = new File(Folders.SAVE_FOLDER);
        saveFile.delete();
    }

    public static void deleteImageFolder() throws IOException {
        File imagesFolder = new File(Folders.IMAGE_FOLDER);
        if(imagesFolder.exists()){
            FileUtils.deleteDirectory(imagesFolder);
        }

    }

    public static void setClipBoardContent(final String clipBoardContent){
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(clipBoardContent);
        clipboard.setContents(stringSelection, stringSelection);
    }

    public static void loadTestSetup0() throws IOException {
        File testSetupSaveFile = new File(TestUtil.class.getResource("/test_setup_0/save.csv").getFile());
        FileUtils.copyFile(testSetupSaveFile, new File(Folders.SAVE_FOLDER));
    }

    public static void loadTestSetup1() throws IOException {
        File testSetupImageFile = new File(TestUtil.class.getResource("/test_setup_1/746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg").getFile());
        File testSetupSaveFile = new File(TestUtil.class.getResource("/test_setup_1/save.csv").getFile());

        FileUtils.copyFile(testSetupImageFile, new File(Folders.IMAGE_FOLDER + "746d5498-e21d-4fd3-a4a9-3221d80610ce.jpeg"));
        FileUtils.copyFile(testSetupSaveFile, new File(Folders.SAVE_FOLDER));
    }

    public static void loadTestSetup2() throws IOException {
        File testSetupImageFile1 = new File(TestUtil.class.getResource("/test_setup_2/8990cd5b-78e4-414c-b12c-8fa2879388fe.jpeg").getFile());
        File testSetupImageFile2 = new File(TestUtil.class.getResource("/test_setup_2/61571ea3-2741-4b2d-8930-34bfcdb68b54.jpeg").getFile());
        File testSetupSaveFile = new File(TestUtil.class.getResource("/test_setup_2/save.csv").getFile());

        FileUtils.copyFile(testSetupImageFile1, new File(Folders.IMAGE_FOLDER + "8990cd5b-78e4-414c-b12c-8fa2879388fe.jpeg"));
        FileUtils.copyFile(testSetupImageFile2, new File(Folders.IMAGE_FOLDER + "61571ea3-2741-4b2d-8930-34bfcdb68b54.jpeg"));
        FileUtils.copyFile(testSetupSaveFile, new File(Folders.SAVE_FOLDER));
    }
}
