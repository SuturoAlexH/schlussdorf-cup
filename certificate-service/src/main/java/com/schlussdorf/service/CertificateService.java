package com.schlussdorf.service;

import com.schlussdorf.constants.CertificateTemplatePathConstants;
import com.schlussdorf.mapper.CertificateReplacementMapper;
import com.schlussdorf.model.Result;
import org.apache.commons.io.FileUtils;
import org.zeroturnaround.zip.ZipUtil;
import com.schlussdorf.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * This is a com.schlussdorf.service that creates the certificate docx and the certificate pdf.
 */
public class CertificateService {

    private DocxService docxService = new DocxService();

    private CertificateReplacementMapper certificateReplacementMapper = new CertificateReplacementMapper();

    /**
     * Creates the certificate pdf and certificate docx for the provided result at the specified paths.
     *
     * @param result the result for which the certificate is created
     * @param docxFile the docx file
     * @param pdfFile the pdf file
     * @param date the date of the certificate
     * @param year the year of the certificate
     * @throws IOException if something went wrong while creating the documents
     */
    public void createDocuments(final Result result, final File docxFile, final File pdfFile, final String date, final String year) throws IOException {
        //copy template folder to temp folder
        File templateFolder = new File(CertificateTemplatePathConstants.TEMPLATE_FOLDER);
        File tempFolder = new File(CertificateTemplatePathConstants.TEMP_FOLDER);
        FileUtils.copyDirectory(templateFolder, tempFolder);

        //replace placeholders in document.xml
        Map<String, String> replacementMap = certificateReplacementMapper.toReplacementMap(result, date, year);
        FileUtil.replacePlaceHolder(CertificateTemplatePathConstants.DOCUMENT_XML, replacementMap);

        //copy image to template
        File templateImage = new File(CertificateTemplatePathConstants.TEMPLATE_IMAGE);
        FileUtils.copyFile(result.getImage(), templateImage);

        //zip temp folder
        ZipUtil.pack(tempFolder, docxFile);

        //convert doccx file to pdf
        docxService.toPdf(docxFile, pdfFile);

        //clear temp folder
        FileUtils.cleanDirectory(tempFolder);
    }
}
