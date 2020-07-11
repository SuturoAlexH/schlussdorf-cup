import com.itextpdf.text.DocumentException;
import com.jPdfUnit.asserts.PdfAssert;
import com.schlussdorf.builder.ResultBuilder;
import com.schlussdorf.model.Result;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.schlussdorf.service.CertificateSummaryService;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CertificateSummaryServiceTest {

    private static final String SUMMARY_FILE_PATH = "./summary.pdf";

    private CertificateSummaryService classUnderTest;

    @Before
    public void setUp(){
        classUnderTest = new CertificateSummaryService();
    }

    @After
    public void tearDown(){
        File summaryFile = new File(SUMMARY_FILE_PATH);
        summaryFile.delete();
    }

    @Test(expected = NullPointerException.class)
    public void createDocument_resultListIsNull_NullPointerException() throws FileNotFoundException, DocumentException {
        //arrange

        //act
        classUnderTest.createDocument(null, SUMMARY_FILE_PATH, "2020");

        //assert
    }

    @Test(expected = NullPointerException.class)
    public void createDocument_targetFilePathIsNull_NullPointerException() throws FileNotFoundException, DocumentException {
        //arrange

        //act
        classUnderTest.createDocument(new ArrayList<>(), null, "2020");

        //assert
    }

    @Test(expected = NullPointerException.class)
    public void createDocument_currentYearIsNull_NullPointerException() throws FileNotFoundException, DocumentException {
        //arrange

        //act
        classUnderTest.createDocument(new ArrayList<>(), SUMMARY_FILE_PATH, null);

        //assert
    }

    @Test(expected = FileNotFoundException.class)
    public void createDocument_targetFilePathIsInvalid_fileNotFoundException() throws FileNotFoundException, DocumentException {
        //arrange

        //act
        classUnderTest.createDocument(new ArrayList<>(), "/folderDoesNotExists/notValidFilePath", "2020");

        //assert
    }

    @Test
    public void createDocument_normal_correctDocument() throws FileNotFoundException, DocumentException, URISyntaxException {
        //arrange
        URL resource = CertificateSummaryServiceTest.class.getResource("/referencePdfs/summary_three.pdf");
        File referencePdfFile = Paths.get(resource.toURI()).toFile();

        List<Result> resultList = new ArrayList<>();
        resultList.add(new ResultBuilder().fireDepartment("Feuerwehr1").place(1).time(33.33).build());
        resultList.add(new ResultBuilder().fireDepartment("Feuerwehr2").place(2).time(166.66).build());
        resultList.add(new ResultBuilder().fireDepartment("Feuerwehr3").place(3).time(299.99).build());

        //act
        classUnderTest.createDocument(resultList, SUMMARY_FILE_PATH, "2020");

        //assert
        PdfAssert.assertThat(new File(SUMMARY_FILE_PATH)).hasSameAppearanceAs(referencePdfFile);
    }

    @Test
    public void createDocument_veryLongFireDepartmentName_c() throws FileNotFoundException, DocumentException, URISyntaxException {
        //arrange
        URL resource = CertificateSummaryServiceTest.class.getResource("/referencePdfs/summary_verylong.pdf");
        File referencePdfFile = Paths.get(resource.toURI()).toFile();

        List<Result> resultList = new ArrayList<>();
        resultList.add(new ResultBuilder().fireDepartment("veryloooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong").place(1).time(33.33).build());
        resultList.add(new ResultBuilder().fireDepartment("Feuerwehr2").place(2).time(166.66).build());
        resultList.add(new ResultBuilder().fireDepartment("Feuerwehr3").place(3).time(299.99).build());

        //act
        classUnderTest.createDocument(resultList, SUMMARY_FILE_PATH, "2020");

        //assert
        PdfAssert.assertThat(new File(SUMMARY_FILE_PATH)).hasSameAppearanceAs(referencePdfFile);
    }

    @Test
    public void createDocument_moreThen38Entries_secondPage() throws FileNotFoundException, DocumentException {
        //arrange
        List<Result> resultList = Collections.nCopies(39, new ResultBuilder().fireDepartment("Feuerwehr").place(1).time(166.66).build());

        //act
        classUnderTest.createDocument(resultList, SUMMARY_FILE_PATH, "2020");

        //assert
        PdfAssert.assertThat(new File(SUMMARY_FILE_PATH)).hasNumberOfPages(2);
    }

    @Test
    public void createDocument_moreThen38Entries_correctDocument() throws FileNotFoundException, DocumentException, URISyntaxException {
        //arrange
        URL resource = CertificateSummaryServiceTest.class.getResource("/referencePdfs/summary_39entries.pdf");
        File referencePdfFile = Paths.get(resource.toURI()).toFile();

        List<Result> resultList = Collections.nCopies(39, new ResultBuilder().fireDepartment("Feuerwehr").place(1).time(166.66).build());

        //act
        classUnderTest.createDocument(resultList, SUMMARY_FILE_PATH, "2020");

        //assert
        PdfAssert.assertThat(new File(SUMMARY_FILE_PATH)).hasSameAppearanceAs(referencePdfFile);
    }

    @Test
    public void createDocument_specialCharacters_correctDocument() throws FileNotFoundException, DocumentException, URISyntaxException {
        //arrange
        URL resource = CertificateSummaryServiceTest.class.getResource("/referencePdfs/summary_speacialCharacters.pdf");
        File referencePdfFile = Paths.get(resource.toURI()).toFile();

        List<Result> resultList = new ArrayList<>();
        resultList.add(new ResultBuilder().fireDepartment("äüöß123").place(1).time(33.33).build());

        //act
        classUnderTest.createDocument(resultList, SUMMARY_FILE_PATH, "2020");

        //assert
        PdfAssert.assertThat(new File(SUMMARY_FILE_PATH)).hasSameAppearanceAs(referencePdfFile);
    }
}
