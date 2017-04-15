package com.bwie.test.topnewsapp.beans;

/**
 * Created by tianjieyu on 2017/4/14.
 */

public class SQLiteTitle {
    private String titleName;
    private String uri;
    private String state;

    public SQLiteTitle(String titleName, String uri, String state) {
        this.titleName = titleName;
        this.uri = uri;
        this.state = state;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
