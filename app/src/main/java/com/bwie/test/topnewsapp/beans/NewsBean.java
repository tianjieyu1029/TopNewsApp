package com.bwie.test.topnewsapp.beans;

/**
 * Created by tianjieyu on 2017/4/12.
 */

public class NewsBean {
    public String getTitle() {
        return title;
    }

    public NewsBean(String title, String key) {
        this.title = title;
        this.key = key;
    }

    @Override
    public String toString() {
        return "NewsBean{" +
                "title='" + title + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String title;
    private String key;
}
