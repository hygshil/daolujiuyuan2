package com.xiexin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiexin.bean.Customer;
import com.xiexin.bean.CustomerDTO;
import com.xiexin.bean.CustomerExample;
import com.xiexin.service.CustomerService;
import com.xiexin.util.AliSMSUtil;
import com.xiexin.util.JwtToToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@CrossOrigin(origins = "*")  //注意：*是来自所有的域名请求，也可以细化
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired(required = false)
    private CustomerService customerService;
    @Autowired
    private JedisPool jedisPool;

    //发送验证码
    @RequestMapping("/sendCodeNum")
    public Map sendCodeNum(String phoneNum) {
        Map codeMap = new HashMap();
        //1.在发送验证码之前，随机创建6个随机数字的验证码
        int randomNum = new Random().nextInt(999999);   //0-999999 如果i<100000+100000
        if (randomNum < 100000) {
            randomNum = randomNum + 100000;
        }
        //1.1在发送验证码之前，需要向redis中查询该手机有没有验证码存在，如果存在就直接从redis中读取，而不从阿里云发送，可以减少成本
        Boolean b = jedisPool.getResource().exists(phoneNum);
        if (b) {
            codeMap.put("code", 0);
            codeMap.put("msg", "验证码已存在,请去短信中查询");
            return codeMap;
        } else {
            //2.接收到前端传过来的手机号：phoneNum，对其调用阿里云的发送短信的工具类去发送验证码
            AliSMSUtil.sendMsg(phoneNum, randomNum);
            //3.发送之后，将手机号当做redis key，验证码当做redis value存入到redis数据库中
            String setex = jedisPool.getResource().setex(phoneNum, 120, String.valueOf(randomNum));
            System.out.println("setex = " + setex);
            jedisPool.getResource().persist(phoneNum);  //注意：这里设置成-1，在线上环境要删除！！
            //4.将验证码发送给前端
            if ("OK".equals(setex)) {
                codeMap.put("code", 0);
                codeMap.put("msg", "发送成功");
                // codeMap.put("data",randomNum);  //线上不能把验证码通过json数据返回给前端，容易被人恶意利用，验证码只能该手机号可以看到
                return codeMap;
            } else {
                codeMap.put("code", 500);
                codeMap.put("msg", "发送失败");
                return codeMap;
            }
        }
        //作业1：后台为了减少验证码多次发送，每个手机号120s内如果连续点击验证码，则不调用阿里云发的
        //直接发送redis中的值

        //作业2：前端也需要拦截多次发送，在发送验证码的按钮上，设置定时器，当点击发送验证码后，按钮变为待机时！！
        //待120s后，显示正常发送验证码按钮
    }

    //校验手机号和验证码
    @RequestMapping("/customerLogin")
    public Map customerLogin(String phoneNum, String codeNum) {
        Map codeMap = new HashMap();
        //1.根据前端传来的手机号和验证码来喝redis中的数据做对比
        String redisCodeNum = jedisPool.getResource().get(phoneNum);  //redis中的验证码
        if (codeNum.equals(redisCodeNum)) {
            //相等，登录成功,需要给顾客返回一个jwt，同时把jwt的key放入到redis中
            JwtToToken jwtToToken = new JwtToToken();
            CustomerDTO jwt = jwtToToken.createJwt(phoneNum);  //注意前后端分离没有session，使用jwt比较容易轻松的做出单点登录，基于jwt+redis的单点登录
            //把token存入到redis当中去
            jedisPool.getResource().set(phoneNum+"token",jwt.getAccessToken());

            codeMap.put("code", 0);
            codeMap.put("msg", "登录成功");
            codeMap.put("data", jwt);
            return codeMap;
        } else {
            //不相等，登录失败
            codeMap.put("code", 400);
            codeMap.put("msg", "登录失败");
            return codeMap;
        }
    }

    //vue校正验证码
    @RequestMapping("/customerLoginByAxios")  // "/api/customer/customerLoginByAxios"
    public Map customerLoginByAxios(@RequestBody Map map) {
        System.out.println("map = " + map);
        Map codeMap = new HashMap();
        // 1. 根据前端 传来的手机号 和 验证码 来和redis 中的数据做对比
        String redisCodeNum = jedisPool.getResource().get((String) map.get("phoneNum")); // redis中的 验证码
        if (map.get("codeNum").equals(redisCodeNum)) {
            // 相等, 登录成功
            codeMap.put("code", 0);
            codeMap.put("msg", "登录成功");
            return codeMap;
        } else {
            // 不相等, 登录失败
            codeMap.put("code", 400);
            codeMap.put("msg", "登录失败");
            return codeMap;
        }
    }

    @RequestMapping("/getMoney")  //"/api/customer/getMoney"
    public Map getMoney(double gongSiLng, double gongSiLat, double customerLng, double customerLat) {
        double money = customerService.getMoney(gongSiLng, gongSiLat, customerLng, customerLat);
        //money应该是2位数的小数
        DecimalFormat df = new DecimalFormat("#.##");
        String format = df.format(money);
        Map codeMap = new HashMap();
        codeMap.put("code", 0);
        codeMap.put("msg", "请求成功");
        codeMap.put("data", format);
        return codeMap;
    }

    //增
