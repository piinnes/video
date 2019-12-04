package com.mju.video.service;

import com.github.pagehelper.PageInfo;
import com.mju.video.domain.Collect;

public interface CollectService {
    PageInfo<Collect> findAll(Integer pageNum, Integer pageSize);

    boolean addOne(Collect collect);

    boolean delCollect(Integer id);

    Collect findOne(Integer collectId);
}
