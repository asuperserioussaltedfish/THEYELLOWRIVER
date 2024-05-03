package com.example.management.handler;


import com.example.management.utils.UserThreadLocal;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author 14158
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    /**
     * 执行方法之前执行
     * @param request 请求
     * @param response 响应
     * @param handler 处理器
     * @return 判断
     * @throws Exception 抛出异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            //判断类型（controller）
            return true;
        }

        if (handlerMethod.hasMethodAnnotation(Fish.class)) {
            return true;
        }



        return true;





//        //查找token
//        String token = request.getHeader("Authorization");
//        System.out.println(token);
//        log.info("=================request start===========================");
//        String requestUri = request.getRequestURI();
//        log.info("request uri:{}", requestUri);
//        log.info("request method:{}", request.getMethod());
//        log.info("token:{}", token);
//        log.info("=================request end===========================");
//        //判断token是否为空
//        if (token == null) {
//            System.out.println(2);
//            Result result = Result.fail(ErrorCode.USER_NOT_LOGGED_IN.getCode(), "未登录");
//            //转成JSON格式
//            response.setContentType("application/json;charset=utf-8");
//            response.getWriter().print(JSON.toJSONString(result));
//            return false;
//        }
//        String s = redisTemplate.opsForValue().get("TOKEN_" + token);
//        User user =JSON.parseObject(s,User.class);
//        System.out.println(user);
//        if (user == null) {
//            System.out.println(1);
//            Result result = Result.fail(ErrorCode.USER_NOT_LOGGED_IN.getCode(), "未登录");
//            response.setContentType("application/json;charset=utf-8");
//            response.getWriter().print(JSON.toJSONString(result));
//            return false;
//        }
//        //登录验证成功，放行
//        UserThreadLocal.put(user);
//        return true;
    }

    /**
     *删除UserThreadLocal中的user
     * @param request 请求
     * @param response 回应
     * @param handler 处理器
     * @param ex 异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserThreadLocal.remove();
    }

}
