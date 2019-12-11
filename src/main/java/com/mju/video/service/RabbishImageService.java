package com.mju.video.service;

import com.mju.video.domain.RabbishImage;

import java.io.IOException;
import java.util.List;

public interface RabbishImageService {
    boolean save(RabbishImage rabbishImage);

    List<RabbishImage> findAllByRabbishId(Integer id);

    RabbishImage findOne(Integer imageId);

    void delectRabbishImageById(Integer imgId) throws IOException;

    void delectRabbishImageByCollectId(Integer collectId);
}
