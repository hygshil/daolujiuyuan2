package com.xiexin.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/*
* 阿里云的短信发送的工具类
* */
public class AliSMSUtil {
    public static void sendMsg(String phoneNum,Integer codeNum){
        DefaultProfile profile = DefaultProfile.getProfile("cn-qingdao", "LTAI5tM3zGxQ7YoQ7afNgzKL", "LsIpTyKYcLFalwPWkQErQIMi7qbMZX");
        /** use STS Token
         DefaultProfile profile = DefaultProfile.getProfile(
         "<your-region-id>",           // The region ID
         "<your-access-key-id>",       // The AccessKey ID of the RAM account
         "<your-access-key-secret>",   // The AccessKey Secret of the RAM account
         "<your-sts-token>");          // STS Token
         **/
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phoneNum);  //此手机号要从前端传进来
        request.putQueryParameter("SignName", "快速指定文件夹清理"); //这个签名要和自己阿里云中的模板一模一样
        request.putQueryParameter("TemplateCode", "SMS_168825399"); //这个是模板，也要和自己阿里云中的模板一模一样

        //随机的六位数字
//        int i = new Random().nextInt(999999);   //0-999999 如果i<100000+100000
//        if(i<100000){
//            i=i+100000;
//        }
//        System.out.println("i = " + i);
        request.putQueryParameter("TemplateParam", "{\"code\":\""+codeNum+"\"}"); //这个是验证码，要自己写一个随机数字


        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
