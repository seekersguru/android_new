
package com.wedwise.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Book {

    @SerializedName("event_date")
    @Expose
    private int eventDate;
    @Expose
    private String button;
    @SerializedName("time_slot")
    @Expose
    private TimeSlot_ timeSlot;
    @Expose
    private String type;
    @SerializedName("package")
    @Expose
    private Package_ _package;

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
    public TimeSlot_ getTimeSlot() {
        return timeSlot;
    }

    /**
     * 
     * @param timeSlot
     *     The time_slot
     */
    public void setTimeSlot(TimeSlot_ timeSlot) {
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
     *     The _package
     */
    public Package_ getPackage() {
        return _package;
    }

    /**
     * 
     * @param _package
     *     The package
     */
    public void setPackage(Package_ _package) {
        this._package = _package;
    }

}
