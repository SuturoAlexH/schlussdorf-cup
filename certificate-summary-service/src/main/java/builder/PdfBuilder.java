package builder;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import constants.StyleConstants;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

public class PdfBuilder {

    private String targetFilePath;

   private String title;

   private String[] tableHeader;

   private Integer[] tableAlignment;

   private float[] tableColumnWidths;

   private String[][] tableContent;

   private boolean alternateTableRowColor;

    public PdfBuilder targetFilePath(final String targetFilePath){
        this.targetFilePath = targetFilePath;
        return this;
    }

    public PdfBuilder title(final String title){
        this.title = title;
        return this;
    }

    public PdfBuilder tableHeader(final String[] tableHeader){
        this.tableHeader = tableHeader;
        return this;
    }

    public PdfBuilder tableAlignment(final Integer[] tableAlignment){
        this.tableAlignment = tableAlignment;
        return this;
    }

    public PdfBuilder tableColumnWidths(final float[] tableColumnWidths){
        this.tableColumnWidths = tableColumnWidths;
        return this;
    }

    public PdfBuilder tableContent(final String[][] tableContent){
        this.tableContent = tableContent;
        return this;
    }

    public PdfBuilder alternateTableRowColor(final boolean alternateTableRowColor){
        this.alternateTableRowColor = alternateTableRowColor;
        return this;
    }

    public void build(){
        try{
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(targetFilePath));
            document.open();

            // header
            final Paragraph header = new Paragraph(title, StyleConstants.TITLE_FONT);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.add(Chunk.NEWLINE );

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
            } catch(IOException |DocumentException e){

            }
    }

    private static void addTableCell(final PdfPTable table, final String text, final int alignment, final boolean isBackgroundGray){
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setHorizontalAlignment(alignment);
        cell.setBackgroundColor(isBackgroundGray ? BaseColor.LIGHT_GRAY : BaseColor.WHITE);
        table.addCell(cell);
    }
}