package com.mju.video.service.impl;

import cn.hutool.core.io.FileUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mju.video.domain.Collect;
import com.mju.video.domain.CollectImage;
import com.mju.video.domain.RabbishImage;
import com.mju.video.mapper.CollectImageMapper;
import com.mju.video.mapper.CollectMapper;
import com.mju.video.service.CollectImageService;
import com.mju.video.service.CollectService;
import com.mju.video.service.RabbishImageService;
import com.mju.video.utils.Base64Util;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private CollectImageMapper collectImageMapper;
    @Autowired
    private CollectImageService collectImageService;
    @Autowired
    private RabbishImageService rabbishImageService;

    @Override
    public PageInfo<Collect> findAll(Integer pageNum, Integer pageSize) {
        PageInfo<Collect> pageInfo = null;
        try {
            PageHelper.startPage(pageNum, pageSize);
            Example example = new Example(Collect.class);
            Example.OrderBy orderBy = example.orderBy("id");
            orderBy.desc();
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

    @Override
    public boolean addOne(Collect collect) {
        collect.setCreateTime(new Date());
        int effNum = collectMapper.insert(collect);
        if (effNum > 0) {
            String imagePath = Base64Util.baseImagePath();
            String collectImagePath = imagePath + "collect/" + collect.getId();
            FileUtil.mkdir(collectImagePath);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delCollect(Integer collectId) {
        File collectImageFile = null;
        File rabbishImageFile = null;
        try {
            List<CollectImage> collectImageList = collectImageService.findImagesByCollectId(collectId);
            if (collectImageList==null){
                //删除采集信息
                collectMapper.deleteByPrimaryKey(collectId);
                String imagePath = Base64Util.baseImagePath();
                String collectImagePath = imagePath + "collect/" + collectId;
                File dir = new File(collectImagePath);
                FileUtils.forceDelete(dir);
                return true;
            }
            //删除collectImage图片
            for (CollectImage collectImage : collectImageList) {
                if (Base64Util.isWin()){
                    collectImageFile= new File("D:" + collectImage.getUrl());
                }else {
                    collectImageFile = new File(collectImage.getUrl());
                }
                FileUtils.forceDelete(collectImageFile);
            }
            collectImageService.deleteCollectImageByCollectId(collectId);
            List<RabbishImage> rabbishImageList = rabbishImageService.findImagesByCollectId(collectId);
            //删除rabbishImage图片
            for (RabbishImage rabbishImage : rabbishImageList) {
                if (Base64Util.isWin()){
                    rabbishImageFile= new File("D:" + rabbishImage.getUrl());
                }else {
                    rabbishImageFile = new File(rabbishImage.getUrl());
                }
                FileUtils.forceDelete(rabbishImageFile);
            }
            rabbishImageService.delectRabbishImageByCollectId(collectId);
            //删除采集信息
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
        Example example = new Example(Collect.class);
        Example.OrderBy orderBy = example.orderBy("id");
        orderBy.desc();
        List<Collect> collectList = collectMapper.selectByExample(example);
        if (collectList != null && collectList.size() > 0) {
            for (Collect collect : collectList) {
                int count = collectImageService.selectCountByCollectId(collect.getId());
                collect.setCount(count);
            }
            return collectList;
        }
        return null;
    }

    @Override
    public PageInfo<Collect> findAllByLikeName(Integer pageNum, Integer pageSize, String searchName) {

        Example example = new Example(Collect.class);
        example.createCriteria().andLike("name", "%" + searchName + "%");
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

    @Override
    public Collect findOneByName(String name) {
        Example example = new Example(Collect.class);
        example.createCriteria().andEqualTo("name", name);
        List<Collect> collectList = collectMapper.selectByExample(example);
        if (collectList != null && collectList.size() > 0) {
            return collectList.get(0);

        }
        return null;
    }

//    @Override
//    public List<Collect> findByLikeName(String likeName, long[] timestamp, Integer operate, Integer total) {
//        Example example = new Example(Collect.class);
//        //判断是否传名称
//        if (StringUtils.isNotBlank(likeName)){
//            example.createCriteria().andLike("name", "%" + likeName + "%");
//        }
//        List<Collect> allCollectList = collectMapper.selectByExample(example);
//        if (allCollectList==null){
//            return null;
//        }
//        for (Collect collect : allCollectList) {
//            example = new Example(CollectImage.class);
//            example.createCriteria().andEqualTo("collectId", collect.getId()).andEqualTo("state", 0);
//            int count = collectImageMapper.selectCountByExample(example);
//            collect.setCount(count);
//        }
//        List<Collect> collectList = null;
//        //判断是否传时间范围
//        if (timestamp.length>0){
//            collectList = new ArrayList<>();
//            for (Collect collect:allCollectList){
//                if (collect.getCreateTime().getTime() >=  timestamp[0] &&
//                        collect.getCreateTime().getTime() <= timestamp[1]){
//                    collectList.add(collect);
//                }
//            }
//        }
//        //判断是否传总计范围
//        List<Collect> newCollectList = null;
//        if (total!=null&&collectList!=null&&collectList.size()>0){
//            newCollectList = new ArrayList<>();
//            switch (operate){
//                case 0:
//                    for (Collect collect:collectList){
//                        if (collect.getCount()<total){
//                            newCollectList.add(collect);
//                        }
//                    }
//                    break;
//                case 1:
//                    for (Collect collect:collectList){
//                        if (collect.getCount()<=total){
//                            newCollectList.add(collect);
//                        }
//                    }
//                    break;
//                case 2:
//                    for (Collect collect:collectList){
//                        if (collect.getCount()==total){
//                            newCollectList.add(collect);
//                        }
//                    }
//                    break;
//                case 3:
//                    for (Collect collect:collectList){
//                        if (collect.getCount()>total){
//                            newCollectList.add(collect);
//                        }
//                    }
//                    break;
//                case 4:
//                    for (Collect collect:collectList){
//                        if (collect.getCount()>=total){
//                            newCollectList.add(collect);
//                        }
//                    }
//                    break;
//            }
//        }
//        if (allCollectList!=null && allCollectList.size()>0){
//            if (collectList!=null && allCollectList.size()>0){
//                if (newCollectList!=null && newCollectList.size()>0){
//                    return newCollectList;
//                }
//            }
//        }
//        return null;
//    }

    @Override
    public PageInfo<Collect> getCollectByCondition(Integer pageNum,Integer pageSize,String likeName, long[] timestamp, Integer operate, Integer total) {
        Example example = new Example(Collect.class);
        Example.OrderBy orderBy = example.orderBy("id");
        orderBy.desc();
        Example.Criteria criteria = example.createCriteria();
        PageInfo<Collect> pageInfo = null;
        if (StringUtils.isNotBlank(likeName)){
            criteria.andLike("name", "%"+ likeName +"%");
        }if (timestamp!=null && timestamp.length>0){
            Date start = new Date(timestamp[0]);
            Date end = new Date(timestamp[1]+=86400000);
            criteria.andBetween("createTime", start, end);
        }
        List<Collect> allCollectList1 = collectMapper.selectByExample(example);
        Page<Object> page = PageHelper.startPage(pageNum, pageSize);
        List<Collect> allCollectList = collectMapper.selectByExample(example);
        if (allCollectList==null){
            return null;
        }
        for (Collect collect : allCollectList1) {
            example = new Example(CollectImage.class);
            example.createCriteria().andEqualTo("collectId", collect.getId()).andEqualTo("state", 0);
            int count = collectImageMapper.selectCountByExample(example);
            collect.setCount(count);
        }
        for (Collect collect : allCollectList) {
            example = new Example(CollectImage.class);
            example.createCriteria().andEqualTo("collectId", collect.getId()).andEqualTo("state", 0);
            int count = collectImageMapper.selectCountByExample(example);
            collect.setCount(count);
        }
//        判断是否传总计范围
        List<Collect> collectList = null;
        if (operate!=null && total!=null){
            collectList = new ArrayList<>();
            switch (operate){
                case 0:
                    for (Collect collect:allCollectList1){
                        if (collect.getCount()<total){
                            collectList.add(collect);
                        }
                    }
                    break;
                case 1:
                    for (Collect collect:allCollectList1){
                        if (collect.getCount()<=total){
                            collectList.add(collect);
                        }
                    }
                    break;
                case 2:
                    for (Collect collect:allCollectList1){
                        if (collect.getCount()==total){
                            collectList.add(collect);
                        }
                    }
                    break;
                case 3:
                    for (Collect collect:allCollectList1){
                        if (collect.getCount()>total){
                            collectList.add(collect);
                        }
                    }
                    break;
                case 4:
                    for (Collect collect:allCollectList1){
                        if (collect.getCount()>=total){
                            collectList.add(collect);
                        }
                    }
                    break;
                default:
                    break;
            }
//            int totalPage = (collectList.size() + pageSize - 1)/pageSize;
//            int fromIndex =
            int toIndex = pageSize*pageNum > collectList.size()?collectList.size():pageNum*pageSize;
            pageInfo = new PageInfo<>(collectList.subList((pageNum-1)*pageSize, toIndex));
            pageInfo.setTotal(collectList.size());
        return pageInfo;
    }
        pageInfo = new PageInfo<>(allCollectList);
        return pageInfo;
}}
