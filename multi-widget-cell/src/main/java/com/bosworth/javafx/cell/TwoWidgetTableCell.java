/**
 *
 * @author Michael Anselm Bosworth
 * Due to a perverse and unpleasing character, Michael has not commented this class.
 */
package com.bosworth.javafx.cell;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TableCell;

public class TwoWidgetTableCell<W1 extends Node, W2 extends Node, S, T> extends TableCell<S, T> implements CellWidgetDecidable {
    private final CellWidgetHelper<W1, T> widgetAHelper;
    private final CellWidgetHelper<W2, T> widgetBHelper;
    private CellWidgetHelper<?, T> currentWidgetHelper;

    public TwoWidgetTableCell(CellWidgetHelper<W1, T> widgetAHelper,
                              CellWidgetHelper<W2, T> widgetBHelper,
                              final CellWidgetDecider<T> decider) {
        super();
        widgetAHelper.setCell(this);
        widgetBHelper.setCell(this);
        this.widgetAHelper = widgetAHelper;
        this.widgetBHelper = widgetBHelper;
        currentWidgetHelper = widgetAHelper;
        itemProperty().addListener(new ChangeListener<T>() {
            @Override
            public void changed(ObservableValue<? extends T> observableValue, T t, T t2) {
                decider.decide(t2, TwoWidgetTableCell.this);
            }
        });
    }

    @Override
    public void useWidgetA() {
        currentWidgetHelper = widgetAHelper;
    }

    @Override
    public void useWidgetB() {
        currentWidgetHelper = widgetBHelper;
    }

    /** {@inheritDoc} */
    @Override public void startEdit() {
        super.startEdit();
        final Node widget = currentWidgetHelper.getWidget();
        setText(null);
        setGraphic(widget);
        final T item = getItem();
        currentWidgetHelper.onStartEdit(item);
    }

    /** {@inheritDoc} */
    @Override public void cancelEdit() {
        super.cancelEdit();
        setText(getItemText());
        setGraphic(null);
    }

    /** {@inheritDoc} */
    @Override public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        final Node widget = currentWidgetHelper.getWidget();
        if (isEmpty()) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                setText(null);
                setGraphic(widget);
                if (widget != null) {
                    currentWidgetHelper.onUpdate(item);
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
}
