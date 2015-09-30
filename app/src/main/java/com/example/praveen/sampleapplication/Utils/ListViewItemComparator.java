package com.example.praveen.sampleapplication.Utils;

import com.example.praveen.sampleapplication.ListViewItems;

import java.util.Comparator;

/**
 * Created by praveen on 9/27/2015.
 */
public class ListViewItemComparator implements Comparator<ListViewItems> {


    @Override
    public int compare(ListViewItems lhs, ListViewItems rhs) {
        return (String.valueOf(lhs.getDistance())).compareTo(String.valueOf(rhs.getDistance()));
    }
}
