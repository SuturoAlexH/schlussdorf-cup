package constants;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;

/**
 * Constants for the fixed style of the pdf
 */
public class StyleConstants {
    public static Font TITLE_FONT = FontFactory.getFont("Times-Roman", 30);

    public static float[] TABLE_COLUMN_WIDTHS =  {0.1f, 0.7f, 0.2f};

    public static Integer[] TABLE_ALIGNMENT = new Integer[]{Element.ALIGN_CENTER, Element.ALIGN_LEFT, Element.ALIGN_CENTER};
}
