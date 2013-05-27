package com.bosworth.javafx.control;

import javafx.scene.Node;
import javafx.scene.control.Cell;

public interface WidgetActivationListener<W extends Node, T> {

    void activate(W widget, Cell<T> cell);

}
