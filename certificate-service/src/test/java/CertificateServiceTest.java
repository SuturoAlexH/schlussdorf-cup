import com.jPdfUnit.asserts.PdfAssert;
import model.Result;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import service.CertificateService;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.UUID;

public class CertificateServiceTest {

    private String docxFilePath = "./test.docx";

    private String pdfFilePath = "./Feuerwehr1_450.66.pdf";

    private static Result result;

    @BeforeClass
    public static void setUp(){
        try {
            URL resource = CertificateServiceTest.class.getResource("/images/test_image.jpeg");
            File image = Paths.get(resource.toURI()).toFile();
            result = new Result(UUID.randomUUID(), 1, "Feuerwehr1", 10.1, 5, 450.66, image.getAbsolutePath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown(){
        File docxFile = new File(docxFilePath);
        docxFile.delete();

        File pdfFile = new File(pdfFilePath);
        pdfFile.delete();
    }

    @Test
    public void createDocuments_pageAmount_one(){
        //arrange

        //act
        CertificateService.createDocuments(result, docxFilePath, pdfFilePath, "22.05.2020", "2020");

        //assert
        PdfAssert.assertThat(new File(pdfFilePath)).hasNumberOfPages(1);
    }

    @Test
    public void createDocuments_appearance_equalsReferenceDocument() throws URISyntaxException {
        //arrange
        URL resource = CertificateServiceTest.class.getResource("/referencePdfs/Feuerwehr1_1_450.66.pdf");
        File referencePdfFile = Paths.get(resource.toURI()).toFile();

        //act
        CertificateService.createDocuments(result, docxFilePath, pdfFilePath, "22.05.2020", "2020");

        //assert
        PdfAssert.assertThat(new File(pdfFilePath)).hasSameAppearanceAs(referencePdfFile);
    }
}
