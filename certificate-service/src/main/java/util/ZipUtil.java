package util;

import java.io.File;

public class ZipUtil {

     public static void zipFolder(final String folderPath, final String filePath){
        File folder = new File(folderPath);
        File file = new File(filePath);

        org.zeroturnaround.zip.ZipUtil.pack(folder, file);
    }
}
