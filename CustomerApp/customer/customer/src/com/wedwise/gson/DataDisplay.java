
package com.wedwise.gson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataDisplay {

    @SerializedName("key_values")
    @Expose
    private List<KeyValue> keyValues = new ArrayList<KeyValue>();
    @Expose
    private String type;
    @SerializedName("read_more")
    @Expose
    private List<ReadMore> readMore = new ArrayList<ReadMore>();

    /**
     * 
     * @return
     *     The keyValues
     */
    public List<KeyValue> getKeyValues() {
        return keyValues;
    }

    /**
     * 
     * @param keyValues
     *     The key_values
     */
    public void setKeyValues(List<KeyValue> keyValues) {
        this.keyValues = keyValues;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The readMore
     */
    public List<ReadMore> getReadMore() {
        return readMore;
    }

    /**
     * 
     * @param readMore
     *     The read_more
     */
    public void setReadMore(List<ReadMore> readMore) {
        this.readMore = readMore;
    }

}
