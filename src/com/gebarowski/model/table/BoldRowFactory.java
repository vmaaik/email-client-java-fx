package com.gebarowski.model.table;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableRow;

public class BoldRowFactory<T extends AbstractTableItem> extends TableRow<T> {

    private final SimpleBooleanProperty bold = new SimpleBooleanProperty();
    private T currentItem = null;

    public BoldRowFactory() {
        super();

        bold.addListener((ObservableValue<? extends Boolean> observable, Boolean oldVal, Boolean newVal) -> {
            if (currentItem != null && currentItem == getItem()) {
                updateItem(getItem(), isEmpty());
            }

        });

        itemProperty().addListener((ObservableValue<? extends T> observable, T oldVal, T newVal) -> {
            bold.unbind();
            if (newVal != null) {
                bold.bind(newVal.getReadProperty());
                currentItem = newVal;
            }
        });
    }


    @Override
    final protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && item.isRead()) {
            setStyle("-fx-font-weight:bold");
        } else {
            setStyle("");
        }
    }
}
