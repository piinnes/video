package com.mju.video.service;

import com.mju.video.domain.Rabbish;

import java.util.List;

public interface RabbishService {
    List<Rabbish> findAll();

    Rabbish findOne(Integer id);

    boolean updateRabbish(Rabbish rabbish);

    boolean add(Rabbish rabbish);

    boolean del(Rabbish rabbish);
}
