package com.mju.video.domain;

import javax.persistence.*;
import java.util.Date;

@Table(name = "rabbish_img")
public class RabbishImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "url")
    private String url;
    @Column(name = "state")
    private Integer state;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "collect_id")
    private Integer collectId;
    @Column(name = "rabbish_id")
    private Integer rabbishId;
    @Transient
    private Collect collect;
    @Transient
    private Rabbish rabbish;

    public Integer getCollectId() {
        return collectId;
    }

    public void setCollectId(Integer collectId) {
        this.collectId = collectId;
    }

    public Collect getCollect() {
        return collect;
    }

    public void setCollect(Collect collect) {
        this.collect = collect;
    }

    public Rabbish getRabbish() {
        return rabbish;
    }

    public void setRabbish(Rabbish rabbish) {
        this.rabbish = rabbish;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getRabbishId() {
        return rabbishId;
    }

    public void setRabbishId(Integer rabbishId) {
        this.rabbishId = rabbishId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
