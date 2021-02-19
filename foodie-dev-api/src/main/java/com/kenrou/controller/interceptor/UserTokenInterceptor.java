package com.kenrou.controller.interceptor;

import com.kenrou.utils.IMOOCJSONResult;
import com.kenrou.utils.JsonUtils;
import com.kenrou.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class UserTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisOperator redisOperator;
    public static final String REDIS_USER_TOKEN = "redis_user_token";
    /**
     * 拦截请求，在controller调用之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        System.out.println("进入到拦截器...");
        String userId = request.getHeader("headerUserId");
        String token  = request.getHeader("headerUserToken");
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(token)) {
            String redisToken = redisOperator.get(REDIS_USER_TOKEN + ":" + userId);
            if (StringUtils.isNotBlank(redisToken)) {
                if (token.equals(redisToken)) {
                    return true;
                } else {
//                    System.out.println("账号在异地登录...");
                    returnErrorResponse(response, IMOOCJSONResult.errorMsg("账号在异地登录"));
                    return false;
                }
            } else {
//                System.out.println("请登录");
                returnErrorResponse(response, IMOOCJSONResult.errorMsg("请登录"));
                return false;
            }
        } else {
//            System.out.println("请登录");
            returnErrorResponse(response, IMOOCJSONResult.errorMsg("请登录"));
            return false;
        }
        // false：请求被拦截，被驳回
        // true：请求通过
//        return false;
    }

    /**
     * 请求访问controller之后，渲染视图之前
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 请求访问controller之后，渲染视图之后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public void returnErrorResponse(HttpServletResponse response, IMOOCJSONResult result) {

        OutputStream out = null;

        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
