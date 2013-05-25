package com.bosworth.javafx.cell;

/**
 * Decides which of two widgets should be used in a cell, based on the cell's value.
 *
 * @param <T>
 */
public interface CellWidgetDecider<T> {

    void decide(T value, CellWidgetDecidable cell);

}
