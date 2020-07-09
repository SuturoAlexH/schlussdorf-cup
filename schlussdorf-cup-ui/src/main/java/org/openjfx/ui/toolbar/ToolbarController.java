package org.openjfx.ui.toolbar;

import com.itextpdf.text.DocumentException;
import com.javafxMvc.l10n.L10n;
import com.javafxMvc.dialog.ProgressDialogView;
import javafx.application.Platform;
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
import org.openjfx.components.ErrorDialog;
import org.openjfx.components.ImageDialog;
import org.openjfx.components.InformationDialog;
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
    private ToolbarView view;

    @Inject
    private ResultTableModel resultTableModel;

    @Inject
    private ResultDialogController resultDialogController;

    @Inject
    private ResultTableController resultTableController;

    private final CertificateService certificateService = new CertificateService();

    private final CertificateSummaryService certificateSummaryService = new CertificateSummaryService();

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

        if(!resultTableModel.getSelectedResult().getImage().exists()){
            LOGGER.info("cant't show result dialog for existing result: {}, because the image file doesn't exists", resultTableModel.getSelectedResult());

            ErrorDialog errorDialog = new ErrorDialog("edit result error dialog");
            String errorText = L10n.getInstance().get("dialog.image_error_dialog", resultTableModel.getSelectedResult().getFireDepartment());
            errorDialog.show(L10n.getInstance().get("error_occured"), errorText);
        }else{
            resultDialogController.show(resultTableModel.getSelectedResult());
        }

    }

    void deleteResult(){
        LOGGER.info("tries to delete result: {}", resultTableModel.getSelectedResult());
        resultTableController.deleteResult();
    }

    void showImage(){
        LOGGER.info("show image for result: {}", resultTableModel.getSelectedResult());

        try (InputStream imageInputStream = new FileInputStream(resultTableModel.getSelectedResult().getImage())){
            ImageDialog imageDialog = new ImageDialog("show image dialog");
            imageDialog.show(new Image(imageInputStream));
        } catch (IOException e) {
           LOGGER.error(e.getMessage());

           ErrorDialog errorDialog = new ErrorDialog("show image error dialog");
           String errorText = L10n.getInstance().get("dialog.image_error_dialog", resultTableModel.getSelectedResult().getFireDepartment());
           errorDialog.show(L10n.getInstance().get("error_occured"), errorText);
        }
    }

    void createCertificates(){
        LOGGER.info("create certificates");

        DirectoryChooser chooser = new DirectoryChooser();
        File folder = chooser.showDialog(view.root.getScene().getWindow());

        if(folder != null){
            LOGGER.info("selected folder: {}", folder.getAbsolutePath());
            Task<Result> certificateTask = createCertificatesTask(folder);

            certificateTask.setOnSucceeded(event -> Platform.runLater(() -> {
                LOGGER.info("certificates are successfully created at {}", folder.getAbsolutePath());

                InformationDialog informationDialog = new InformationDialog("certificate success dialog");
                String dialogText = L10n.getInstance().get("toolbar.certificates_success_text",folder.getAbsolutePath());
                informationDialog.show(L10n.getInstance().get("toolbar.certificates_success_header"), dialogText);
            }));


            certificateTask.setOnFailed(event -> Platform.runLater(() -> {
                LOGGER.error("Cant create certificate for {}", event.getSource().getValue());
                LOGGER.error(event.getSource().getException().getMessage());

                ErrorDialog errorDialog = new ErrorDialog("certificate fail dialog");
                Result result = (Result)event.getSource().getValue();
                errorDialog.show(L10n.getInstance().get("error_occured"), L10n.getInstance().get("progress.create_error", result.getFireDepartment()));
            }));

            ProgressDialogView progressDialog = new ProgressDialogView();
            progressDialog.show(L10n.getInstance().get("progress.header"), certificateTask, "certificate progress dialog");
        }
    }

    private Task<Result> createCertificatesTask(final File folder){
        return new Task<>() {
            @Override
            protected Result call() throws IOException, DocumentException {
                //estimate max progress steps
                final int maxProgressSteps = resultTableModel.getResultList().size() +2;

                List<File> certificatePdfFileList = new ArrayList<>();

                //create certificate folder
                String certificateFolderPath = folder.getAbsolutePath() + FolderConstants.CERTIFICATE_FOLDER;
                FileUtils.forceMkdir(new File(certificateFolderPath));

                updateProgress(1, maxProgressSteps);
                for (int i = 0; i < resultTableModel.getResultList().size(); i++) {
                    Result currentResult = resultTableModel.getResultList().get(i);
                    updateValue(currentResult);
                    LOGGER.info("try to create certificate for {}", currentResult);

                    //update message
                    updateMessage(L10n.getInstance().get("progress.create_certificate_for", currentResult.getFireDepartment()));

                    //create result folder
                    String currentResultFolderPath = certificateFolderPath + currentResult.getPlace() + "_" + currentResult.getFireDepartment() + File.separator;
                    FileUtils.forceMkdir(new File(currentResultFolderPath));

                    //create documents
                    File doxcFile = new File(currentResultFolderPath + currentResult.getFireDepartment() + ".docx");
                    File pdfFile = new File(currentResultFolderPath + currentResult.getFireDepartment() + ".pdf");
                    certificateService.createDocuments(currentResult, doxcFile, pdfFile, DateUtil.getCurrentDateAsString(), DateUtil.getCurrentYearAsString());
                    certificatePdfFileList.add(pdfFile);

                    //update progress
                    updateProgress(i + 2, maxProgressSteps);//TODO index constants
                }

                //create certificate PDF
                updateMessage(L10n.getInstance().get("progress.create_certificate_pdf"));
                PDFMergerUtility pdfMerger = new PDFMergerUtility();
                pdfMerger.setDestinationFileName(certificateFolderPath + "/" + FileConstants.CERTIFICATE_PDF);
                certificatePdfFileList.forEach(certificatePdfFile -> {
                    try {
                        pdfMerger.addSource(certificatePdfFile);
                    } catch (FileNotFoundException e) {
                        LOGGER.error(e.getMessage());
                    }
                });
                pdfMerger.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
                updateProgress(resultTableModel.getResultList().size()+1, maxProgressSteps);

                //create summary PDF
                updateMessage(L10n.getInstance().get("progress.create_certificate_summary"));
                String certificateSummaryFilePath = certificateFolderPath + "/" + FileConstants.CERTIFICATE_SUMMARY_PDF;
                certificateSummaryService.createDocument(resultTableModel.getResultList(), certificateSummaryFilePath, DateUtil.getCurrentYearAsString());
                updateProgress(resultTableModel.getResultList().size()+2, maxProgressSteps);

                return null;
            }
        };
    }
}