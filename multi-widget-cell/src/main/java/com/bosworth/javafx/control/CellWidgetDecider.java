package com.bosworth.javafx.control;

import javafx.scene.control.Cell;

/**
 * Decides what widget should be used in a cell.
 *
 * @param <T> The {@link javafx.scene.control.Cell}'s values' type.
 */
public interface CellWidgetDecider<T> {

    <C extends Cell<T> & CellWidgetDecidable<T>> void decide(C cell);

}
