package com.mju.video.service;

import com.mju.video.domain.CollectImage;

import java.io.IOException;
import java.util.List;

public interface CollectImageService {
    /**
     * 添加采集图片
     * @param collectImage
     * @return
     */
    boolean save(CollectImage collectImage);

    /**
     * 查询此次采集的图片总数
     * @param collectId
     * @return
     */
    int selectCountByCollectId(Integer collectId);

    /**
     * 删除采集的图片通过collectId
     * @param collectId
     * @return
     */
    void deleteImageByCollectId(Integer collectId);

    /**
     * 查找该次采集下的所有图片
     * @param collectId
     * @return
     */
    List<CollectImage> findImagesByCollectId(Integer collectId);

    /**
     * 删除图片通过主键id
     * @param imgId
     */
    void delImageById(Integer imgId) throws IOException;

    /**
     * 查找图片通过主键id
     * @param imageId
     * @return
     */
    CollectImage findOne(Integer imageId);
}
