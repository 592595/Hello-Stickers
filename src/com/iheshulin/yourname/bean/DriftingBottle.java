package com.iheshulin.yourname.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * Created by HeShulin on 2017/5/16.
 */
@Table("driftingbottle")
public class DriftingBottle {
    @Id
    private int id;

    @Column
    private int postuserid;

    @Column
    private int receiveuserid;

    @Column
    private String bottlecontent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostuserid() {
        return postuserid;
    }

    public void setPostuserid(int postuserid) {
        this.postuserid = postuserid;
    }

    public int getReceiveuserid() {
        return receiveuserid;
    }

    public void setReceiveuserid(int receiveuserid) {
        this.receiveuserid = receiveuserid;
    }

    public String getBottlecontent() {
        return bottlecontent;
    }

    public void setBottlecontent(String bottlecontent) {
        this.bottlecontent = bottlecontent;
    }
}
