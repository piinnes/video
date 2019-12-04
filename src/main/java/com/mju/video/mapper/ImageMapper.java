package com.mju.video.mapper;

import com.mju.video.domain.Image;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ImageMapper extends Mapper<Image> {
}
