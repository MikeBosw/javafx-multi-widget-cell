package com.bosworth.javafx.control;

import javafx.scene.Node;
import javafx.scene.control.Cell;

/**
 * A {@code CellWidgetDecidable} can swap out one widget implementation for another.
 *
 * @param <T> The {@link javafx.scene.control.Cell}'s values' type.
 */
public interface CellWidgetDecidable<C extends Cell<T>, T> {

    void use(CellWidgetHelper<C, ? extends Node, T> widget);

}
