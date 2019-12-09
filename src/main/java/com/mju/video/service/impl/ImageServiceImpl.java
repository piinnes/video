package com.mju.video.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mju.video.domain.Collect;
import com.mju.video.domain.Image;
import com.mju.video.domain.Rabbish;
import com.mju.video.mapper.CollectMapper;
import com.mju.video.mapper.ImageMapper;
import com.mju.video.mapper.RabbishMapper;
import com.mju.video.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private RabbishMapper rabbishMapper;
    @Override
    @Transactional
    public boolean save(Image image) {
        image.setCreateTime(new Date());
        int effNum = imageMapper.insert(image);
        if (effNum>0){
            return true;
        }
        return false;
    }

    @Override
    public PageInfo<Image> findAllNoApproval(int pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Image> imageList = imageMapper.selectAll();
        //返回的是一个PageInfo,包含了分页的所有信息
        PageInfo<Image> pageInfo = new PageInfo<>(imageList);
//      Example example = new Example(Image.class);
//      Example.Criteria criteria = example.createCriteria();
//      criteria.andEqualTo("state", 0);
//      List<Image> imageList = imageMapper.selectByExample(example);
        return pageInfo;
    }

    @Override
    public List<Image> findImagesByCollectId(Integer collectId) {
        Example example = new Example(Image.class);
        example.createCriteria().andEqualTo("collectId",collectId)
                                .andEqualTo("state", 0);
        List<Image> imageList = imageMapper.selectByExample(example);
        if (imageList!=null&&imageList.size()>0){
            return imageList;
        }
        return null;
    }

    @Override
    public boolean deleteImageByCollectId(Integer collectId) {
        int effNum = 0;
        try {
            Example example = new Example(Image.class);
            example.createCriteria().andEqualTo("collectId",collectId);
            Image image = new Image();
            image.setCollectId(-1);
            effNum = imageMapper.updateByExampleSelective(image, example);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (effNum > 0) {

            return true;
        }
        return false;
    }

    @Override
    public void delImageById(Integer imgId) {
        Image image = imageMapper.selectByPrimaryKey(imgId);
        image.setState(-1);
        imageMapper.updateByPrimaryKey(image);
    }

    @Override
    public int selectCountByExample(Integer collectId) {
        Example example = new Example(Image.class);
        example.createCriteria().andEqualTo("collectId", collectId).andEqualTo("state",0);
        return imageMapper.selectCountByExample(example);
    }

    @Override
    public Image findOne(Integer imageId) {
        Image image = imageMapper.selectByPrimaryKey(imageId);
        Collect collect = collectMapper.selectByPrimaryKey(image.getCollectId());
        image.setCollect(collect);
        Rabbish rabbish = rabbishMapper.selectByPrimaryKey(image.getRab_id());
        image.setRabbish(rabbish);
        return image;
    }
}
