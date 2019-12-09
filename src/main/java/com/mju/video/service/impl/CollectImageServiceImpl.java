package com.mju.video.service.impl;

import com.mju.video.domain.Collect;
import com.mju.video.domain.CollectImage;
import com.mju.video.domain.Rabbish;
import com.mju.video.mapper.CollectImageMapper;
import com.mju.video.mapper.CollectMapper;
import com.mju.video.mapper.RabbishMapper;
import com.mju.video.service.CollectImageService;
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
    public void deleteImageByCollectId(Integer collectId) {
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
        CollectImage collectImage = collectImageMapper.selectByPrimaryKey(imgId);
        File file = new File("D:"+collectImage.getUrl());
        FileUtils.forceDelete(file);
        collectImageMapper.deleteByPrimaryKey(imgId);
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
}
