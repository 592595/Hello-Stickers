package com.iheshulin.yourname.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * Created by HeShulin on 2017/5/16.
 */
@Table("diary")
public class Diary {
    @Id
    private int id;

    @Column
    private int userid;

    @Column
    private String content;

    @Column
    private Date contenttime;

    @Column
    private String location;

    @Column
    private String contentphote;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getContenttime() {
        return contenttime;
    }

    public void setContenttime(Date contenttime) {
        this.contenttime = contenttime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContentphote() {
        return contentphote;
    }

    public void setContentphote(String contentphote) {
        this.contentphote = contentphote;
    }
}
