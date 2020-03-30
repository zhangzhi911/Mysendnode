package com.zhangzhi;


import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.zhangzhi.service.insertNode;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
public class sendNode {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    public insertNode insertNode;

    @RequestMapping("/send")
    public String sendnote(String str) {

        if (str.length() != 11) {
            return "请输入正确的手机号!";
        }
        // 短信应用SDK AppID
//        int appid = 1400332571; // 1400开头
        int appid = 1400343441; // 1400开头

        // 短信应用SDK AppKey
        String appkey = "2b3cac35963c8062d2fb54f5c54f56e9";
//        String appkey = "ca72f260795a2cd79b25da53911f4db4";

        // 需要发送短信的手机号码
        String[] phoneNumbers = {"18662746291"};

        // 短信模板ID，需要在短信应用中申请
        // NOTE: 这里的模板ID`7839`只是一个示例，
        // 真实的模板ID需要在短信控制台中申请
//        int templateId = 564425;
        int templateId = 566829;

        // 签名
        // NOTE: 这里的签名"腾讯云"只是一个示例，
        // 真实的签名需要在短信控制台中申请，另外
        // 签名参数使用的是`签名内容`，而不是`签名ID`
//        String smsSign = "002睿智";
        String smsSign = "高博军的公众号";

        try {
            int num = (int) ((Math.random() * 9 + 1) * 100000);
            String ran = String.valueOf(num);
            String[] params = {ran, "2"};
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", str,
                    templateId, params, smsSign, "", "");
            redisTemplate.opsForValue().set(str, "用户" + str + "验证码是:" + ran + "时间是:" + new Date(), 60, TimeUnit.SECONDS);
            Object o = redisTemplate.opsForValue().get(str);
            System.out.println("这是redis" + o);
            System.out.println(result + "完成了");
            if (result.errMsg.equals("OK")) {
                return "尊敬的客户：" + str + "您好,验证码已发送到您的手机，请尽快查收";

            } else {
                return "尊敬的客户：" + str + "您好,今日您的手机号已到上限，请改日再来";
            }

        } catch (HTTPException e) {
            // HTTP 响应码错误
            System.out.println("响应码错误");
            e.printStackTrace();
            return "响应码错误";

        } catch (JSONException e) {
            System.out.println(" 解析错误");
            // JSON 解析错误
            e.printStackTrace();
            return "解析错误";

        } catch (IOException e) {
            System.out.println(" 网络 IO 错误");
            // 网络 IO 错误
            e.printStackTrace();
            return "网络 IO 错误";
        }
    }


}
