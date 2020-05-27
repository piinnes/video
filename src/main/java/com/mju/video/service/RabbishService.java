package com.mju.video.service;

import com.github.pagehelper.PageInfo;
import com.mju.video.domain.Collect;
import com.mju.video.domain.Rabbish;

import java.util.List;

public interface RabbishService {
    List<Rabbish> findAll();

    Rabbish findOne(Integer id);

    boolean updateRabbish(Rabbish rabbish);

    boolean addRabbish(Rabbish rabbish);

    boolean delRabbish(Rabbish rabbish);

    List<Rabbish> findByLikeName(String likeName);

    PageInfo<Rabbish> getList(Integer pageNum, Integer pageSize);

    PageInfo<Rabbish> getRubbishByCondition(Integer pageNum, Integer pageSize, String name, Integer operate, Integer total);

    Rabbish findOneByName(String name);
}
