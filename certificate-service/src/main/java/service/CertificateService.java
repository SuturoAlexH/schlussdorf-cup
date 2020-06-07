package service;

import constants.PathConstants;
import mapper.CertificateReplacementMapper;
import model.Result;
import org.apache.commons.io.FileUtils;
import org.zeroturnaround.zip.ZipUtil;
import util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CertificateService {

    private DocxService docxService;

    public CertificateService(){
        this.docxService = new DocxService();
    }

    public void createDocuments(final Result result, final File docxFile, final File pdfFile, final String currentDate, final String currentYear) throws IOException {
        //copy template folder to temp folder
        File templateFolder = new File(PathConstants.TEMPLATE_FOLDER);
        File tempFolder = new File(PathConstants.TEMP_FOLDER);
        FileUtils.copyDirectory(templateFolder, tempFolder);

        //replace placeholders in document.xml
        Map<String, String> replacementMap = CertificateReplacementMapper.toReplacementMap(result, currentDate, currentYear);
        FileUtil.replacePlaceHolder(PathConstants.DOCUMENT_XML, replacementMap);

        //copy image to template
        File templateImage = new File(PathConstants.TEMPLATE_IMAGE);
        FileUtils.copyFile(result.getImage(), templateImage);

        //zip temp folder
        ZipUtil.pack(tempFolder, docxFile);

        //convert doccx file to pdf
        docxService.toPdf(docxFile, pdfFile);

        //clear temp folder
        FileUtils.cleanDirectory(tempFolder);
    }
}
