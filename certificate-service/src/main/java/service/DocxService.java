package service;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;

import java.io.*;

public class DocxService {

    public void toPdf(final File docxFile, final File pdfFile) throws IOException {
        try(FileInputStream docxFileInputStream = new FileInputStream(docxFile); OutputStream outputStream = new FileOutputStream(pdfFile)){
            IConverter converter = LocalConverter.builder().build();
            converter.convert(docxFileInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();
        }
    }
}
