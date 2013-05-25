/**
 *
 * @author Michael Anselm Bosworth
 * Due to a perverse and unpleasing character, Michael has not commented this class.
 */
package com.bosworth.javafx.cell;

import javafx.event.EventHandler;
import javafx.scene.control.Cell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

public class TextFieldCellHelper<T> implements CellWidgetHelper<TextField, T> {

    private final TextField textField = new TextField();
    private final StringConverter<T> converter;
    private boolean initialized;
    private Cell<T> _currentCell;

    public TextFieldCellHelper(StringConverter<T> converter) {
        this.converter = converter;
    }

    private void initialize() {
        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    if (converter == null) {
                        throw new IllegalStateException(
                                "Attempting to convert text input into Object, but provided "
                                        + "StringConverter is null. Be sure to set a StringConverter "
                                        + "in your cell factory.");
                    }
                    getCurrentCell().commitEdit(converter.fromString(textField.getText()));
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    getCurrentCell().cancelEdit();
                }
            }
        });
        initialized = true;
    }

    @Override
    public TextField getWidget(final Cell<T> cell) {
        _currentCell = cell;
        if (!initialized) {
            initialize();
        }
        return textField;
    }

    @Override
    public void onStartEdit(Cell<T> cell, T item) {
        textField.setText(item == null ? "" : item.toString());
        textField.selectAll();
    }

    @Override
    public void onUpdate(Cell<T> cell, T item) {
        textField.setText(item == null ? "" : item.toString());
    }

    private Cell<T> getCurrentCell() {
        return _currentCell;
    }
}
