
package com.wedwise.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BidOptions {

    @SerializedName("min_per_unit")
    @Expose
    private double minPerUnit;
    @Expose
    private String name;
    @Expose
    private Item item;
    @Expose
    private Quantity quantity;

    /**
     * 
     * @return
     *     The minPerUnit
     */
    public double getMinPerUnit() {
        return minPerUnit;
    }

    /**
     * 
     * @param minPerUnit
     *     The min_per_unit
     */
    public void setMinPerUnit(double minPerUnit) {
        this.minPerUnit = minPerUnit;
    }

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
     *     The item
     */
    public Item getItem() {
        return item;
    }

    /**
     * 
     * @param item
     *     The item
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * 
     * @return
     *     The quantity
     */
    public Quantity getQuantity() {
        return quantity;
    }

    /**
     * 
     * @param quantity
     *     The quantity
     */
    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

}
