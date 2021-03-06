/**
 * A TableCell whose choice of editing widget can be determined dynamically.
 *
 * @author Michael Anselm Bosworth
 */
package com.bosworth.javafx.control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TableCell;

public class MultiWidgetTableCell<S, T> extends TableCell<S, T> implements CellWidgetDecidable<MultiWidgetTableCell<S, T>, T> {
    private CellWidgetHelper<MultiWidgetTableCell<S, T>, ?, T> currentWidgetHelper;

    public MultiWidgetTableCell(final CellWidgetDecider<MultiWidgetTableCell<S, T>, T> decider) {
        super();
        itemProperty().addListener(new ChangeListener<T>() {
            @Override
            public void changed(ObservableValue<? extends T> observableValue, T t, T t2) {
                decider.decide(MultiWidgetTableCell.this);
            }
        });
    }

    /** {@inheritDoc} */
    @Override
    public void startEdit() {
        super.startEdit();
        if (!allowEditing() || currentWidgetHelper == null) {
            return;
        }
        final Node widget = currentWidgetHelper.getWidget(this);
        setText(null);
        setGraphic(widget);
        final T item = getItem();
        currentWidgetHelper.onStartEdit(this, item);
    }

    private boolean allowEditing() {
        if (!isEditable()) {
            return false;
        } else if ((getTableView() != null && !getTableView().isEditable())) {
            return false;
        } else if ((getTableColumn() != null && !getTableColumn().isEditable())) {
            return false;
        }
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItemText());
        setGraphic(null);
    }

    /** {@inheritDoc} */
    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (currentWidgetHelper == null) {
            return;
        }
        final Node widget = currentWidgetHelper.getWidget(this);
        if (isEmpty()) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                setText(null);
                setGraphic(widget);
                if (widget != null) {
                    currentWidgetHelper.onUpdate(this, item);
                }
            } else {
                setText(getItemText());
                setGraphic(null);
            }
        }
    }

    private String getItemText() {
        return getItem() != null ? getItem().toString() : "";
    }

    @Override
    public void use(CellWidgetHelper<MultiWidgetTableCell<S, T>, ? extends Node, T> widget) {
        currentWidgetHelper = widget;
    }

    public static interface Decider<S, T> extends CellWidgetDecider<MultiWidgetTableCell<S, T>, T> {}
}
