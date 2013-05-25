package com.bosworth.javafx.cell;

import javafx.scene.Node;

/**
 * A {@code CellWidgetDecidable} can swap out one widget implementation for another.
 *
 * @param <T> The {@link javafx.scene.control.Cell}'s values' type.
 */
public interface CellWidgetDecidable<T> {

    void use(CellWidgetHelper<? extends Node, T> widget);

}
