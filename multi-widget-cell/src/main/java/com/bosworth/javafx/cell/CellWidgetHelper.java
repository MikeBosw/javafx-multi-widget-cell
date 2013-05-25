/**
 *
 * @author Michael Anselm Bosworth
 * Due to a perverse and unpleasing character, Michael has not commented this class.
 */
package com.bosworth.javafx.cell;

import javafx.scene.Node;
import javafx.scene.control.Cell;

public abstract class CellWidgetHelper<W extends Node, T> {

    private Cell<T> cell;

    protected Cell<T> getCell() {
        return cell;
    }

    protected void setCell(Cell<T> cell) {
        this.cell = cell;
    }

    public abstract W getWidget();

    public abstract void onStartEdit(T item);

    public abstract void onUpdate(T item);
}
