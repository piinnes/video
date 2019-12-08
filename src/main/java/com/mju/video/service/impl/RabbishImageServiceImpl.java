package com.mju.video.service.impl;

import com.mju.video.domain.Collect;
import com.mju.video.domain.Rabbish;
import com.mju.video.domain.RabbishImage;
import com.mju.video.mapper.CollectMapper;
import com.mju.video.mapper.RabbishImageMapper;
import com.mju.video.mapper.RabbishMapper;
import com.mju.video.service.RabbishImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class RabbishImageServiceImpl implements RabbishImageService {
    @Autowired
    private RabbishImageMapper rabbishImageMapper;
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private RabbishMapper rabbishMapper;
    @Override
    public boolean save(RabbishImage rabbishImage) {
        int effNum = rabbishImageMapper.insert(rabbishImage);
        if (effNum>0){
            return true;
        }
        return false;
    }

    @Override
    public List<RabbishImage> findAllByRabbishId(Integer id) {
        Example example = new Example(RabbishImage.class);
        example.createCriteria().andEqualTo("rabbishId", id).andEqualTo("state", 0);
        List<RabbishImage> rabbishImageList = rabbishImageMapper.selectByExample(example);
        if (rabbishImageList!=null&&rabbishImageList.size()>0){
            return rabbishImageList;
        }
        return null;
    }

    @Override
    public RabbishImage findOne(Integer imageId) {
        RabbishImage rabbishImage = rabbishImageMapper.selectByPrimaryKey(imageId);
        Collect collect = collectMapper.selectByPrimaryKey(rabbishImage.getCollectId());
        rabbishImage.setCollect(collect);
        Rabbish rabbish = rabbishMapper.selectByPrimaryKey(rabbishImage.getRabbishId());
        rabbishImage.setRabbish(rabbish);
        return rabbishImage;
    }

    @Override
    public void delectRabbishImageById(Integer imgId) {
        RabbishImage rabbishImage = rabbishImageMapper.selectByPrimaryKey(imgId);
        rabbishImage.setState(-1);
        rabbishImageMapper.updateByPrimaryKeySelective(rabbishImage);
    }
}
