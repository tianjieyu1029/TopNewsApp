package com.bwie.test.topnewsapp.beans;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by tianjieyu on 2017/4/11.
 */
@Table(name = "TitleTable")
public class TitleDB {
    @Column(name = "id",isId = true,autoGen = true)
    private int id;
    @Column(name = "tid")
    private int tid;
    @Column(name = "uri")
    private String uri;
    @Column(name = "title")
    private String title;

    public List<TitleDB> getTitleDB(DbManager db) throws DbException {
        return db.selector(TitleDB.class).findAll();
    }
    @Override
    public String toString() {
        return "TitleDB{" +
                "id=" + id +
                ", uri='" + uri + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
