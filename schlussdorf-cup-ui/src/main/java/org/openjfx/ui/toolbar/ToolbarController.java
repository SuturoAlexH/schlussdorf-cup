package org.openjfx.ui.toolbar;

import com.javafxMvc.dialog.ProgressDialogView;
import javafx.concurrent.Task;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import com.javafxMvc.annotations.Bind;
import com.javafxMvc.annotations.Inject;
import com.javafxMvc.annotations.MVCController;
import javafx.stage.DirectoryChooser;
import model.Result;
import org.apache.commons.io.FileUtils;
import org.openjfx.components.ImageDialog;
import org.openjfx.components.YesOrNoDialog;
import org.openjfx.constants.Folders;
import org.openjfx.ui.resultDialog.ResultDialogController;
import org.openjfx.ui.table.ResultTableModel;
import org.openjfx.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CertificateService;

import java.io.File;
import java.io.IOException;

@MVCController
public class ToolbarController implements ToolbarActions {

    private static final Logger LOGGER = LoggerFactory.getLogger(ToolbarController.class);

    @Inject
    private ResultTableModel resultTableModel;

    @Inject
    private ToolbarView view;

    @Inject
    private ResultDialogController resultDialogController;

    private CertificateService certificateService;

    private ImageDialog imageDialog;

    private ProgressDialogView progressDialog;

    private YesOrNoDialog deleteDialog;

    public ToolbarController(){
        certificateService = new CertificateService();

        imageDialog = new ImageDialog();
        progressDialog = new ProgressDialogView("Urkunden erzeugen");
        deleteDialog = new YesOrNoDialog();
    }

    @Bind
    public void bindModelAndView() {
        view.editButton.disableProperty().bind(resultTableModel.selectedResultProperty().isNull());
        view.deleteButton.disableProperty().bind(resultTableModel.selectedResultProperty().isNull());
        view.imageButton.disableProperty().bind(resultTableModel.selectedResultProperty().isNull());
    }

    public void addNewResult(){
        LOGGER.info("show result dialog for new result");
        resultDialogController.show(null);
    }

    public void editResult(){
        LOGGER.info("show result dialog for existing result " + resultTableModel.getSelectedResult());
        resultDialogController.show(resultTableModel.getSelectedResult());
    }

    public void deleteResult(){
        ButtonType deleteResult = deleteDialog.show("Soll das Ergebnis für die Ortsfeuerwehr " + resultTableModel.getSelectedResult().getFireDepartment() +" wirklich gelöscht werden?");

        if (deleteResult == ButtonType.YES) {
            resultTableModel.getSelectedResult().getImage().delete();
            resultTableModel.getResultList().remove(resultTableModel.getSelectedResult());
            resultTableModel.selectedResultProperty().set(null);
        }
    }

    public void showImage(){
        try {
            Image image = new Image(FileUtils.openInputStream(resultTableModel.getSelectedResult().getImage()));
            imageDialog.setImageAndShow(image);
        } catch (IOException e) {
           LOGGER.error(e.getMessage());
        }
    }

    public void createCertificates(){
        DirectoryChooser chooser = new DirectoryChooser();
        File folder = chooser.showDialog(view.root.getScene().getWindow());

        Task certificateTask = createCertificatesTask(folder);
        progressDialog.show(certificateTask);
    }

    private Task createCertificatesTask(final File folder){
        return new Task() {
            @Override
            protected Object call() {
                try {
                    //create certificate folder
                    String certificateFolderPath = folder.getAbsolutePath() + Folders.CERTIFICATE_FOLDER;
                    FileUtils.forceMkdir(new File(certificateFolderPath));

                    for (int i = 0; i < resultTableModel.getResultList().size(); i++) {
                        Result currentResult = resultTableModel.getResultList().get(i);

                        //update message
                        updateMessage("Erzeuge Urkunde für " + currentResult.getFireDepartment());

                        //create result folder
                        String currentResultFolderPath = certificateFolderPath + currentResult.getPlace() + "_" + currentResult.getFireDepartment() + File.separator;
                        FileUtils.forceMkdir(new File(currentResultFolderPath));

                        //create documents
                        File doxcFile = new File(currentResultFolderPath + currentResult.getFireDepartment() + ".docx");
                        File pdfFile = new File(currentResultFolderPath + currentResult.getFireDepartment() + ".docx");
                        certificateService.createDocuments(currentResult, doxcFile, pdfFile, DateUtil.getCurrentDateAsString(), DateUtil.getCurrentYearAsString());

                        //update progress
                        updateProgress(i + 1, resultTableModel.getResultList().size());
                    }
                }catch (IOException e){
                    LOGGER.error(e.getMessage());
                }

                return true;
            }
        };
    }
}