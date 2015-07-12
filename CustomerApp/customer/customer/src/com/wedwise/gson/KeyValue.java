
package com.wedwise.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KeyValue {

    @SerializedName("Package 1 (Lunch)")
    @Expose
    private String Package1Lunch;
    @SerializedName("Package 2 (Dinner)")
    @Expose
    private String Package2Dinner;
    @SerializedName("Package 3 (Lunch/Dinner)")
    @Expose
    private String Package3LunchDinner;

    /**
     * 
     * @return
     *     The Package1Lunch
     */
    public String getPackage1Lunch() {
        return Package1Lunch;
    }

    /**
     * 
     * @param Package1Lunch
     *     The Package 1 (Lunch)
     */
    public void setPackage1Lunch(String Package1Lunch) {
        this.Package1Lunch = Package1Lunch;
    }

    /**
     * 
     * @return
     *     The Package2Dinner
     */
    public String getPackage2Dinner() {
        return Package2Dinner;
    }

    /**
     * 
     * @param Package2Dinner
     *     The Package 2 (Dinner)
     */
    public void setPackage2Dinner(String Package2Dinner) {
        this.Package2Dinner = Package2Dinner;
    }

    /**
     * 
     * @return
     *     The Package3LunchDinner
     */
    public String getPackage3LunchDinner() {
        return Package3LunchDinner;
    }

    /**
     * 
     * @param Package3LunchDinner
     *     The Package 3 (Lunch/Dinner)
     */
    public void setPackage3LunchDinner(String Package3LunchDinner) {
        this.Package3LunchDinner = Package3LunchDinner;
    }

}
