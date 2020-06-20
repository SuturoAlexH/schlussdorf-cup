package org.openjfx.ui.table;

import factory.ResultBuilder;
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

@MVCController
public class ResultTableController implements ResultTableActions{

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultTableController.class);

    @Inject
    private ResultTableModel model;

    @Inject
    private ResultTableView view;

    private LoadService loadService = new LoadService();

    private SaveService saveService = new SaveService();

    @Bind
    public void bindModelAndView() {
        view.table.itemsProperty().bindBidirectional(model.resultListProperty());

        model.selectedResultProperty().addListener((observable, oldValue, newValue) -> {
            if(view.table.getSelectionModel().getSelectedItem() != newValue) {
                view.table.getSelectionModel().select(newValue);
            }
        });

        view.table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)  -> {
            if(model.getSelectedResult() != newValue) {
                model.selectedResultProperty().set(newValue);
            }
        });
    }

    @PostConstruct
    public void initialize() {
        try {
            List<Result> loadedResults = loadService.load(Folders.SAVE_FOLDER);
            model.getResultList().addAll(loadedResults);
        }catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void addResult(final UUID id, final String fireDepartment, final String time, final String mistakePoints, final File selectedImageFile) throws IOException {
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
        view.table.scrollTo(result);
    }
}