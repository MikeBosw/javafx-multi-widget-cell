/**
 *
 * @author Michael Anselm Bosworth
 * Due to a perverse and unpleasing character, Michael has not commented this class.
 */
package com.bosworth.javafx.control;

import javafx.event.EventHandler;
import javafx.scene.control.Cell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

public class TextFieldCellHelper<T> implements CellWidgetHelper<TextField, T> {

    private final TextField textField = new TextField();
    private final StringConverter<T> converter;
    private boolean initialized;
    private Cell<T> currentCell;
    private final WidgetActivationListener<TextField, T> listener;

    /** convenience factory method for TextFields containing Strings **/
    public static TextFieldCellHelper<String> create() {
        return create(new WidgetActivationAdapter<TextField, String>());
    }

    /** convenience factory method for TextFields containing Strings **/
    public static TextFieldCellHelper<String> create(WidgetActivationListener<TextField, String> listener) {
        return new TextFieldCellHelper<>(new DefaultStringConverter(), listener);
    }

    public TextFieldCellHelper(StringConverter<T> converter, WidgetActivationListener<TextField, T> listener) {
        this.converter = converter;
        this.listener = listener;
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

    private Cell<T> getCurrentCell() {
        return currentCell;
    }

    @Override
    public TextField getWidget(final Cell<T> cell) {
        currentCell = cell;
        if (!initialized) {
            initialize();
        }
        listener.activate(textField, cell);
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
}
