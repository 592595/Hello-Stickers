package com.iheshulin.yourname.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * Created by HeShulin on 2017/5/16.
 */
@Table("obtaindiary")
public class ObtainDiary {
    @Id
    private int id;

    @Column
    private int userid;

    @Column
    private int otheruserid;

    @Column
    private Date diarytime;

    @Column
    private double completion;

    public Date getObtaintime() {
        return obtaintime;
    }

    public void setObtaintime(Date obtaintime) {
        this.obtaintime = obtaintime;
    }

    @Column
    private Date obtaintime;

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

    public int getOtheruserid() {
        return otheruserid;
    }

    public void setOtheruserid(int otheruserid) {
        this.otheruserid = otheruserid;
    }

    public Date getDiarytime() {
        return diarytime;
    }

    public void setDiarytime(Date diarytime) {
        this.diarytime = diarytime;
    }

    public double getCompletion() {
        return completion;
    }

    public void setCompletion(double completion) {
        this.completion = completion;
    }
}
