package com.mju.video.service.impl;

import com.mju.video.domain.Collect;
import com.mju.video.domain.CollectImage;
import com.mju.video.domain.Rabbish;
import com.mju.video.domain.RabbishImage;
import com.mju.video.mapper.CollectImageMapper;
import com.mju.video.mapper.CollectMapper;
import com.mju.video.mapper.RabbishImageMapper;
import com.mju.video.mapper.RabbishMapper;
import com.mju.video.service.CollectImageService;
import com.mju.video.service.RabbishImageService;
import com.mju.video.utils.Base64Util;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class CollectImageServiceImpl implements CollectImageService {
    @Autowired
    private CollectImageMapper collectImageMapper;
    @Autowired
    private RabbishImageMapper rabbishImageMapper;
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private RabbishMapper rabbishMapper;
    @Override
    @Transactional
    public boolean save(CollectImage collectImage) {
        int effNum = collectImageMapper.insert(collectImage);
        if (effNum>0){
            return true;
        }
        return false;
    }

    @Override
    public int selectCountByCollectId(Integer collectId) {
        Example example = new Example(CollectImage.class);
        example.createCriteria().andEqualTo("collectId",collectId);
        int count = collectImageMapper.selectCountByExample(example);
        return count;
    }

    @Override
    @Transactional
    public void deleteCollectImageByCollectId(Integer collectId) {
        Example example = new Example(CollectImage.class);
        example.createCriteria().andEqualTo("collectId", collectId);
        collectImageMapper.deleteByExample(example);
    }

    @Override
    public List<CollectImage> findImagesByCollectId(Integer collectId) {
        Example example = new Example(CollectImage.class);
        example.createCriteria().andEqualTo("collectId", collectId).andEqualTo("state", 0);
        List<CollectImage> collectImageList = collectImageMapper.selectByExample(example);
        if (collectImageList!=null&&collectImageList.size()>0){
            return collectImageList;
        }
        return null;
    }

    @Override
    @Transactional
    public void delImageById(Integer imgId) throws IOException {
//      CollectImage collectImage = collectImageMapper.selectByPrimaryKey(imgId);
//      collectImage.setState(-1);
//      collectImageMapper.updateByPrimaryKeySelective(collectImage);
        //删除collectImage图片
        CollectImage collectImage = collectImageMapper.selectByPrimaryKey(imgId);
        File collectImageFile;
        if (Base64Util.isWin()){
            collectImageFile = new File("D:"+collectImage.getUrl());
        }else {
            collectImageFile = new File(collectImage.getUrl());
        }
        FileUtils.forceDelete(collectImageFile);
        collectImageMapper.deleteByPrimaryKey(imgId);
        //删除rabbishImage图片
        RabbishImage rabbishImage = rabbishImageMapper.selectByPrimaryKey(imgId);
        File rabbishImageFile;
        if (Base64Util.isWin()){
            rabbishImageFile = new File("D:"+rabbishImage.getUrl());
        }else {
            rabbishImageFile = new File(rabbishImage.getUrl());
        }
        FileUtils.forceDelete(rabbishImageFile);
        rabbishImageMapper.deleteByPrimaryKey(imgId);
    }

    @Override
    public CollectImage findOne(Integer imageId) {
        CollectImage collectImage = collectImageMapper.selectByPrimaryKey(imageId);
        Collect collect = collectMapper.selectByPrimaryKey(collectImage.getCollectId());
        collectImage.setCollect(collect);
        Rabbish rabbish = rabbishMapper.selectByPrimaryKey(collectImage.getRabbishId());
        collectImage.setRabbish(rabbish);
        return collectImage;
    }

    @Override
    public void update(String srcUrl,String destUrl, Integer destCollectId) {
        Example example = new Example(CollectImage.class);
        example.createCriteria().andEqualTo("url", srcUrl);
        List<CollectImage> collectImageList = collectImageMapper.selectByExample(example);
        CollectImage collectImage = collectImageList.get(0);
        collectImage.setUrl(destUrl);
        collectImage.setCollectId(destCollectId);
        collectImageMapper.updateByPrimaryKeySelective(collectImage);
    }

    @Override
    public void deleteCollectImageByRabbishId(Integer rabbishId) {
        Example example = new Example(CollectImage.class);
        example.createCriteria().andEqualTo("rabbishId", rabbishId);
        collectImageMapper.deleteByExample(example);
    }

    @Override
    public List<CollectImage> findImagesByRabbishId(Integer rabbishId) {
        Example example = new Example(CollectImage.class);
        example.createCriteria().andEqualTo("rabbishId", rabbishId);
        List<CollectImage> collectImageList = collectImageMapper.selectByExample(example);
        return collectImageList;
    }
}
