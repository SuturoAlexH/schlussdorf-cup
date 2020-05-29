package org.openjfx.ui.toolbar;

import com.javafxMvc.annotations.ProgressDialog;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import com.javafxMvc.annotations.Bind;
import com.javafxMvc.annotations.Inject;
import com.javafxMvc.annotations.MVCController;
import javafx.stage.DirectoryChooser;
import org.openjfx.components.ImageAlert;
import org.openjfx.components.YesOrNoDialog;
import org.openjfx.ui.resultDialog.ResultDialogController;
import org.openjfx.ui.table.ResultTableModel;

import java.io.File;

@MVCController
public class ToolbarController implements ToolbarActions {

    @Inject
    private ResultTableModel resultTableModel;

    @Inject
    private ToolbarView view;

    @Inject
    private ResultDialogController resultDialogController;

    private ImageAlert imageAlert = new ImageAlert();

    @Bind
    public void bindModelAndView() {
        view.editButton.disableProperty().bind(resultTableModel.selectedResultProperty().isNull());
        view.deleteButton.disableProperty().bind(resultTableModel.selectedResultProperty().isNull());
        view.imageButton.disableProperty().bind(resultTableModel.selectedResultProperty().isNull());
    }

    public void addNewResult(){
        resultDialogController.show();
    }

    public void editResult(){
        resultDialogController.show(resultTableModel.getSelectedResult());
    }

    public void deleteResult(){
        Alert alert = YesOrNoDialog.getAlert("Soll das Ergebnis für die Ortsfeuerwehr " + resultTableModel.getSelectedResult().getFireDepartment() +" wirklich gelöscht werden?");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            resultTableModel.getResultList().remove(resultTableModel.getSelectedResult());
        }
    }

    public void showImage(){
        File imageFile = new File(resultTableModel.getSelectedResult().getImagePath());
        Image image = new Image(imageFile.toURI().toString());

        imageAlert.setImageAndShow(image);
    }


    public void createCertificates(){
      //  createCertificatesTask();
    }

//    @ProgressDialog
//    private Task createCertificatesTask(){
//        DirectoryChooser chooser = new DirectoryChooser();
//        File folder = chooser.showDialog(view.root.getScene().getWindow());
//
//        return new Task() {
//            @Override
//            protected Object call() {
//                String certificateFolderPath = folderPath + Folders.CERTIFICATE_FOLDER;
//                FileUtil.createFolder(certificateFolderPath);
//
//                for (int i = 0; i < resultList.get().size(); i++) {
//                    Result currentResult = resultList.get().get(i);
//                    CustomLogger.LOGGER.info("create certificates for: " + currentResult);
//                    updateMessage("Erzeuge Urkunde für " + currentResult.getFireDepartment());
//
//                    String currentResultFolderPath = certificateFolderPath + currentResult.getPlace() + "_" + currentResult.getFireDepartment() + File.separator;
//                    FileUtil.createFolder(currentResultFolderPath);
//
//                    CertificateService.createDocuments(currentResult, currentResultFolderPath + currentResult.getFireDepartment() + ".docx",
//                            currentResultFolderPath + currentResult.getFireDepartment() + ".pdf",
//                            DateUtil.getCurrentDateAsString(),
//                            DateUtil.getCurrentYearAsString());
//                    updateProgress(i + 1, resultList.get().size());
//                }
//                return true;
//            }
//        };
//    }
}
