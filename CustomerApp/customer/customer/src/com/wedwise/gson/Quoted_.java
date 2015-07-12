
package com.wedwise.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Quoted_ {

    @SerializedName("Quoted Price")
    @Expose
    private String QuotedPrice;

    /**
     * 
     * @return
     *     The QuotedPrice
     */
    public String getQuotedPrice() {
        return QuotedPrice;
    }

    /**
     * 
     * @param QuotedPrice
     *     The Quoted Price
     */
    public void setQuotedPrice(String QuotedPrice) {
        this.QuotedPrice = QuotedPrice;
    }

}
