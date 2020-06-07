package service;

import builder.PdfBuilder;
import com.itextpdf.text.DocumentException;
import constants.CertificateSummaryConstants;
import mapper.ResultMapper;
import model.Result;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

public class CertificateSummaryService {

    public void createDocument(final List<Result> resultList, final String targetFilePath, final String currentYear) throws FileNotFoundException, DocumentException {
        Objects.requireNonNull(resultList, "resultList is null");
        Objects.requireNonNull(targetFilePath, "targetFilePath is null");
        Objects.requireNonNull(currentYear, "currentYear is null");

        String[][] tableContent = resultList.stream().map(ResultMapper::toCertificateSummaryData).toArray(String[][]::new);

        PdfBuilder pdfBuilder = new PdfBuilder();
        pdfBuilder.targetFilePath(targetFilePath)
                .title(CertificateSummaryConstants.TITLE_PREFIX + currentYear)
                .tableHeader(CertificateSummaryConstants.TABLE_HEADER)
                .tableColumnWidths(CertificateSummaryConstants.TABLE_COLUMN_WIDTHS)
                .tableAlignment(CertificateSummaryConstants.TABLE_ALIGNMENT)
                .tableContent(tableContent)
                .alternateTableRowColor(true)
                .build();
    }
}