// 后端订单增加 -- 针对layui的 针对前端传 json序列化的
    @RequestMapping("/insert")
    public Map insert(@RequestBody Customer customer) { // orders 对象传参, 规则: 前端属性要和后台的属性一致!!!
        Map map = new HashMap();
        int i = customerService.insertSelective(customer);
        if (i > 0) {
            map.put("code", 200);
            map.put("msg", "添加成功");
            return map;
        } else {
            map.put("code", 400);
            map.put("msg", "添加失败,检查网络再来一次");
            return map;
        }
    }


    // 删
// 删除订单  根据 主键 id 删除
    @RequestMapping("/deleteById")
    public Map deleteById(@RequestParam(value = "id") Integer id) {
        Map responseMap = new HashMap();
        int i = customerService.deleteByPrimaryKey(id);
        if (i > 0) {
            responseMap.put("code", 200);
            responseMap.put("msg", "删除成功");
            return responseMap;
        } else {
            responseMap.put("code", 400);
            responseMap.put("msg", "删除失败");
            return responseMap;
        }
    }

    // 批量删除
    @RequestMapping("/deleteBatch")
    public Map deleteBatch(@RequestParam(value = "idList[]") List<Integer> idList) {
        for (Integer integer : idList) {
            this.deleteById(integer);
        }
        Map responseMap = new HashMap();
        responseMap.put("code", 200);
        responseMap.put("msg", "删除成功");
        return responseMap;
    }


    // 改
    // 修改订单
    @RequestMapping("/update")
    public Map update(@RequestBody Customer customer) {
        Map map = new HashMap();
        int i = customerService.updateByPrimaryKeySelective(customer);
        if (i > 0) {
            map.put("code", 200);
            map.put("msg", "修改成功");
            return map;
        } else {
            map.put("code", 400);
            map.put("msg", "修改失败,检查网络再来一次");
            return map;
        }
    }

    // 查--未分页
    // 全查
    @RequestMapping("/selectAll")
    public Map selectAll() {
        List<Customer> customers = customerService.selectByExample(null);
        Map responseMap = new HashMap();
        responseMap.put("code", 0);
        responseMap.put("msg", "查询成功");
        responseMap.put("customers", customers);
        return responseMap;
    }

    // 查-- 查询+自身对象的查询 + 分页
// 分页查询
    @RequestMapping("/selectAllByPage")
    public Map selectAllByPage(Customer customer, @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
                               @RequestParam(value = "limit", required = true) Integer pageSize) {
        // 调用service 层   , 适用于 单表!!!
        PageHelper.startPage(page, pageSize);
        CustomerExample example = new CustomerExample();
        CustomerExample.Criteria criteria = example.createCriteria();

//    if (null!=customer.getYYYYYYYY()&&!"".equals(customer.getYYYYYYY())){
//         criteria.andPhoneEqualTo(customer.getPhone());   // 1
//    }

        List<Customer> customersList = customerService.selectByExample(example);
        PageInfo pageInfo = new PageInfo(customersList);
        long total = pageInfo.getTotal();
        Map responseMap = new HashMap();
        responseMap.put("code", 0);
        responseMap.put("msg", "查询成功");
        responseMap.put("pageInfo", pageInfo);
        responseMap.put("count", total);
        return responseMap;
    }


}
