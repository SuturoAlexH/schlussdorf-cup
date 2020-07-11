package com.schlussdorf.ui.table;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.schlussdorf.model.Result;
import com.javafxMvc.annotations.MVCModel;

/**
 * The com.schlussdorf.model for the result table.
 */
@MVCModel
public class ResultTableModel  {
    private ObjectProperty<ObservableList<Result>> resultList = new SimpleObjectProperty<>(FXCollections.observableArrayList());

    private ObjectProperty<Result> selectedResult = new SimpleObjectProperty<>();

    public ObservableList<Result> getResultList() {
        return resultList.get();
    }

    public ObjectProperty<ObservableList<Result>> resultListProperty() {
        return resultList;
    }

    public Result getSelectedResult() {
        return selectedResult.get();
    }

    public ObjectProperty<Result> selectedResultProperty() {
        return selectedResult;
    }
}
