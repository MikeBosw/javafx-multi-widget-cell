package com.bosworth.javafx.control;

import javafx.scene.control.Cell;

/**
 * Decides what widget should be used in a cell.
 *
 * @param <T> The {@link javafx.scene.control.Cell}'s values' type.
 */
public interface CellWidgetDecider<C extends Cell<T> & CellWidgetDecidable<C, T>, T> {

    void decide(C cell);

}
