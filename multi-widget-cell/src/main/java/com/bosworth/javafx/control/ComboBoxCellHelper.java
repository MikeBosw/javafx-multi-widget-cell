/**
 *
 * @author Michael Anselm Bosworth
 * Due to a perverse and unpleasing character, Michael has not commented this class.
 */
package com.bosworth.javafx.control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Cell;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

public class ComboBoxCellHelper<C extends Cell<T>, T> implements CellWidgetHelper<C, ComboBox<T>, T> {

    private final ComboBox<T> comboBox;
    private boolean initialized;
    private Cell<T> currentCell;
    private final WidgetActivationListener<C, ComboBox<T>, T> listener;

    /** convenience factory method for ComboBoxes containing Strings **/
    public static <C extends Cell<String>> ComboBoxCellHelper<C, String> create() {
        final WidgetActivationListener<C, ComboBox<String>, String> adapter = new WidgetActivationAdapter<>();
        return create(adapter);
    }

    /** convenience factory method for ComboBoxes containing Strings **/
    public static <C extends Cell<String>> ComboBoxCellHelper<C, String> create(WidgetActivationListener<C, ComboBox<String>, String> listener) {
        return new ComboBoxCellHelper<>(new DefaultStringConverter(), listener);
    }

    public ComboBoxCellHelper(StringConverter<T> converter, WidgetActivationListener<C, ComboBox<T>, T> listener) {
        this.listener = listener;
        comboBox = new ComboBox<>();
        comboBox.setConverter(converter);
        comboBox.setMaxWidth(Double.MAX_VALUE);
    }

    private void initialize() {
        comboBox.addEventFilter(KeyEvent.ANY, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                final Cell<T> cell = getCurrentCell();
                if (t.getCode() == KeyCode.ENTER) {
                    final StringConverter<T> sc = comboBox.getConverter();
                    final String text = comboBox.getEditor().getText();
                    final T value = sc.fromString(text);
                    cell.commitEdit(value);
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cell.cancelEdit();
                }
            }
        });
        comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<T>() {
            @Override
            public void changed(ObservableValue<? extends T> ov, T oldValue, T newValue) {
                Cell<T> cell = getCurrentCell();
                if (cell != null && cell.isEditing()) {
                    cell.commitEdit(newValue);
                }
            }
        });
        initialized = true;
    }

    private Cell<T> getCurrentCell() {
        return currentCell;
    }

    @Override
    public ComboBox<T> getWidget(C cell) {
        currentCell = cell;
        if (!initialized) {
            initialize();
        }
        return comboBox;
    }

    @Override
    public void onStartEdit(C cell, T item) {
        comboBox.setEditable(true);
        comboBox.getSelectionModel().select(item);
        listener.activate(comboBox, cell);
    }

    @Override
    public void onUpdate(C cell, T item) {
        comboBox.getSelectionModel().select(item);
    }

    public static interface ActivationListener<C extends Cell<T>, T> extends WidgetActivationListener<C, ComboBox<T>, T> {}
}
