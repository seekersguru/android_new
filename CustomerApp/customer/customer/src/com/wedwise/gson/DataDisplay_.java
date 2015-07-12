
package com.wedwise.gson;

import com.google.gson.annotations.Expose;

public class DataDisplay_ {

    @Expose
    private com.wedwise.gson.Lunch Lunch;
    @Expose
    private com.wedwise.gson.Dinner Dinner;
    @Expose
    private String type;

    /**
     * 
     * @return
     *     The Lunch
     */
    public com.wedwise.gson.Lunch getLunch() {
        return Lunch;
    }

    /**
     * 
     * @param Lunch
     *     The Lunch
     */
    public void setLunch(com.wedwise.gson.Lunch Lunch) {
        this.Lunch = Lunch;
    }

    /**
     * 
     * @return
     *     The Dinner
     */
    public com.wedwise.gson.Dinner getDinner() {
        return Dinner;
    }

    /**
     * 
     * @param Dinner
     *     The Dinner
     */
    public void setDinner(com.wedwise.gson.Dinner Dinner) {
        this.Dinner = Dinner;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

}
