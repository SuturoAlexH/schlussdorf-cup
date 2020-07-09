package org.openjfx.ui.table;

import com.google.common.io.Files;
import com.javafxMvc.annotations.*;
import com.javafxMvc.l10n.L10n;
import exception.CsvFormatException;
import factory.ResultBuilder;
import javafx.scene.control.ButtonType;
import model.Result;
import org.openjfx.components.ErrorDialog;
import org.openjfx.components.YesOrNoDialog;
import org.openjfx.constants.FileConstants;
import org.openjfx.constants.FolderConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.LoadService;
import service.SaveService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The controller for the result table.
 */
@MVCController
public class ResultTableController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultTableController.class);

    @Inject
    private ResultTableModel model;

    @Inject
    private ResultTableView view;

    private final LoadService loadService = new LoadService();

    private final SaveService saveService = new SaveService();

    @Bind
    private void bindModelAndView() {
        view.table.itemsProperty().bindBidirectional(model.resultListProperty());

        model.selectedResultProperty().addListener((observable, oldValue, newValue) -> {
            if(view.table.getSelectionModel().getSelectedItem() != newValue) {
                LOGGER.info("model changes selection to result: {}", newValue);
                view.table.getSelectionModel().select(newValue);
            }
        });

        view.table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)  -> {
            if(model.getSelectedResult() != newValue) {
                LOGGER.info("view changes selection to result: {}", newValue);
                model.selectedResultProperty().set(newValue);
            }
        });
    }

    @PostConstruct
    private void initialize() {
        try {
            List<Result> loadedResults = loadService.load(FolderConstants.SAVE_FOLDER + FileConstants.SAVE_FILE);
            model.getResultList().addAll(loadedResults);
        } catch (IOException|CsvFormatException e){
            LOGGER.error(e.getMessage());

            ErrorDialog errorDialog = new ErrorDialog("load save file error dialog");
            errorDialog.show(L10n.getInstance().get("error_occured"), L10n.getInstance().get("table.load_error"));
        }
    }

    /**
     * Adds a new result to the table if id is null or edits an existing result if id is not null.
     *
     * @param id the uuid of the result
     * @param fireDepartment the fire department of the result
     * @param time the time of the result
     * @param mistakePoints the mistake points of the result
     * @param selectedImageFile the image of the result
     * @throws IOException if the image cant be copied
     */
    public void addResult(final UUID id, final String fireDepartment, final String time, final String mistakePoints, final File selectedImageFile) throws IOException {
        LOGGER.info("tries to add result with data: id: {}, fire department:{}, time: {}, mistake points: {}, image path: {}", id, fireDepartment, time, mistakePoints, selectedImageFile.getAbsolutePath());

        UUID uuid = id != null? id: UUID.randomUUID();

        //copy image if necessary
        File resultImageFile = new File(FolderConstants.IMAGE_FOLDER + uuid +  ".jpeg");
        if(!selectedImageFile.getCanonicalPath().equals(resultImageFile.getCanonicalPath())){
            //create image folder if not exists
            File imageFolder = new File(FolderConstants.IMAGE_FOLDER);
            if(!imageFolder.exists()){
                imageFolder.mkdir();
            }
            Files.copy(selectedImageFile, resultImageFile);
        }

        //create result
        ResultBuilder resultBuilder = new ResultBuilder();
        Result result = resultBuilder.uuid(uuid)
                .fireDepartment(fireDepartment)
                .time(time)
                .mistakePoints(mistakePoints)
                .image(resultImageFile)
                .build();

        //insert or update result
        if(model.getResultList().contains(result)){
            int index = model.getResultList().indexOf(result);
            model.getResultList().set(index, result);
        }else{
            model.getResultList().add(result);
        }

        //update place
        List<Result> sortedResultList = model.getResultList().stream().sorted().collect(Collectors.toList());
        sortedResultList.forEach(currentResult -> currentResult.setPlace(sortedResultList.indexOf(currentResult)+1));
        model.resultListProperty().get().removeAll(sortedResultList);
        model.resultListProperty().get().addAll(sortedResultList);

        //save to csv
        saveService.save(model.getResultList(), FolderConstants.SAVE_FOLDER + FileConstants.SAVE_FILE);

        //select and scroll to table row
        model.selectedResultProperty().set(result);
        view.table.refresh();
        view.table.scrollTo(result);
    }

    /**
     * Opens a yes or no dialog and asks the user if the current selected result should really be deleted.
     * If the user clicks yes the result will be deleted and if the user clicks no nothing happen.
     */
    public void deleteResult() {
        YesOrNoDialog deleteDialog = new YesOrNoDialog("delete result dialog");
        String deleteDialogText = L10n.getInstance().get("toolbar.delete_fire_department", model.getSelectedResult().getFireDepartment());
        ButtonType deleteResult = deleteDialog.show(L10n.getInstance().get("toolbar.delete_header"), deleteDialogText);

        if (deleteResult == ButtonType.YES) {
            LOGGER.info("delete result: {}", model.getSelectedResult());

            //remove result image
            model.getSelectedResult().getImage().delete();

            //remove result form model
            List<Result> filteredResultList = model.getResultList().stream().filter(r -> !r.equals(model.getSelectedResult())).collect(Collectors.toList());

            //update place
            filteredResultList.forEach(currentResult -> currentResult.setPlace(filteredResultList.indexOf(currentResult) + 1));
            model.resultListProperty().get().removeAll(model.getResultList());
            model.resultListProperty().get().addAll(filteredResultList);

            //save to csv
            try {
                saveService.save(model.getResultList(), FolderConstants.SAVE_FOLDER + FileConstants.SAVE_FILE);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }

            //remove selected result from model
            model.selectedResultProperty().set(null);
            view.table.refresh();
        }
    }
}