
package com.wedwise.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestData {

    @SerializedName("image_type")
    @Expose
    private String imageType;
    @Expose
    private String mode;
    @SerializedName("vendor_email")
    @Expose
    private String vendorEmail;

    /**
     * 
     * @return
     *     The imageType
     */
    public String getImageType() {
        return imageType;
    }

    /**
     * 
     * @param imageType
     *     The image_type
     */
    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    /**
     * 
     * @return
     *     The mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * 
     * @param mode
     *     The mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * 
     * @return
     *     The vendorEmail
     */
    public String getVendorEmail() {
        return vendorEmail;
    }

    /**
     * 
     * @param vendorEmail
     *     The vendor_email
     */
    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

}
