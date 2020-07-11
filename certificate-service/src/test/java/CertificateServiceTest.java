import com.jPdfUnit.asserts.PdfAssert;
import com.schlussdorf.builder.ResultBuilder;
import com.schlussdorf.model.Result;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.schlussdorf.service.CertificateService;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class CertificateServiceTest {

    private static final String DOCX_FILE_PATH = "./certificate.docx";

    private static final String PDF_FILE_PATH = "./certificate.pdf";

    private static final String CURRENT_DATE = "06.06.2020";

    private static final String CURRENT_YEAR = "2020";

    private CertificateService classUnderTest;

    private File docxFile;

    private File pdfFile;

    @Before
    public void setUp(){
        classUnderTest = new CertificateService();

        docxFile = new File(DOCX_FILE_PATH);
        pdfFile = new File(PDF_FILE_PATH);
    }

    @After
    public void tearDown(){
        docxFile.delete();
        pdfFile.delete();
    }

    @Test(expected = NullPointerException.class)
    public void createDocuments_resultIsNull_nullPointerException() throws IOException {
        //arrange

        //act
        classUnderTest.createDocuments(null, docxFile, pdfFile, CURRENT_DATE, CURRENT_YEAR);

        //assert
    }

    @Test(expected = NullPointerException.class)
    public void createDocuments_docxFileIsNull_nullPointerException() throws IOException {
        //arrange
        ResultBuilder resultBuilder = new ResultBuilder();
        Result result = resultBuilder
                .fireDepartment("Feuerwehr")
                .place(1)
                .time(66.66)
                .image(new File(CertificateServiceTest.class.getResource("/images/test_image.jpeg").getFile())).build();

        //act
        classUnderTest.createDocuments(result, null, pdfFile, CURRENT_DATE, CURRENT_YEAR);

        //assert
    }

    @Test(expected = NullPointerException.class)
    public void createDocuments_pdfFileIsNull_nullPointerException() throws IOException {
        //arrange
        ResultBuilder resultBuilder = new ResultBuilder();
        Result result = resultBuilder
                .fireDepartment("Feuerwehr")
                .place(1)
                .time(66.66)
                .image(new File(CertificateServiceTest.class.getResource("/images/test_image.jpeg").getFile())).build();

        //act
        classUnderTest.createDocuments(result, docxFile, null, CURRENT_DATE, CURRENT_YEAR);

        //assert
    }

    @Test(expected = NullPointerException.class)
    public void createDocuments_currentDateIsNull_nullPointerException() throws IOException {
        //arrange
        ResultBuilder resultBuilder = new ResultBuilder();
        Result result = resultBuilder
                .fireDepartment("Feuerwehr")
                .place(1)
                .time(66.66)
                .image(new File(CertificateServiceTest.class.getResource("/images/test_image.jpeg").getFile())).build();

        //act
        classUnderTest.createDocuments(result, docxFile, pdfFile, null, CURRENT_YEAR);

        //assert
    }

    @Test(expected = NullPointerException.class)
    public void createDocuments_currentYearIsNull_nullPointerException() throws IOException {
        //arrange
        ResultBuilder resultBuilder = new ResultBuilder();
        Result result = resultBuilder
                .fireDepartment("Feuerwehr")
                .place(1)
                .time(66.66)
                .image(new File(CertificateServiceTest.class.getResource("/images/test_image.jpeg").getFile())).build();

        //act
        classUnderTest.createDocuments(result, docxFile, pdfFile, CURRENT_DATE, null);

        //assert
    }

    @Test
    public void createDocuments_normal_onePage() throws IOException {
        //arrange
        ResultBuilder resultBuilder = new ResultBuilder();
        Result result = resultBuilder
                .fireDepartment("Feuerwehr")
                .place(1)
                .time(66.66)
                .image(new File(CertificateServiceTest.class.getResource("/images/test_image.jpeg").getFile())).build();

        //act
        classUnderTest.createDocuments(result, docxFile, pdfFile, CURRENT_DATE, CURRENT_YEAR);

        //assert
        PdfAssert.assertThat(pdfFile).hasNumberOfPages(1);
    }

    @Test
    public void createDocuments_normal_appearanceIsCorrect() throws IOException, URISyntaxException {
        //arrange
        File referencePdfFile = new File(CertificateServiceTest.class.getResource("/referencePdfs/Feuerwehr_433.34_1.pdf").getFile());

        ResultBuilder resultBuilder = new ResultBuilder();
        Result result = resultBuilder
                .fireDepartment("Feuerwehr")
                .place(1)
                .time(66.66)
                .image(new File(CertificateServiceTest.class.getResource("/images/test_image.jpeg").getFile())).build();

        //act
        classUnderTest.createDocuments(result, docxFile, pdfFile, CURRENT_DATE, CURRENT_YEAR);

        //assert
        PdfAssert.assertThat(pdfFile).hasSameAppearanceAs(referencePdfFile);
    }

    @Test
    public void createDocuments_placeHasLengthTwo_appearanceIsCorrect() throws IOException, URISyntaxException {
        //arrange
        URL resource = CertificateServiceTest.class.getResource("/referencePdfs/Feuerwehr_433.34_25.pdf");
        File referencePdfFile = Paths.get(resource.toURI()).toFile();

        ResultBuilder resultBuilder = new ResultBuilder();
        Result result = resultBuilder
                .fireDepartment("Feuerwehr")
                .place(25)
                .time(66.66)
                .image(new File(CertificateServiceTest.class.getResource("/images/test_image.jpeg").getFile())).build();

        //act
        classUnderTest.createDocuments(result, docxFile, pdfFile, CURRENT_DATE, CURRENT_YEAR);

        //assert
        PdfAssert.assertThat(pdfFile).hasSameAppearanceAs(referencePdfFile);
    }

    @Test
    public void createDocuments_specialCharacters_appearanceIsCorrect() throws IOException, URISyntaxException {
        //arrange
        URL resource = CertificateServiceTest.class.getResource("/referencePdfs/äüöß12345_433.34_1.pdf");
        File referencePdfFile = Paths.get(resource.toURI()).toFile();

        ResultBuilder resultBuilder = new ResultBuilder();
        Result result = resultBuilder
                .fireDepartment("äüöß12345")
                .place(1)
                .time(66.66)
                .image(new File(CertificateServiceTest.class.getResource("/images/test_image.jpeg").getFile())).build();

        //act
        classUnderTest.createDocuments(result, docxFile, pdfFile, CURRENT_DATE, CURRENT_YEAR);

        //assert
        PdfAssert.assertThat(pdfFile).hasSameAppearanceAs(referencePdfFile);
    }
}
