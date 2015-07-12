
package com.wedwise.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Minimum_ {

    @SerializedName("Minimum Price")
    @Expose
    private String MinimumPrice;

    /**
     * 
     * @return
     *     The MinimumPrice
     */
    public String getMinimumPrice() {
        return MinimumPrice;
    }

    /**
     * 
     * @param MinimumPrice
     *     The Minimum Price
     */
    public void setMinimumPrice(String MinimumPrice) {
        this.MinimumPrice = MinimumPrice;
    }

}
