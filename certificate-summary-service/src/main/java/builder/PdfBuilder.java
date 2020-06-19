package builder;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import constants.StyleConstants;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.stream.IntStream;

/**
 * This builder can create a pdf at the provided file path with a title and a table.
 */
public class PdfBuilder {

    private String targetFilePath;

   private String title;

   private String[] tableHeader;

   private Integer[] tableAlignment;

   private float[] tableColumnWidths;

   private String[][] tableContent;

   private boolean alternateTableRowColor;

    /**
     * Sets the file path where the pdf should be created. If the path
     *  doesn't point to a pdf file .pdf is append to the path.
     *
     * @param targetFilePath the pdf file path
     * @return the builder
     */
    public PdfBuilder targetFilePath(final String targetFilePath){
        if(targetFilePath.endsWith(".pdf")){
            this.targetFilePath = targetFilePath;
        }else{
            this.targetFilePath = targetFilePath + ".pdf";
        }

        return this;
    }

    /**
     * Sets the title of the pdf.
     *
     * @param title the title
     * @return the builder
     */
    public PdfBuilder title(final String title){
        this.title = title;
        return this;
    }

    /**
     * Sets the table header.
     *
     * @param tableHeader the table header
     * @return the builder
     */
    public PdfBuilder tableHeader(final String[] tableHeader){
        this.tableHeader = tableHeader;
        return this;
    }

    /**
     * Sets the alignment of the table columns.
     *
     * @param tableAlignment an array of alignment constants
     * @return the builder
     */
    public PdfBuilder tableAlignment(final Integer[] tableAlignment){
        this.tableAlignment = tableAlignment;
        return this;
    }

    /**
     * Sets the widths of the table columns.
     *
     * @param tableColumnWidths an array with floats that describes the with of the table columns
     * @return the builder
     */
    public PdfBuilder tableColumnWidths(final float[] tableColumnWidths){
        this.tableColumnWidths = tableColumnWidths;
        return this;
    }

    /**
     * Sets the content of the table.
     *
     * @param tableContent a 2d array that describes the content of the table
     * @return the builder
     */
    public PdfBuilder tableContent(final String[][] tableContent){
        this.tableContent = tableContent;
        return this;
    }

    /**
     * Sets if the table row color should alternate between white and gray.
     *
     * @param alternateTableRowColor if true, then the row alternate. Otherwise all are white.
     * @return the builder
     */
    public PdfBuilder alternateTableRowColor(final boolean alternateTableRowColor){
        this.alternateTableRowColor = alternateTableRowColor;
        return this;
    }

    /**
     *  This method creates the pdf from the provided data.
     *
     * @throws FileNotFoundException if the file path is invalid
     * @throws DocumentException if there are problem while creating the document
     */
    public void build() throws FileNotFoundException, DocumentException {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(targetFilePath));
            document.open();

            // header
            final Paragraph header = new Paragraph(title, StyleConstants.TITLE_FONT);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.add(Chunk.NEWLINE);

            //table
            PdfPTable table = new PdfPTable(tableHeader.length);
            table.setWidths(tableColumnWidths);

            //table header
            IntStream.range(0, tableHeader.length).forEach(i -> addTableCell(table, tableHeader[i], tableAlignment[i], false));

            //table content
            boolean alternate = false;
            for(int rowIndex = 0; rowIndex < tableContent.length; rowIndex++){
                for(int columnIndex = 0; columnIndex < tableContent[rowIndex].length; columnIndex++){
                    String data = tableContent[rowIndex][columnIndex];
                    addTableCell(table, data, tableAlignment[columnIndex], alternateTableRowColor && alternate);
                }
                alternate = !alternate;
            }
            document.add(table);

            document.close();
    }

    private static void addTableCell(final PdfPTable table, final String text, final int alignment, final boolean isBackgroundGray){
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setHorizontalAlignment(alignment);
        cell.setBackgroundColor(isBackgroundGray ? BaseColor.LIGHT_GRAY : BaseColor.WHITE);
        table.addCell(cell);
    }
}