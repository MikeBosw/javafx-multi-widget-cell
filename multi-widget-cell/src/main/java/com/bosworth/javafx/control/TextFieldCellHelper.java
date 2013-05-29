/**
 *
 * @author Michael Anselm Bosworth
 * Due to a perverse and unpleasing character, Michael has not commented this class.
 */
package com.bosworth.javafx.control;

import javafx.event.EventHandler;
import javafx.scene.control.Cell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

public class TextFieldCellHelper<C extends Cell<T>, T> implements CellWidgetHelper<C, TextField, T> {

    private final TextField textField = new TextField();
    private final StringConverter<T> converter;
    private boolean initialized;
    private Cell<T> currentCell;
    private final WidgetActivationListener<C, TextField, T> listener;

    /** convenience factory method for TextFields containing Strings **/
    public static <C extends Cell<String>> TextFieldCellHelper<C, String> create() {
        return create(new WidgetActivationAdapter<C, TextField, String>());
    }

    /** convenience factory method for TextFields containing Strings **/
    public static <C extends Cell<String>> TextFieldCellHelper<C, String> create(WidgetActivationListener<C, TextField, String> listener) {
        return new TextFieldCellHelper<>(new DefaultStringConverter(), listener);
    }

    public TextFieldCellHelper(StringConverter<T> converter, WidgetActivationListener<C, TextField, T> listener) {
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
    public TextField getWidget(final C cell) {
        currentCell = cell;
        if (!initialized) {
            initialize();
        }
        return textField;
    }

    @Override
    public void onStartEdit(C cell, T item) {
        textField.setText(item == null ? "" : item.toString());
        textField.selectAll();
        listener.activate(textField, cell);
    }

    @Override
    public void onUpdate(C cell, T item) {
        textField.setText(item == null ? "" : item.toString());
    }
}
