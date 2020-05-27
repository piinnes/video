package com.mju.video.domain;

public class Upload {
    private String base64Str;
    private Integer rab_id;
    private Integer collectId;

    public String getBase64Str() {
        return base64Str;
    }

    public void setBase64Str(String base64Str) {
        this.base64Str = base64Str;
    }

    public Integer getRab_id() {
        return rab_id;
    }

    public void setRab_id(Integer rab_id) {
        this.rab_id = rab_id;
    }

    public Integer getCollectId() {
        return collectId;
    }

    public void setCollectId(Integer collectId) {
        this.collectId = collectId;
    }
}
