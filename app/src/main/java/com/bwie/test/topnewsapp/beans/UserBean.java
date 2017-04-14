package com.bwie.test.topnewsapp.beans;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by tianjieyu on 2017/4/13.
 */
@Table(name = "user")
public class UserBean {
    @Column(name = "id",isId = true,autoGen = true)
    private int id;
    @Column(name = "phoneNum")
    private String phone;
    @Column(name = "pwd")
    private String pwd;
    @Column(name = "pic")
    private String pic;
    @Column(name = "name")
    private String name;

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

    public List<UserBean> getUserDB(DbManager db) throws DbException {
        return db.selector(UserBean.class).findAll();
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", pwd='" + pwd + '\'' +
                ", pic='" + pic + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
