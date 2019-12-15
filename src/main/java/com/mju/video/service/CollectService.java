package com.mju.video.service;

import com.github.pagehelper.PageInfo;
import com.mju.video.domain.Collect;

import java.util.List;

public interface CollectService {
    /**
     * 采集信息列表
     * @param pageNum 页码
     * @param pageSize 一页显示几条
     * @return
     */
    PageInfo<Collect> findAll(Integer pageNum, Integer pageSize);


    boolean addOne(Collect collect);

    boolean delCollect(Integer id);

    Collect findOne(Integer collectId);

    void updateCollect(Collect collect);

    List<Collect> selectAll();

    PageInfo<Collect> findAllByLikeName(Integer pageNum, Integer pageSize, String searchName);
}
