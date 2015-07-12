
package com.wedwise.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bid {

    @Expose
    private String text;
    @Expose
    private Quoted quoted;
    @SerializedName("package")
    @Expose
    private Package _package;
    @SerializedName("event_date")
    @Expose
    private int eventDate;
    @Expose
    private String button;
    @SerializedName("time_slot")
    @Expose
    private TimeSlot timeSlot;
    @Expose
    private String type;
    @SerializedName("bid_options")
    @Expose
    private BidOptions bidOptions;

    /**
     * 
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 
     * @return
     *     The quoted
     */
    public Quoted getQuoted() {
        return quoted;
    }

    /**
     * 
     * @param quoted
     *     The quoted
     */
    public void setQuoted(Quoted quoted) {
        this.quoted = quoted;
    }

    /**
     * 
     * @return
     *     The _package
     */
    public Package getPackage() {
        return _package;
    }

    /**
     * 
     * @param _package
     *     The package
     */
    public void setPackage(Package _package) {
        this._package = _package;
    }

    /**
     * 
     * @return
     *     The eventDate
     */
    public int getEventDate() {
        return eventDate;
    }

    /**
     * 
     * @param eventDate
     *     The event_date
     */
    public void setEventDate(int eventDate) {
        this.eventDate = eventDate;
    }

    /**
     * 
     * @return
     *     The button
     */
    public String getButton() {
        return button;
    }

    /**
     * 
     * @param button
     *     The button
     */
    public void setButton(String button) {
        this.button = button;
    }

    /**
     * 
     * @return
     *     The timeSlot
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * 
     * @param timeSlot
     *     The time_slot
     */
    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
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
     *     The bidOptions
     */
    public BidOptions getBidOptions() {
        return bidOptions;
    }

    /**
     * 
     * @param bidOptions
     *     The bid_options
     */
    public void setBidOptions(BidOptions bidOptions) {
        this.bidOptions = bidOptions;
    }

}
