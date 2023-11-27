package io.ads;


import io.ads.modules.analysis.dto.WuyuAnalysisResultDTO;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateTest {

    @Resource
    private RestTemplate restTemplate;
    @Test
    public void testAIAnalysisApi() {
        final String url = "https://chatglm2.tocmcc.cn:443";


        //restTemplate 会根据 params 的具体类型，调用合适的 HttpMessageConvert 将请求参数写到请求体 body 中，并在请求头中添加合适的 content-type；
        // 也会根据 responseType 的类型（本列子中是 JSONObject），设置 head 中的 accept 字段，当响应返回的时候再调用合适的 HttpMessageConvert 进行响应转换

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("prompt", "类型#个人建议*主题#品德评定*等级#优*主题#奖惩记录*等级#差*主题#德育课程*等级#中*主题#实践活动*等级#中*主题#网络文化*等级#中*主题#人际关系*等级#中");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(requestBody, headers);

        WuyuAnalysisResultDTO data = restTemplate.postForObject(url, httpEntity, WuyuAnalysisResultDTO.class);


        assert data != null;
        System.out.println(data.getResponse());
    }

}
