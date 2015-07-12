
package com.wedwise.gson;

import com.google.gson.annotations.Expose;

public class Min {

    @Expose
    private String message;
    @Expose
    private int value;

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     The value
     */
    public int getValue() {
        return value;
    }

    /**
     * 
     * @param value
     *     The value
     */
    public void setValue(int value) {
        this.value = value;
    }

}
