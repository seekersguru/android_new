
package com.wedwise.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option {

    @SerializedName("Starters (Any 2)")
    @Expose
    private String StartersAny2;
    @SerializedName("Cold Drink (Any 2)")
    @Expose
    private String ColdDrinkAny2;

    /**
     * 
     * @return
     *     The StartersAny2
     */
    public String getStartersAny2() {
        return StartersAny2;
    }

    /**
     * 
     * @param StartersAny2
     *     The Starters (Any 2)
     */
    public void setStartersAny2(String StartersAny2) {
        this.StartersAny2 = StartersAny2;
    }

    /**
     * 
     * @return
     *     The ColdDrinkAny2
     */
    public String getColdDrinkAny2() {
        return ColdDrinkAny2;
    }

    /**
     * 
     * @param ColdDrinkAny2
     *     The Cold Drink (Any 2)
     */
    public void setColdDrinkAny2(String ColdDrinkAny2) {
        this.ColdDrinkAny2 = ColdDrinkAny2;
    }

}
