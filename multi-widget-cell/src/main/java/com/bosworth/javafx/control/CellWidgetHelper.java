/**
 *
 * @author Michael Anselm Bosworth
 * Due to a perverse and unpleasing character, Michael has not commented this class.
 */
package com.bosworth.javafx.control;

import javafx.scene.Node;
import javafx.scene.control.Cell;

/**
 * Helps to embed a widget {@code W} in a single {@link Cell}. A separate {@code CellWidgetHelper} instance should be
 * used for each cell.
 *
 * @param <W> The type of {@link Node} that this CellWidgetHelper helps to embed in a {@link Cell}.
 * @param <T> The {@link Cell}'s values' type.
 */
public interface CellWidgetHelper<C extends Cell<T>, W extends Node, T> {

    W getWidget(C cell);

    void onStartEdit(C cell, T item);

    void onUpdate(C cell, T item);
}
