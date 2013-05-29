package com.bosworth.javafx.sample;

import com.bosworth.javafx.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TableView<FooBar> _fooBarTableView;
    @FXML
    private TableColumn<FooBar, String> _fooColumn;
    @FXML
    private TableColumn<FooBar, String> _barColumn;

    private CellWidgetDecider<MultiWidgetTableCell<FooBar, String>, String> createDecider() {
        //1. the TextFieldCellHelper will be used when we want a TextField widget in the cell
        final TextFieldCellHelper<MultiWidgetTableCell<FooBar, String>, String> h1 = TextFieldCellHelper.create();

        //2. the ComboBoxCellHelper will be used when we want a ComboBox widget in the cell
        final ComboBoxCellHelper<MultiWidgetTableCell<FooBar, String>, String> h2 = ComboBoxCellHelper.create(
                /** optionally pass an activation listener in here to get a callback when the ComboBox is displayed **/
        );

        //3. create the decider to make the call on which widget to use
        final MultiWidgetTableCell.Decider<FooBar, String> d = new MultiWidgetTableCell.Decider<FooBar, String>() {
            @Override
            public void decide(MultiWidgetTableCell<FooBar, String> cell) {
                final String value = cell.getItem();
                if (value == null || value.isEmpty()) {
                    cell.use(h1);
                    return;
                }
                if (value.toUpperCase().charAt(0) >= 'N') {
                    cell.use(h1);
                } else {
                    cell.use(h2);
                }
            }
        };

        return d;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        _fooColumn.setCellValueFactory(new PropertyValueFactory<FooBar, String>("foo"));
        _barColumn.setCellValueFactory(new PropertyValueFactory<FooBar, String>("bar"));

        _barColumn.setCellFactory(new Callback<TableColumn<FooBar, String>, TableCell<FooBar, String>>() {
            @Override
            public TableCell<FooBar, String> call(TableColumn<FooBar, String> col) {
                return new MultiWidgetTableCell<>(createDecider());
            }
        });

        final ObservableList<FooBar> list = FXCollections.observableArrayList();
        list.add(new FooBar("foo", "bar"));
        list.add(new FooBar("bar", "foo"));
        _fooBarTableView.setItems(list);
    }

    public class FooBar {
        private final StringProperty foo, bar;

        @FXML
        public StringProperty fooProperty() {
            return foo;
        }

        @FXML
        public StringProperty barProperty() {
            return bar;
        }

        private FooBar(String foo, String bar) {
            this.foo = new SimpleStringProperty(foo);
            this.bar = new SimpleStringProperty(bar);
        }
    }


}
