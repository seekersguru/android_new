
package com.wedwise.gson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lunch {

    @SerializedName("package_values")
    @Expose
    private List<PackageValue> packageValues = new ArrayList<PackageValue>();

    /**
     * 
     * @return
     *     The packageValues
     */
    public List<PackageValue> getPackageValues() {
        return packageValues;
    }

    /**
     * 
     * @param packageValues
     *     The package_values
     */
    public void setPackageValues(List<PackageValue> packageValues) {
        this.packageValues = packageValues;
    }

}
