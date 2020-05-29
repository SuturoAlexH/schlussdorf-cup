package constants;

import com.itextpdf.text.Element;
import util.DateUtil;

public class CertificateSummaryConstants {

    public static String TITLE_PREFIX = "Schlußdorf Cup ";

    public static float[] TABLE_COLUMN_WIDTHS =  {0.1f, 0.7f, 0.2f};

    public static Integer[] TABLE_ALIGNMENT = new Integer[]{Element.ALIGN_CENTER, Element.ALIGN_LEFT, Element.ALIGN_CENTER};

    public static String[] TABLE_HEADER = new String[] {"Platz", "Feuerwehr", "Gesamtpunkte"};
}
