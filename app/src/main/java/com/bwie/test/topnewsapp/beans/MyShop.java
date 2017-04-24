package com.bwie.test.topnewsapp.beans;

/**
 * Created by tianjieyu on 2017/4/24.
 */

public class MyShop {
    private String title;
    private String data;
    private String name;
    private String pic;
    @Override
    public String toString() {
        return "MyShop{" +
                "title='" + title + '\'' +
                ", data='" + data + '\'' +
                ", name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
