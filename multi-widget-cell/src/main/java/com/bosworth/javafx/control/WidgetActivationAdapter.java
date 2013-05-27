/**
 *
 * @author Michael Anselm Bosworth
 * Due to a perverse and unpleasing character, Michael has not commented this class.
 */
package com.bosworth.javafx.control;

import javafx.scene.Node;
import javafx.scene.control.Cell;

public class WidgetActivationAdapter<W extends Node, T> implements WidgetActivationListener<W, T> {
    @Override
    public void activate(W widget, Cell<T> cell) {
    }
}
