package com.example.praveen.sampleapplication;

import java.util.List;

/**
 * Created by praveen on 9/26/2015.
 */
public class ListViewItems {

    private String rest_name;
    private String offers;
    private String imageResUrl;
    private String neighbourHoodName;

    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getNeighbourHoodName() {
        return neighbourHoodName;
    }

    public void setNeighbourHoodName(String neighbourHoodName) {
        this.neighbourHoodName = neighbourHoodName;
    }

    public List getCategories() {
        return categories;
    }

    public void setCategories(List categories) {
        this.categories = categories;
    }

    private List categories;
    public float distance;


    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getRest_name() {
        return rest_name;
    }

    public void setRest_name(String rest_name) {
        this.rest_name = rest_name;
    }

    public String getOffers() {
        return offers;
    }

    public void setOffers(String offers) {
        this.offers = offers;
    }

    public String getImageResUrl() {
        return imageResUrl;
    }

    public void setImageResUrl(String imageResUrl) {
        this.imageResUrl = imageResUrl;
    }


}
