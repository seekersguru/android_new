
package com.wedwise.gson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class TimeSlot {

    @Expose
    private String name;
    @Expose
    private List<List<String>> value = new ArrayList<List<String>>();

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The value
     */
    public List<List<String>> getValue() {
        return value;
    }

    /**
     * 
     * @param value
     *     The value
     */
    public void setValue(List<List<String>> value) {
        this.value = value;
    }

}
