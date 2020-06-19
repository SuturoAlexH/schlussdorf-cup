package service;

import builder.PdfBuilder;
import com.itextpdf.text.DocumentException;
import constants.CertificateSummaryConstants;
import constants.StyleConstants;
import mapper.ResultMapper;
import model.Result;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

/**
 * This is a service class that creates the certificate summary.
 */
public class CertificateSummaryService {

    private ResultMapper resultMapper;

    public CertificateSummaryService(){
        resultMapper = new ResultMapper();
    }

    /**
     * Creates the certificate summary pdf at the specified targetFilePath. The title contains the provided currentYear
     * and a table with three columns place, fire department and final score.
     *
     * @param resultList the result list for data extraction
     * @param targetFilePath the certificate summary pdf file path
     * @param currentYear the current year
     * @throws FileNotFoundException if the file path is invalid
     * @throws DocumentException if there are problem while creating the document
     */
    public void createDocument(final List<Result> resultList, final String targetFilePath, final String currentYear) throws FileNotFoundException, DocumentException {
        Objects.requireNonNull(resultList, "resultList is null");
        Objects.requireNonNull(targetFilePath, "targetFilePath is null");
        Objects.requireNonNull(currentYear, "currentYear is null");

        String[][] tableContent = resultList.stream().map(resultMapper::toCertificateSummaryData).toArray(String[][]::new);

        PdfBuilder pdfBuilder = new PdfBuilder();
        pdfBuilder.targetFilePath(targetFilePath)
                .title(CertificateSummaryConstants.TITLE_PREFIX + currentYear)
                .tableHeader(CertificateSummaryConstants.TABLE_HEADER)
                .tableColumnWidths(StyleConstants.TABLE_COLUMN_WIDTHS)
                .tableAlignment(StyleConstants.TABLE_ALIGNMENT)
                .tableContent(tableContent)
                .alternateTableRowColor(true)
                .build();
    }
}
