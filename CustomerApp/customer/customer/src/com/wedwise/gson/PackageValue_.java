
package com.wedwise.gson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class PackageValue_ {

    @Expose
    private Minimum_ minimum;
    @Expose
    private List<Option_> options = new ArrayList<Option_>();
    @Expose
    private Quoted__ quoted;
    @Expose
    private String label;

    /**
     * 
     * @return
     *     The minimum
     */
    public Minimum_ getMinimum() {
        return minimum;
    }

    /**
     * 
     * @param minimum
     *     The minimum
     */
    public void setMinimum(Minimum_ minimum) {
        this.minimum = minimum;
    }

    /**
     * 
     * @return
     *     The options
     */
    public List<Option_> getOptions() {
        return options;
    }

    /**
     * 
     * @param options
     *     The options
     */
    public void setOptions(List<Option_> options) {
        this.options = options;
    }

    /**
     * 
     * @return
     *     The quoted
     */
    public Quoted__ getQuoted() {
        return quoted;
    }

    /**
     * 
     * @param quoted
     *     The quoted
     */
    public void setQuoted(Quoted__ quoted) {
        this.quoted = quoted;
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
