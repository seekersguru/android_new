
package com.wedwise.gson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class PackageValue {

    @Expose
    private Minimum minimum;
    @Expose
    private List<Option> options = new ArrayList<Option>();
    @Expose
    private Quoted_ quoted;
    @Expose
    private String label;

    /**
     * 
     * @return
     *     The minimum
     */
    public Minimum getMinimum() {
        return minimum;
    }

    /**
     * 
     * @param minimum
     *     The minimum
     */
    public void setMinimum(Minimum minimum) {
        this.minimum = minimum;
    }

    /**
     * 
     * @return
     *     The options
     */
    public List<Option> getOptions() {
        return options;
    }

    /**
     * 
     * @param options
     *     The options
     */
    public void setOptions(List<Option> options) {
        this.options = options;
    }

    /**
     * 
     * @return
     *     The quoted
     */
    public Quoted_ getQuoted() {
        return quoted;
    }

    /**
     * 
     * @param quoted
     *     The quoted
     */
    public void setQuoted(Quoted_ quoted) {
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
