package com.mju.video.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mju.video.domain.Collect;
import com.mju.video.domain.Image;
import com.mju.video.mapper.CollectMapper;
import com.mju.video.mapper.ImageMapper;
import com.mju.video.service.CollectService;
import com.mju.video.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageMapper imageMapper;
    @Override
    public PageInfo<Collect> findAll(Integer pageNum, Integer pageSize) {
        int count;
        PageInfo<Collect> pageInfo = null;
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Collect> collectList = collectMapper.selectAll();
            for (Collect collect : collectList) {
                count = imageService.selectCountByExample(collect.getId());
                collect.setCount(count);
            }
            pageInfo = new PageInfo<>(collectList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    @Override
    public boolean addOne(Collect collect) {
        collect.setCreateTime(new Date());
        int effNum = collectMapper.insert(collect);
        if (effNum > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delCollect(Integer collectId) {
        int effNum = 0;
        boolean isSuccess = false;
        try {
            effNum = collectMapper.deleteByPrimaryKey(collectId);
            isSuccess = imageService.deleteImageByCollectId(collectId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (effNum>0&&isSuccess){
            return true;
        }
        return false;
    }

    @Override
    public Collect findOne(Integer collectId) {
        Collect collect = collectMapper.selectByPrimaryKey(collectId);
        return collect;
    }
}
