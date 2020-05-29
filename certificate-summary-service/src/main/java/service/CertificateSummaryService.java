package service;

import builder.PdfBuilder;
import constants.CertificateSummaryConstants;
import mapper.ResultMapper;
import model.Result;

import java.util.List;

public class CertificateSummaryService {

    public static void createDocument(final List<Result> resultList, final String targetFilePath, final String currentYear){
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
