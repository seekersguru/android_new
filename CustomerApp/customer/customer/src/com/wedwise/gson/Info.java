
package com.wedwise.gson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @Expose
    private String contact;
    @SerializedName("starting_price")
    @Expose
    private String startingPrice;
    @SerializedName("top_name")
    @Expose
    private String topName;
    @SerializedName("hero_imgs")
    @Expose
    private List<String> heroImgs = new ArrayList<String>();
    @Expose
    private String name;
    @SerializedName("video_links")
    @Expose
    private List<String> videoLinks = new ArrayList<String>();
    @SerializedName("360_imgs")
    @Expose
    private List<String> _360Imgs = new ArrayList<String>();
    @Expose
    private String email;
    @SerializedName("top_address")
    @Expose
    private String topAddress;

    /**
     * 
     * @return
     *     The contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * 
     * @param contact
     *     The contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * 
     * @return
     *     The startingPrice
     */
    public String getStartingPrice() {
        return startingPrice;
    }

    /**
     * 
     * @param startingPrice
     *     The starting_price
     */
    public void setStartingPrice(String startingPrice) {
        this.startingPrice = startingPrice;
    }

    /**
     * 
     * @return
     *     The topName
     */
    public String getTopName() {
        return topName;
    }

    /**
     * 
     * @param topName
     *     The top_name
     */
    public void setTopName(String topName) {
        this.topName = topName;
    }

    /**
     * 
     * @return
     *     The heroImgs
     */
    public List<String> getHeroImgs() {
        return heroImgs;
    }

    /**
     * 
     * @param heroImgs
     *     The hero_imgs
     */
    public void setHeroImgs(List<String> heroImgs) {
        this.heroImgs = heroImgs;
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
     *     The videoLinks
     */
    public List<String> getVideoLinks() {
        return videoLinks;
    }

    /**
     * 
     * @param videoLinks
     *     The video_links
     */
    public void setVideoLinks(List<String> videoLinks) {
        this.videoLinks = videoLinks;
    }

    /**
     * 
     * @return
     *     The _360Imgs
     */
    public List<String> get360Imgs() {
        return _360Imgs;
    }

    /**
     * 
     * @param _360Imgs
     *     The 360_imgs
     */
    public void set360Imgs(List<String> _360Imgs) {
        this._360Imgs = _360Imgs;
    }

    /**
     * 
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The topAddress
     */
    public String getTopAddress() {
        return topAddress;
    }

    /**
     * 
     * @param topAddress
     *     The top_address
     */
    public void setTopAddress(String topAddress) {
        this.topAddress = topAddress;
    }

}
