/**
 *
 * @author Michael Anselm Bosworth
 * Due to a perverse and unpleasing character, Michael has not commented this class.
 */
package com.bosworth.javafx.cell;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;

public class ComboBoxCellHelper<T> extends CellWidgetHelper<ComboBox<T>, T> {

    private final ComboBox<T> comboBox;

    public ComboBoxCellHelper() {
        comboBox = new ComboBox<>();
        comboBox.setMaxWidth(Double.MAX_VALUE);
        comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<T>() {
            @Override public void changed(ObservableValue<? extends T> ov, T oldValue, T newValue) {
                if (getCell().isEditing()) {
                    getCell().commitEdit(newValue);
                }
            }
        });
    }

    @Override
    public ComboBox<T> getWidget() {
        return comboBox;
    }

    @Override
    public void onStartEdit(T item) {
        comboBox.setEditable(true);
        comboBox.getSelectionModel().select(item);
    }

    @Override
    public void onUpdate(T item) {
        comboBox.getSelectionModel().select(item);
    }
}
