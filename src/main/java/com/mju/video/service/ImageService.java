package com.mju.video.service;

import com.github.pagehelper.PageInfo;
import com.mju.video.domain.Image;

import java.util.List;

public interface ImageService {
    boolean save(Image image);

    PageInfo<Image> findAllNoApproval(int pageNum, Integer pageSize);

    List<Image> findImagesByCollectId(Integer collectId);

    boolean deleteImageByCollectId(Integer collectId);

    void delImageById(Integer imgId);

    int selectCountByExample(Integer collectId);

    Image findOne(Integer imageId);
}
