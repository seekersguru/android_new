
package com.wedwise.gson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Section {

    @SerializedName("data_display")
    @Expose
    private List<JsonObject> dataDisplay = new ArrayList<JsonObject>();
    @Expose
    private String heading;

    /**
     * 
     * @return
     *     The dataDisplay
     */
    public List<JsonObject> getDataDisplay() {
        return dataDisplay;
    }

    /**
     * 
     * @param dataDisplay
     *     The data_display
     */
    public void setDataDisplay(List<JsonObject> dataDisplay) {
        this.dataDisplay = dataDisplay;
    }

    /**
     * 
     * @return
     *     The heading
     */
    public String getHeading() {
        return heading;
    }

    /**
     * 
     * @param heading
     *     The heading
     */
    public void setHeading(String heading) {
        this.heading = heading;
    }

}
