import model.Result;
import org.junit.After;
import org.junit.Test;
import service.CertificateSummaryService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CertificateSummaryServiceTest {

    private static final String SUMMARY_FILE_PATH = "./summary";

    @After
    public void tearDown(){
        File summaryFile = new File(SUMMARY_FILE_PATH);
        summaryFile.delete();
    }

    @Test
    public void createDocument_(){
        List<Result> results = new ArrayList<>();
        results.add(new Result(UUID.randomUUID(), 1, "Feuerwehr1", 10.1, 5, 100.66, "imagePath"));
        results.add(new Result(UUID.randomUUID(), 2, "Feuerwehr2", 10.1, 5, 55.43, "imagePath"));
        results.add(new Result(UUID.randomUUID(), 3, "Feuerwehr3", 10.1, 5, 24.23, "imagePath"));

        CertificateSummaryService.createDocument(results, SUMMARY_FILE_PATH, "2020");
    }
}
