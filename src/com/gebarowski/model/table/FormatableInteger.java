package com.gebarowski.model.table;

import java.util.Comparator;

public class FormatableInteger implements Comparator<FormatableInteger> {

    private int size;

    public FormatableInteger(int size) {
        this.size = size;
    }

    @Override
    public String toString(){

        /**
         * Method converts size of email adding suffix, accordingly: B,KB,MB.
         * @param size it's size of an email
         * @variable formattedValues Map links String with Integer in column size's comparator.
         * @return formatted value of size with suffix
         */

        String returnValue;

        if (size < 1024) {
            returnValue = size + " B";
        } else if (size < 1048576) {
            returnValue = size / 1025 + " KB";
        } else {
            returnValue = size / 1048576 + " MB";
        }

        return returnValue;


    }

    @Override
    public int compare(FormatableInteger o1, FormatableInteger o2) {
        Integer int1 = o1.size;
        Integer int2 = o2.size;
        return int1.compareTo(int2);
    }
}
