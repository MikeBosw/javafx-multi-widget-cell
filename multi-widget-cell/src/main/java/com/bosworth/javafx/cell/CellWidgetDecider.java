package com.bosworth.javafx.cell;

import com.sun.istack.internal.Nullable;

/**
 * Decides what widget should be used in a cell.
 *
 * @param <T> The {@link javafx.scene.control.Cell}'s values' type.
 */
public interface CellWidgetDecider<T> {

    void decide(@Nullable T value, CellWidgetDecidable cell);

}
