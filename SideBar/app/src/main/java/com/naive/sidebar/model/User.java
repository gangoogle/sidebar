package com.naive.sidebar.model;

/**
 * Created by zgyi on 2017/1/19.
 */

public class User {
    private String name;
    private int headPhoto;
    private String pingying;
    private String firstLetter;
    private boolean isHead;//按照姓名排序中的第一位
    private int telNumber;

    public User(String name, int telNumber, int headPhoto, String firstLetter, String pingying, boolean isHead) {
        this.name = name;
        this.telNumber = telNumber;
        this.headPhoto = headPhoto;
        this.firstLetter = firstLetter;
        this.pingying = pingying;
        this.isHead = isHead;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(int headPhoto) {
        this.headPhoto = headPhoto;
    }

    public String getPingying() {
        return pingying;
    }

    public void setPingying(String pingying) {
        this.pingying = pingying;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public boolean getIsHead() {
        return isHead;
    }

    public void setIsHead(boolean head) {
        isHead = head;
    }

    public int getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(int telNumber) {
        this.telNumber = telNumber;
    }
}
