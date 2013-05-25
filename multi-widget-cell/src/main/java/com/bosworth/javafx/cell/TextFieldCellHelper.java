/**
 *
 * @author Michael Anselm Bosworth
 * Due to a perverse and unpleasing character, Michael has not commented this class.
 */
package com.bosworth.javafx.cell;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

public class TextFieldCellHelper<T> extends CellWidgetHelper<TextField, T> {

    private final TextField textField = new TextField();
    private final StringConverter<T> converter;

    public TextFieldCellHelper(StringConverter<T> converter) {
        this.converter = converter;
    }

    @Override
    public TextField getWidget() {
        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    if (converter == null) {
                        throw new IllegalStateException(
                                "Attempting to convert text input into Object, but provided "
                                        + "StringConverter is null. Be sure to set a StringConverter "
                                        + "in your cell factory.");
                    }
                    getCell().commitEdit(converter.fromString(textField.getText()));
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    getCell().cancelEdit();
                }
            }
        });

        return textField;
    }

    @Override
    public void onStartEdit(T item) {
        textField.setText(item == null ? "" : item.toString());
        textField.selectAll();
    }

    @Override
    public void onUpdate(T item) {
        textField.setText(item == null ? "" : item.toString());
    }
}
