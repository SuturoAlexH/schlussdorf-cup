package util;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;

import java.io.*;

public class DocxUtil {

    public static void docxFileToPdf(final String docxFilePath, final String pdfPath) throws IOException {
        FileInputStream docxFileInputStream = new FileInputStream(docxFilePath);

        IConverter converter = LocalConverter.builder().build();
        OutputStream outputStream = new FileOutputStream(new File(pdfPath));
        converter.convert(docxFileInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();
        outputStream.close();
    }
}
