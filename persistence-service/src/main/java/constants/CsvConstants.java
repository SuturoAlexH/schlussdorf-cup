package constants;

/**
 * Constants for the csv file.
 */
public class CsvConstants {

    public static final String UUID = "uuid";
    public static final String PLACE = "place";
    public static final String FIRE_DEPARTMENT = "fire_department";
    public static final String MISTAKE_POINTS = "mistake_points";
    public static final String TIME = "time";
    public static final String FINAL_SCORE = "final_score";
    public static final String IMAGE_PATH = "image_path";

    public static final String[] HEADER = new String[]{UUID, PLACE, FIRE_DEPARTMENT, TIME, MISTAKE_POINTS, FINAL_SCORE, IMAGE_PATH};

    public static final int ROW_SIZE = HEADER.length;
}
