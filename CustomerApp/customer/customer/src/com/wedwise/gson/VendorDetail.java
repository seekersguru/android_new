
package com.wedwise.gson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorDetail {

    @SerializedName("request_data")
    @Expose
    private RequestData requestData;
    @SerializedName("request_type")
    @Expose
    private String requestType;
    @SerializedName("code_string")
    @Expose
    private int codeString;
    @Expose
    private Json json;
    @Expose
    private String result;
    @SerializedName("error_fields")
    @Expose
    private List<Object> errorFields = new ArrayList<Object>();
    @Expose
    private int message;

    /**
     * 
     * @return
     *     The requestData
     */
    public RequestData getRequestData() {
        return requestData;
    }

    /**
     * 
     * @param requestData
     *     The request_data
     */
    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
    }

    /**
     * 
     * @return
     *     The requestType
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * 
     * @param requestType
     *     The request_type
     */
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    /**
     * 
     * @return
     *     The codeString
     */
    public int getCodeString() {
        return codeString;
    }

    /**
     * 
     * @param codeString
     *     The code_string
     */
    public void setCodeString(int codeString) {
        this.codeString = codeString;
    }

    /**
     * 
     * @return
     *     The json
     */
    public Json getJson() {
        return json;
    }

    /**
     * 
     * @param json
     *     The json
     */
    public void setJson(Json json) {
        this.json = json;
    }

    /**
     * 
     * @return
     *     The result
     */
    public String getResult() {
        return result;
    }

    /**
     * 
     * @param result
     *     The result
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * 
     * @return
     *     The errorFields
     */
    public List<Object> getErrorFields() {
        return errorFields;
    }

    /**
     * 
     * @param errorFields
     *     The error_fields
     */
    public void setErrorFields(List<Object> errorFields) {
        this.errorFields = errorFields;
    }

    /**
     * 
     * @return
     *     The message
     */
    public int getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(int message) {
        this.message = message;
    }

}
