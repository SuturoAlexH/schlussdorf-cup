package org.openjfx.ui.table;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Result;
import com.javafxMvc.annotations.Inject;
import com.javafxMvc.annotations.MVCView;

/**
 * The view for the result table.
 */
@MVCView("/fxml/table.fxml")
public class ResultTableView{

    @Inject
    private ResultTableController controller;

    @FXML
    TableView<Result> table;

    @FXML
    private TableColumn<Result, String> placeColumn;

    @FXML
    private TableColumn<Result, String> fireDepartmentColumn;

    @FXML
    private TableColumn<Result, String> timeColumn;

    @FXML
    private TableColumn<Result, String> mistakePointsColumn;

    @FXML
    private TableColumn<Result, String> finalScoreColumn;

    @FXML
    public void initialize() {
        initializeCellFactories();
        initializeSelectionListener();

        addKeyListener();
    }

    private void initializeCellFactories(){
        placeColumn.setCellValueFactory(new PropertyValueFactory<>("place"));
        fireDepartmentColumn.setCellValueFactory(new PropertyValueFactory<>("fireDepartment"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        mistakePointsColumn.setCellValueFactory(new PropertyValueFactory<>("mistakePoints"));
        finalScoreColumn.setCellValueFactory(new PropertyValueFactory<>("finalScore"));
    }

    private void initializeSelectionListener(){
        table.setRowFactory(tableView2 -> {
            final TableRow<Result> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                final int index = row.getIndex();
                if (table.getSelectionModel().isSelected(index)) {
                    table.getSelectionModel().clearSelection();
                    event.consume();
                }
            });

            return row;
        });
    }

    private void addKeyListener(){
        table.setOnKeyPressed(keyEvent -> {
            final Result selectedResult = table.getSelectionModel().getSelectedItem();
            if (selectedResult != null && keyEvent.getCode() == KeyCode.DELETE) {
                controller.deleteResult();
            }
        });
    }
}