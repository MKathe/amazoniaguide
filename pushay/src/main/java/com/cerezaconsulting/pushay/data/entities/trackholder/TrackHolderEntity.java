package com.cerezaconsulting.pushay.data.entities.trackholder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by katherine on 29/03/17.
 */

public class TrackHolderEntity<T> implements Serializable{

    private ArrayList<T> results;
    private String count;
    private String next;
    private String previous;

    public ArrayList<T> getResults() {
        return results;
    }

    public void setResults(ArrayList<T> results) {
        this.results = results;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }
}
