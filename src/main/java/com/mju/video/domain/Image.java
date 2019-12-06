package com.mju.video.domain;

import javax.persistence.*;
import java.util.Date;

@Table(name = "image")
public class Image {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "url")
    private String url;
    @Column(name = "rab_id")
    private Integer rab_id;
    @Column(name = "state")
    private Integer state;
    @Column(name = "collect_id")
    private Integer collectId;
    @Column(name = "create_time")
    private Date createTime;
    @Transient
    private Collect collect;
    @Transient
    private Rabbish rabbish;

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

    public Integer getRab_id() {
        return rab_id;
    }

    public void setRab_id(Integer rab_id) {
        this.rab_id = rab_id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCollectId() {
        return collectId;
    }

    public void setCollectId(Integer collectId) {
        this.collectId = collectId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
}
