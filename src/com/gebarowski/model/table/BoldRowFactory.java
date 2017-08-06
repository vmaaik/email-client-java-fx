package com.gebarowski.model.table;

import javafx.scene.control.TableRow;

public class BoldRowFactory<T extends AbstractTableItem> extends TableRow<T> {

    @Override
    final protected void updateItem(T item, boolean empty){
        super.updateItem(item, empty);
        if(item != null && item.isRead()){
            setStyle("-fx-font-weight:bold");
        } else{
            setStyle("");
        }



    }



}
