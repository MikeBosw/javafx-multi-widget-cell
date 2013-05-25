/**
 *
 * @author Michael Anselm Bosworth
 * Due to a perverse and unpleasing character, Michael has not commented this class.
 */
package com.bosworth.javafx.control;

import javafx.scene.Node;
import javafx.scene.control.Cell;

/**
 * Helps to embed a widget {@code W} in a single {@link Cell}. If more than one cell needs to be edited at the same
 * time, multiple {@code CellWidgetHelper} instances should be used.
 *
 * @param <W> The type of {@link Node} that this CellWidgetHelper helps to embed in a {@link Cell}.
 * @param <T> The {@link Cell}'s values' type.
 */
public interface CellWidgetHelper<W extends Node, T> {

    W getWidget(Cell<T> cell);

    void onStartEdit(Cell<T> cell, T item);

    void onUpdate(Cell<T> cell, T item);
}
