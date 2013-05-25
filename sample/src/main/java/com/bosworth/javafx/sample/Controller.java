package com.bosworth.javafx.sample;

import com.bosworth.javafx.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TableView<StringPair> _fooBarTableView;
    @FXML
    private TableColumn<StringPair, String> _fooColumn;
    @FXML
    private TableColumn<StringPair, String> _barColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        _fooColumn.setCellValueFactory(new PropertyValueFactory<StringPair, String>("aString"));
        _barColumn.setCellValueFactory(new PropertyValueFactory<StringPair, String>("bString"));
        final TextFieldCellHelper<String> helperA = TextFieldCellHelper.create();
        final CellWidgetHelper<ComboBox<String>, String> helperB = ComboBoxCellHelper.create();
        _barColumn.setCellFactory(new Callback<TableColumn<StringPair, String>, TableCell<StringPair, String>>() {
            @Override
            public TableCell<StringPair, String> call(TableColumn<StringPair, String> col) {
                CellWidgetDecider<String> decider = new CellWidgetDecider<String>() {
                    @Override
                    public void decide(String value, CellWidgetDecidable cwd) {
                        if (value == null || value.isEmpty()) {
                            cwd.use(helperA);
                            return;
                        }
                        if (value.toUpperCase().charAt(0) >= 'N') {
                            cwd.use(helperA);
                        } else {
                            cwd.use(helperB);
                        }
                    }
                };
                return new MultiWidgetTableCell<>(decider);
            }
        });

        final ObservableList<StringPair> list = FXCollections.observableArrayList();
        list.add(new StringPair("foo", "bar"));
        list.add(new StringPair("bar", "foo"));
        _fooBarTableView.setItems(list);
    }

    public class StringPair {
        private final StringProperty aString, bString;

        @FXML
        public StringProperty aStringProperty() {
            return aString;
        }

        @FXML
        public StringProperty bStringProperty() {
            return bString;
        }

        private StringPair(String aString, String bString) {
            this.aString = new SimpleStringProperty(aString);
            this.bString = new SimpleStringProperty(bString);
        }
    }

}
