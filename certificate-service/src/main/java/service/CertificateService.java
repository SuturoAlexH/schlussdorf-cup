package service;

import constants.PathConstants;
import logger.CustomLogger;
import mapper.CertificateReplacementMapper;
import model.Result;
import util.DateUtil;
import util.ZipUtil;
import util.DocxUtil;
import util.FileUtil;

import java.io.IOException;
import java.util.Map;

public class CertificateService {

    public static void createDocuments(final Result result, final String docxFilePath, final String pdfFilePath, final String currentDate, final String currentYear){
        try {
            String workingFolder = System.getProperty("user.dir");

            FileUtil.copyFolder(workingFolder + PathConstants.TEMPLATE_FOLDER, workingFolder + PathConstants.TEMP_FOLDER);

            Map<String, String> replacementMap = CertificateReplacementMapper.toReplacementMap(result, currentDate, currentYear);

            FileUtil.replacePlaceHolder(workingFolder + PathConstants.TEMPLATE_DOCUMENT, replacementMap);
            FileUtil.copyFile(result.getImagePath(), workingFolder + PathConstants.TEMPLATE_IMAGE);

            ZipUtil.zipFolder(workingFolder + PathConstants.TEMP_FOLDER, docxFilePath);

            DocxUtil.docxFileToPdf(docxFilePath, pdfFilePath);

            FileUtil.clearFolder(workingFolder + PathConstants.TEMP_FOLDER);
        }catch(IOException e){
            e.printStackTrace();
            CustomLogger.LOGGER.info(e.getMessage());
        }
    }
}
