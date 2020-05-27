package com.mju.video.service.impl;

import cn.hutool.core.io.FileUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mju.video.domain.Collect;
import com.mju.video.domain.CollectImage;
import com.mju.video.domain.Rabbish;
import com.mju.video.domain.RabbishImage;
import com.mju.video.mapper.CollectImageMapper;
import com.mju.video.mapper.RabbishImageMapper;
import com.mju.video.mapper.RabbishMapper;
import com.mju.video.service.CollectImageService;
import com.mju.video.service.RabbishImageService;
import com.mju.video.service.RabbishService;
import com.mju.video.utils.Base64Util;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class RabbishServiceImpl implements RabbishService {
    @Autowired
    private RabbishMapper rabbishMapper;
    @Autowired
    private RabbishImageMapper rabbishImageMapper;
    @Autowired
    private CollectImageMapper collectImageMapper;
    @Autowired
    private RabbishImageService rabbishImageService;
    @Autowired
    private CollectImageService collectImageService;
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
    public boolean addRabbish(Rabbish rabbish) {
        int effNum = rabbishMapper.insert(rabbish);
        if (effNum > 0) {
            String imagePath = Base64Util.baseImagePath();
            String collectImagePath = imagePath +"rubbish/"+rabbish.getId();
            FileUtil.mkdir(collectImagePath);
            return true;
        }
        return false;
    }

    @Override
    public boolean delRabbish(Rabbish rabbish) {
        File collectImageFile = null;
        File rabbishImageFile = null;
        try {
            List<CollectImage> collectImageList = collectImageService.findImagesByRabbishId(rabbish.getId());
            if (collectImageList==null){
                //删除采集信息
                rabbishMapper.deleteByPrimaryKey(rabbish.getId());
                String imagePath = Base64Util.baseImagePath();
                String rubbishImagePath = imagePath + "rubbish/" + rabbish.getId();
                File dir = new File(rubbishImagePath);
                FileUtils.forceDelete(dir);
                return true;
            }
            //删除collectImage图片
            for (CollectImage collectImage:collectImageList){
                if (Base64Util.isWin()){
                    collectImageFile = new File("D:"+collectImage.getUrl());
                }else {
                    collectImageFile = new File(collectImage.getUrl());
                }
                FileUtils.forceDelete(collectImageFile);
            }
            collectImageService.deleteCollectImageByRabbishId(rabbish.getId());
            List<RabbishImage> rabbishImageList = rabbishImageService.findImagesByRabbishId(rabbish.getId());
            //删除rabbishImage图片
            for (RabbishImage rabbishImage:rabbishImageList){
                if (Base64Util.isWin()){
                    rabbishImageFile = new File("D:"+rabbishImage.getUrl());
                }else {
                    rabbishImageFile = new File(rabbishImage.getUrl());
                }
                FileUtils.forceDelete(rabbishImageFile);
            }
            rabbishImageService.delectRabbishImageByRabbishId(rabbish.getId());
            //删除垃圾类别
            rabbishMapper.deleteByPrimaryKey(rabbish);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

    @Override
    public PageInfo<Rabbish> getList(Integer pageNum, Integer pageSize) {
        PageInfo<Rabbish> pageInfo = null;
        try {
            PageHelper.startPage(pageNum, pageSize);
            Example example = new Example(Rabbish.class);
            Example.OrderBy orderBy = example.orderBy("id");
            orderBy.desc();
            List<Rabbish> rabbishList = rabbishMapper.selectByExample(example);
            for (Rabbish rabbish : rabbishList) {
                example = new Example(RabbishImage.class);
                example.createCriteria().andEqualTo("rabbishId", rabbish.getId()).andEqualTo("state", 0);
                int count = rabbishImageMapper.selectCountByExample(example);
                rabbish.setCount(count);
            }
            pageInfo = new PageInfo<>(rabbishList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    @Override
    public PageInfo<Rabbish> getRubbishByCondition(Integer pageNum, Integer pageSize, String likeName, Integer operate, Integer total) {
        Example example = new Example(Rabbish.class);
        Example.OrderBy orderBy = example.orderBy("id");
        orderBy.desc();
        Example.Criteria criteria = example.createCriteria();
        PageInfo<Rabbish> pageInfo = null;
        if (StringUtils.isNotBlank(likeName)){
            criteria.andLike("name", "%"+ likeName +"%");
        }
//        if (timestamp!=null && timestamp.length>0){
//            Date start = new Date(timestamp[0]);
//            Date end = new Date(timestamp[1]+=86400000);
//            criteria.andBetween("createTime", start, end);
//        }
        List<Rabbish> allCollectList1 = rabbishMapper.selectByExample(example);
        Page<Object> page = PageHelper.startPage(pageNum, pageSize);
        List<Rabbish> allCollectList = rabbishMapper.selectByExample(example);
        if (allCollectList==null){
            return null;
        }
        for (Rabbish rabbish : allCollectList1) {
            example = new Example(RabbishImage.class);
            example.createCriteria().andEqualTo("rabbishId", rabbish.getId()).andEqualTo("state", 0);
            int count = rabbishImageMapper.selectCountByExample(example);
            rabbish.setCount(count);
        }
        for (Rabbish rabbish : allCollectList) {
            example = new Example(RabbishImage.class);
            example.createCriteria().andEqualTo("rabbishId", rabbish.getId()).andEqualTo("state", 0);
            int count = rabbishImageMapper.selectCountByExample(example);
            rabbish.setCount(count);
        }
//        判断是否传总计范围
        List<Rabbish> rabbishList = null;
        if (operate!=null && total!=null){
            rabbishList = new ArrayList<>();
            switch (operate){
                case 0:
                    for (Rabbish rabbish:allCollectList1){
                        if (rabbish.getCount()<total){
                            rabbishList.add(rabbish);
                        }
                    }
                    break;
                case 1:
                    for (Rabbish rabbish:allCollectList1){
                        if (rabbish.getCount()<=total){
                            rabbishList.add(rabbish);
                        }
                    }
                    break;
                case 2:
                    for (Rabbish rabbish:allCollectList1){
                        if (rabbish.getCount()==total){
                            rabbishList.add(rabbish);
                        }
                    }
                    break;
                case 3:
                    for (Rabbish rabbish:allCollectList1){
                        if (rabbish.getCount()>total){
                            rabbishList.add(rabbish);
                        }
                    }
                    break;
                case 4:
                    for (Rabbish rabbish:allCollectList1){
                        if (rabbish.getCount()>=total){
                            rabbishList.add(rabbish);
                        }
                    }
                    break;
                default:
                    break;
            }

            int toIndex = pageSize*pageNum > rabbishList.size()?rabbishList.size():pageNum*pageSize;
            pageInfo = new PageInfo<>(rabbishList.subList((pageNum-1)*pageSize, toIndex));
            pageInfo.setTotal(rabbishList.size());
            return pageInfo;
        }
        pageInfo = new PageInfo<>(allCollectList);
        return pageInfo;
    }

    @Override
    public Rabbish findOneByName(String name) {
        Example example = new Example(Rabbish.class);
        example.createCriteria().andEqualTo("name", name);
        List<Rabbish> rabbishList = rabbishMapper.selectByExample(example);
        if (rabbishList != null && rabbishList.size() > 0) {
            return rabbishList.get(0);

        }
        return null;
    }
}
