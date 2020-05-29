package org.openjfx.ui.table;

import model.Result;
import com.javafxMvc.annotations.Bind;
import com.javafxMvc.annotations.Inject;
import com.javafxMvc.annotations.MVCController;
import com.javafxMvc.annotations.PostConstruct;
import service.LoadService;
import service.SaveService;

import java.nio.file.FileSystems;
import java.util.Collections;
import java.util.List;

@MVCController
public class ResultTableController implements ResultTableActions{

    @Inject
    private ResultTableModel model;

    @Inject
    private ResultTableView view;

    @Bind
    public void bindModelAndView() {
        view.table.itemsProperty().bindBidirectional(model.resultListProperty());
        model.selectedResultProperty().bind(view.table.getSelectionModel().selectedItemProperty());
    }

    @PostConstruct
    public void initialize() {
        List<Result> loadedResults = LoadService.load("./save/save.csv");
        model.getResultList().addAll(loadedResults);
    }

    public void insertOrUpdate(final Result result){
       if(model.getResultList().contains(result)){
           int index = model.getResultList().indexOf(result);
           model.getResultList().set(index, result);
       }else{
           model.getResultList().add(result);
       }

        Collections.sort(model.getResultList());
        for(int i = 0; i <  model.getResultList().size(); i++){
            model.getResultList().get(i).setPlace(i+1);
        }

        //save to csv
        String saveFilePath = FileSystems.getDefault().getPath("").toAbsolutePath() + "/save/save.csv";
        SaveService.save(model.getResultList(), saveFilePath);
    }
}