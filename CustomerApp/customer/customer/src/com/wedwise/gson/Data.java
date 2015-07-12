
package com.wedwise.gson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Data {

    @Expose
    private Info info;
    @Expose
    private Bid bid;
    @Expose
    private Book book;
    @Expose
    private List<Section> sections = new ArrayList<Section>();

    /**
     * 
     * @return
     *     The info
     */
    public Info getInfo() {
        return info;
    }

    /**
     * 
     * @param info
     *     The info
     */
    public void setInfo(Info info) {
        this.info = info;
    }

    /**
     * 
     * @return
     *     The bid
     */
    public Bid getBid() {
        return bid;
    }

    /**
     * 
     * @param bid
     *     The bid
     */
    public void setBid(Bid bid) {
        this.bid = bid;
    }

    /**
     * 
     * @return
     *     The book
     */
    public Book getBook() {
        return book;
    }

    /**
     * 
     * @param book
     *     The book
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * 
     * @return
     *     The sections
     */
    public List<Section> getSections() {
        return sections;
    }

    /**
     * 
     * @param sections
     *     The sections
     */
    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

}
