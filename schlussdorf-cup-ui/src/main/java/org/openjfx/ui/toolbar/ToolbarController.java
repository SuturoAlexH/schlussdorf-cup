package org.openjfx.ui.toolbar;

import com.itextpdf.text.DocumentException;
import com.javafxMvc.l10n.L10n;
import com.javafxMvc.annotations.InjectL10n;
import com.javafxMvc.dialog.ProgressDialogView;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import com.javafxMvc.annotations.Bind;
import com.javafxMvc.annotations.Inject;
import com.javafxMvc.annotations.MVCController;
import javafx.stage.DirectoryChooser;
import model.Result;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.openjfx.components.ImageDialog;
import org.openjfx.constants.FileConstants;
import org.openjfx.constants.FolderConstants;
import org.openjfx.ui.resultDialog.ResultDialogController;
import org.openjfx.ui.table.ResultTableController;
import org.openjfx.ui.table.ResultTableModel;
import org.openjfx.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CertificateService;
import service.CertificateSummaryService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The controller for the toolbar.
 */
@MVCController
public class ToolbarController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ToolbarController.class);

    @Inject
    private ToolbarModel model;

    @Inject
    private ToolbarView view;

    @Inject
    private ResultTableModel resultTableModel;

    @Inject
    private ResultDialogController resultDialogController;

    @Inject
    private ResultTableController resultTableController;

    @InjectL10n
    private L10n l10n;

    private final CertificateService certificateService = new CertificateService();

    private final CertificateSummaryService certificateSummaryService = new CertificateSummaryService();

    private final ImageDialog imageDialog = new ImageDialog();

    private final ProgressDialogView progressDialog = new ProgressDialogView("Urkunden erzeugen");


    @Bind
    public void bindModelAndView() {
        view.editButton.disableProperty().bind(resultTableModel.selectedResultProperty().isNull());
        view.deleteButton.disableProperty().bind(resultTableModel.selectedResultProperty().isNull());
        view.showImageButton.disableProperty().bind(resultTableModel.selectedResultProperty().isNull());
    }

    void addNewResult(){
        LOGGER.info("show result dialog for new result");
        resultDialogController.show(null);
    }

    void editResult(){
        LOGGER.info("show result dialog for existing result: {}", resultTableModel.getSelectedResult());
        resultDialogController.show(resultTableModel.getSelectedResult());
    }

    void deleteResult(){
        LOGGER.info("tries to delete result: {}", resultTableModel.getSelectedResult());
        resultTableController.deleteResult();
    }

    void showImage(){
        LOGGER.info("show image for result: {}", resultTableModel.getSelectedResult());

        try (InputStream imageInputStream = new FileInputStream(resultTableModel.getSelectedResult().getImage())){
            Image image = new Image(imageInputStream);
            imageDialog.show(image);
        } catch (IOException e) {
           LOGGER.error(e.getMessage());
        }
    }

    void createCertificates(){
        LOGGER.info("create certificates");

        DirectoryChooser chooser = new DirectoryChooser();
        File folder = chooser.showDialog(view.root.getScene().getWindow());

        if(folder != null){
            Task certificateTask = createCertificatesTask(folder);
            progressDialog.show(certificateTask);
        }
    }

    private Task createCertificatesTask(final File folder){
        return new Task() {
            @Override
            protected Object call() {
                try {
                    //estimate max progress steps
                    final int maxProgressSteps = resultTableModel.getResultList().size() +2;

                    List<File> certificatePdfFileList = new ArrayList<>();

                    //create certificate folder
                    String certificateFolderPath = folder.getAbsolutePath() + FolderConstants.CERTIFICATE_FOLDER;
                    FileUtils.forceMkdir(new File(certificateFolderPath));

                    updateProgress(1, maxProgressSteps);
                    for (int i = 0; i < resultTableModel.getResultList().size(); i++) {
                        Result currentResult = resultTableModel.getResultList().get(i);

                        //update message
                        updateMessage(l10n.get("progress.create_certificate_for", currentResult.getFireDepartment()));

                        //create result folder
                        String currentResultFolderPath = certificateFolderPath + currentResult.getPlace() + "_" + currentResult.getFireDepartment() + File.separator;
                        FileUtils.forceMkdir(new File(currentResultFolderPath));

                        //create documents
                        File doxcFile = new File(currentResultFolderPath + currentResult.getFireDepartment() + ".docx");
                        File pdfFile = new File(currentResultFolderPath + currentResult.getFireDepartment() + ".pdf");
                        certificateService.createDocuments(currentResult, doxcFile, pdfFile, DateUtil.getCurrentDateAsString(), DateUtil.getCurrentYearAsString());
                        certificatePdfFileList.add(pdfFile);

                        //update progress
                        updateProgress(i + 2, maxProgressSteps);
                    }

                    //create certificate PDF
                    updateMessage(l10n.get("progress.create_certificate_pdf"));
                    PDFMergerUtility pdfMerger = new PDFMergerUtility();
                    pdfMerger.setDestinationFileName(certificateFolderPath + "/" + FileConstants.CERTIFICATE_PDF);
                    certificatePdfFileList.forEach(certificatePdfFile -> {
                        try {
                            pdfMerger.addSource(certificatePdfFile);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    });
                    pdfMerger.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
                    updateProgress(resultTableModel.getResultList().size()+1, maxProgressSteps);

                    //create summary PDF
                    updateMessage(l10n.get("progress.create_certificate_summary"));
                    String certificateSummaryFilePath = certificateFolderPath + "/" + FileConstants.CERTIFICATE_SUMMARY_PDF;
                    certificateSummaryService.createDocument(resultTableModel.getResultList(), certificateSummaryFilePath, DateUtil.getCurrentYearAsString());
                    updateProgress(resultTableModel.getResultList().size()+2, maxProgressSteps);
                }catch (IOException | DocumentException e){
                    LOGGER.error(e.getMessage());
                }

                return true;
            }
        };
    }
}