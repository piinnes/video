package com.mju.video.interceptor;

import com.mju.video.domain.Collect;
import com.mju.video.domain.User;
import com.mju.video.mapper.CollectMapper;
import com.mju.video.service.CollectService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class privilegeInterceptor implements HandlerInterceptor {
    @Autowired
    private CollectService collectService;
    private static final Logger logger = LoggerFactory.getLogger(privilegeInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("---------------------开始进入请求地址拦截----------------------------");
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)){
            response.sendRedirect("/collect");
            return false;
        }
        Integer collectId = Integer.valueOf(id);
        Collect collect = collectService.findOne(collectId);
        if (collectId!=null&&collect!=null){
            return true;
        }
        response.sendRedirect("/collect");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
