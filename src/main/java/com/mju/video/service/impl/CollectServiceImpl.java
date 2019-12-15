package com.mju.video.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mju.video.domain.Collect;
import com.mju.video.mapper.CollectMapper;
import com.mju.video.mapper.ImageMapper;
import com.mju.video.service.CollectImageService;
import com.mju.video.service.CollectService;
import com.mju.video.service.RabbishImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private CollectImageService collectImageService;
    @Autowired
    private RabbishImageService rabbishImageService;
    @Override
    public PageInfo<Collect> findAll(Integer pageNum, Integer pageSize) {
        PageInfo<Collect> pageInfo = null;
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Collect> collectList = collectMapper.selectAll();
            for (Collect collect : collectList) {
                int count = collectImageService.selectCountByCollectId(collect.getId());
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
        try {
            collectImageService.deleteImageByCollectId(collectId);
            rabbishImageService.delectRabbishImageByCollectId(collectId);
            collectMapper.deleteByPrimaryKey(collectId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Collect findOne(Integer collectId) {
        Collect collect = collectMapper.selectByPrimaryKey(collectId);
        return collect;
    }

    @Override
    public void updateCollect(Collect collect) {
        try {
            collectMapper.updateByPrimaryKeySelective(collect);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Collect> selectAll() {
        List<Collect> collectList = collectMapper.selectAll();
        if (collectList!=null&&collectList.size()>0){
            return collectList;
        }
        return null;
    }

    @Override
    public PageInfo<Collect> findAllByLikeName(Integer pageNum, Integer pageSize, String searchName) {

        Example example = new Example(Collect.class);
        example.createCriteria().andLike("name", "%"+searchName+"%");
        PageInfo<Collect> pageInfo = null;
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Collect> collectList = collectMapper.selectByExample(example);
            for (Collect collect : collectList) {
                int count = collectImageService.selectCountByCollectId(collect.getId());
                collect.setCount(count);
            }
            pageInfo = new PageInfo<>(collectList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }
}
