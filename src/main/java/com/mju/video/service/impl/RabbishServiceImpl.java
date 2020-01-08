package com.mju.video.service.impl;

import com.mju.video.domain.CollectImage;
import com.mju.video.domain.Rabbish;
import com.mju.video.domain.RabbishImage;
import com.mju.video.mapper.CollectImageMapper;
import com.mju.video.mapper.RabbishImageMapper;
import com.mju.video.mapper.RabbishMapper;
import com.mju.video.service.CollectImageService;
import com.mju.video.service.RabbishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class RabbishServiceImpl implements RabbishService {
    @Autowired
    private RabbishMapper rabbishMapper;
    @Autowired
    private RabbishImageMapper rabbishImageMapper;
    @Autowired
    private CollectImageMapper collectImageMapper;
    @Override
    public List<Rabbish> findAll() {
        List<Rabbish> rabbishList = rabbishMapper.selectAll();
        for (Rabbish rabbish : rabbishList) {
            Example example = new Example(RabbishImage.class);
            example.createCriteria().andEqualTo("rabbishId", rabbish.getId()).andEqualTo("state", 0);
            int count = rabbishImageMapper.selectCountByExample(example);
            rabbish.setCount(count);
        }

        return rabbishList;
    }

    @Override
    public Rabbish findOne(Integer id) {
        return rabbishMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateRabbish(Rabbish rabbish) {
        int effNum = rabbishMapper.updateByPrimaryKeySelective(rabbish);
        if (effNum > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(Rabbish rabbish) {
        int effNum = rabbishMapper.insert(rabbish);
        if (effNum > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean del(Rabbish rabbish) {
        Example example = new Example(RabbishImage.class);
        example.createCriteria().andEqualTo("rabbishId", rabbish.getId());
        rabbishImageMapper.deleteByExample(example);
        Example example2 = new Example(CollectImage.class);
        example.createCriteria().andEqualTo("rabbishId", rabbish.getId());
        collectImageMapper.deleteByExample(example2);
        int effNum = rabbishMapper.deleteByPrimaryKey(rabbish);
        if (effNum > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Rabbish> findByLikeName(String likeName) {
        Example example = new Example(Rabbish.class);
        example.createCriteria().andLike("name", "%"+likeName+"%");
        List<Rabbish> rabbishList = rabbishMapper.selectByExample(example);
        for (Rabbish rabbish : rabbishList) {
            example = new Example(RabbishImage.class);
            example.createCriteria().andEqualTo("rabbishId", rabbish.getId()).andEqualTo("state", 0);
            int count = rabbishImageMapper.selectCountByExample(example);
            rabbish.setCount(count);
        }
        return rabbishList;
    }
}
