package com.bosworth.javafx.control;

import javafx.scene.Node;
import javafx.scene.control.Cell;

public interface WidgetActivationListener<C extends Cell<T>, W extends Node, T> {

    void activate(W widget, C cell);

}
