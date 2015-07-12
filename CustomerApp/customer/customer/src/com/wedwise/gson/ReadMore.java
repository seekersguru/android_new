
package com.wedwise.gson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReadMore {

    @SerializedName("data_display")
    @Expose
    private List<DataDisplay_> dataDisplay = new ArrayList<DataDisplay_>();
    @Expose
    private String heading;

    /**
     * 
     * @return
     *     The dataDisplay
     */
    public List<DataDisplay_> getDataDisplay() {
        return dataDisplay;
    }

    /**
     * 
     * @param dataDisplay
     *     The data_display
     */
    public void setDataDisplay(List<DataDisplay_> dataDisplay) {
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
