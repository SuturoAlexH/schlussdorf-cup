package org.openjfx.ui.table;

import factory.ResultBuilder;
import javafx.collections.FXCollections;
import javafx.scene.control.ButtonType;
import model.Result;
import com.javafxMvc.annotations.Bind;
import com.javafxMvc.annotations.Inject;
import com.javafxMvc.annotations.MVCController;
import com.javafxMvc.annotations.PostConstruct;
import org.apache.commons.io.FileUtils;
import org.openjfx.constants.Folders;
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

    private LoadService loadService = new LoadService();

    private SaveService saveService = new SaveService();

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
            List<Result> loadedResults = loadService.load(Folders.SAVE_FOLDER);
            model.getResultList().addAll(loadedResults);
        }catch (IOException e) {
            LOGGER.error(e.getMessage());
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
        File resultImageFile = new File(Folders.IMAGE_FOLDER + uuid +  ".jpeg");
        if(!selectedImageFile.getCanonicalPath().equals(resultImageFile.getCanonicalPath())){
            FileUtils.copyFile(selectedImageFile, resultImageFile);
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
        saveService.save(model.getResultList(), Folders.SAVE_FOLDER);

        //select and scroll to table row
        model.selectedResultProperty().set(result);
        view.table.refresh();
        view.table.scrollTo(result);
    }

    public void deleteResult() {
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
            saveService.save(model.getResultList(), Folders.SAVE_FOLDER);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        //remove selected result from model
        model.selectedResultProperty().set(null);
        view.table.refresh();
    }
}