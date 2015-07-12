
package com.wedwise.gson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dinner {

    @SerializedName("package_values")
    @Expose
    private List<PackageValue_> packageValues = new ArrayList<PackageValue_>();

    /**
     * 
     * @return
     *     The packageValues
     */
    public List<PackageValue_> getPackageValues() {
        return packageValues;
    }

    /**
     * 
     * @param packageValues
     *     The package_values
     */
    public void setPackageValues(List<PackageValue_> packageValues) {
        this.packageValues = packageValues;
    }

}
