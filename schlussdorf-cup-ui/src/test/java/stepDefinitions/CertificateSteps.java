package stepDefinitions;

import com.jPdfUnit.asserts.PdfAssert;
import exception.CsvFormatException;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.Result;
import org.apache.commons.io.FileUtils;
import org.openjfx.constants.FileConstants;
import org.openjfx.constants.FolderConstants;
import service.LoadService;
import util.TestUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertTrue;

public class CertificateSteps extends BaseApplicationTest {

    private static final String CERTIFICATE_FOLDER = "./urkunden";

    private LoadService loadService = new LoadService();

    @Before
    public void setUp() throws IOException {
        File certificateFolder = new File(CERTIFICATE_FOLDER);
        FileUtils.deleteDirectory(certificateFolder);
    }

    @After
    public void tearDown() throws IOException {
        File certificateFolder = new File(CERTIFICATE_FOLDER);
        FileUtils.deleteDirectory(certificateFolder);
    }

    @Given("the certificate progress dialog has passed (un)successfully")
    public void theCertificateProgressDialogHasPassedSuccessfully() {
        File certificateFolder = new File(CERTIFICATE_FOLDER);
        certificateFolder.mkdir();

        TestUtil.setClipBoardContent(certificateFolder.getAbsolutePath());

        Button certificateButton = lookup("#certificateButton").query();
        clickOn(certificateButton)
                .press(KeyCode.CONTROL, KeyCode.V).release(KeyCode.V, KeyCode.CONTROL)
                .press(KeyCode.ENTER).release(KeyCode.ENTER)
                .press(KeyCode.ENTER);

        await().atMost(60, TimeUnit.SECONDS).until(() -> {
            Stage certificateProgressDialog = getTopModalStage();
            Node node = certificateProgressDialog.getScene().getRoot();

            return !"certificate progress dialog".equals(node.getId());
        });

    }

    @Then("the certificate folder exists")
    public void theCertificateFolderExists() {
        File innerCertificateFolder = new File(CERTIFICATE_FOLDER + "/urkunden");
        assertTrue(innerCertificateFolder.exists());
    }

    @Then("a certificate for {string} has bean created")
    public void aCertificateForHasBeanCreated(String fireDepartment) {
        TableView<Result> resultTable = lookup("#table").query();
        Optional<Result> resultOptional = resultTable.getItems().stream().filter(result -> result.getFireDepartment().equals(fireDepartment)).findFirst();

        resultOptional.ifPresent(result -> {
            File certificate = new File(CertificateSteps.class.getResource("/referencePdfs/" + fireDepartment + ".pdf").getFile());
            PdfAssert.assertThat(new File(CERTIFICATE_FOLDER + "/urkunden/" + result.getPlace() +"_" + result.getFireDepartment() + "/" + fireDepartment + ".pdf")).hasSameAppearanceAs(certificate);
        });
    }

    @Then("the certificate summary has bean created")
    public void theCertificateSummaryHasBeanCreated() {
        File summary = new File(CertificateSteps.class.getResource("/referencePdfs/zusammenfassung.pdf").getFile());
        PdfAssert.assertThat(new File(CERTIFICATE_FOLDER + "/urkunden/zusammenfassung.pdf")).hasSameAppearanceAs(summary);
    }

    @Then("the certificates pdf has bean created")
    public void theCertificatesPdfHasBeanCreated() {
        File summary = new File(CertificateSteps.class.getResource("/referencePdfs/urkunden.pdf").getFile());
        PdfAssert.assertThat(new File(CERTIFICATE_FOLDER + "/urkunden/urkunden.pdf")).hasSameAppearanceAs(summary);
    }
}
