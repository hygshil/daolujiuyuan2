package com.xiexin.interceptor;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
登陆的拦截，为了拦截3端的登录接口
1）后台管理---session
2）顾客---token
3）工程师---暂定
*/
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private JedisPool jedisPool;

    //方法调用之前的拦截
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //判断web端有没有登录，即判断他是否携带了token
        //如果携带了token，则需要判断token是否是在redis中的
        //如果不在redis中，则是一个过期的或者前端伪造的token，则不允许登录，让前端重新登录
        //如果这个token是ok的，name校验他的jwt token是否过期(非必要的)
        //过期了不让登录，并删除redis中的key
        //排除完毕后 就可以登录的.....

        //那么，后台httpServletRequest这个参数就可以收取前端传来的token，那么现在问题在于前端把token放在哪里？才可以正常发送到后台
        //答案：可以放在ajax的请求头中
        System.out.println("拦截器生效了  return false 是拦截了，return true 是放行了");
        //后台获取token
        String token = httpServletRequest.getHeader("token");//获取请求头
        System.out.println("token = " + token);
        //获取了token---看token有没有在redis当中，单点登录！！基于redis+token的
        //因为要用到手机号，之前手机号是存在jwt的信息中的，需要解析出来，生成的不同，解析的方式也不同
        if (token != null) {
            long tokentime = JWT.decode(token).getExpiresAt().getTime();   //获取token的过期时间  time = 1633681115000
            System.out.println("tokentime = " + tokentime);
            String s = JWT.decode(token).getAudience().get(0); //获取手机号
            System.out.println("s = " + s.substring(0, 11));
            String phoneNum = s.substring(0, 11);
            httpServletRequest.setAttribute("phoneNum", phoneNum);  //将手机号放入到request作用域中
            //做单点登录，每次使用新的设备（手机，浏览器，平板，等等）登录，都会诞生一个token值，这个token值每次登陆都不一样
            //抓住这个特点可以做单点登录
            //1.登录后的请求需要查看自己的token和redis中的token是否一样，如果一样是同一个设备，如果不一样，提示您需要重新登陆，因为你的设备在ip xxx登录了
            String redisKey = phoneNum + "token";
            String customerRedisToken = jedisPool.getResource().get(redisKey);
            if (!customerRedisToken.equals(token)) {
                System.out.println("token不相等");
                PrintWriter writer = httpServletResponse.getWriter();
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.setContentType("application/json; charset=utf-8");
                httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//                response.setHeader("Vary", "Origin");

//                    // Access-Control-Max-Age
//                    response.setHeader("Access-Control-Max-Age", "3600");

                // Access-Control-Allow-Credentials
                httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

                // Access-Control-Allow-Methods
                httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");

                // Access-Control-Allow-Headers
                httpServletResponse.setHeader("Access-Control-Allow-Headers",
                        "Origin, X-Requested-With, Content-Type, Accept, token");
                Map codeMap = new HashMap();
                codeMap.put("code", 50001);
                codeMap.put("msg", "因为您的设备在ip xxx登录了或者你的账户名密码不对");  //作业1：给前端提示设备在xxx地方登陆了，饭后保存一个在日志数据库中，id，手机号，ip，时间
                String jsonString = JSON.toJSONString(codeMap);
                System.out.println("jsonString = " + jsonString);
                writer.print(jsonString);
                writer.flush();
                writer.close();
                return false;
            }

            //判断token是否过期
            //输出现在时间的long值
            long nowtime = new Date().getTime();
            long subTime = tokentime - nowtime;
            if (subTime < 0) {
                //过期的日子减去现在的时间是负值，证明token过期了
                //告知前端发送json，让他重新登陆
                PrintWriter writer = httpServletResponse.getWriter();
                responseToken(httpServletResponse);
                Map codeMap = new HashMap();
                codeMap.put("code", 50002);
                codeMap.put("msg", "您的登录信息过期，请重新登录");  //作业1：给前端提示设备在xxx地方登陆了，饭后保存一个在日志数据库中，id，手机号，ip，时间
                String jsonString = JSON.toJSONString(codeMap);
                System.out.println("jsonString = " + jsonString);
                writer.print(jsonString);
                writer.flush();
                writer.close();
                return false;
            }

        }
        //jedisPool.getResource().get("手机号+token");
        return true;
    }

    //方法执行中的拦截
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    //方法调用之后的拦截
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private void responseToken(HttpServletResponse httpServletResponse){
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//                response.setHeader("Vary", "Origin");

//                    // Access-Control-Max-Age
//                    response.setHeader("Access-Control-Max-Age", "3600");

        // Access-Control-Allow-Credentials
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

        // Access-Control-Allow-Methods
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");

        // Access-Control-Allow-Headers
        httpServletResponse.setHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept, token");
    }
}
