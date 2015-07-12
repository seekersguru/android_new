
package com.wedwise.gson;

import com.google.gson.annotations.Expose;

public class Item {

    @Expose
    private int max;
    @Expose
    private int min;
    @Expose
    private String label;

    /**
     * 
     * @return
     *     The max
     */
    public int getMax() {
        return max;
    }

    /**
     * 
     * @param max
     *     The max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * 
     * @return
     *     The min
     */
    public int getMin() {
        return min;
    }

    /**
     * 
     * @param min
     *     The min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * 
     * @return
     *     The label
     */
    public String getLabel() {
        return label;
    }

    /**
     * 
     * @param label
     *     The label
     */
    public void setLabel(String label) {
        this.label = label;
    }

}
