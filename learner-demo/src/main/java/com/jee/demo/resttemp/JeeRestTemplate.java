package com.jee.demo.resttemp;

import com.jee.demo.api.User;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: restTemplate工具类
 * @Auther: jeeLearner
 * @Date: 2019/5/24
 * @Version:v1.0
 */
@Component("jeeRestTemplate")
public class JeeRestTemplate {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 普通http请求
     */
    public <T> ResponseEntity<T> get(String url, Class<T> clazz) {
        return restTemplate.getForEntity(url, clazz);
    }
    public <T> ResponseEntity<T> get(String url,Class<T> clazz, Object... uriVariables){
        return restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, clazz, uriVariables);
    }
    public <T> ResponseEntity<T> get(String url, Class<T> clazz, Map<String, ?> params){
        return restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, clazz, params);
    }
    public <T> ResponseEntity<T> get(String url, ParameterizedTypeReference<T> responseType, Object... uriVariables){
        return restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, responseType, uriVariables);
    }
    public <T> ResponseEntity<T> get(String url, HttpEntity httpEntity, Class<T> clazz, Object... uriVariables){
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, clazz, uriVariables);
    }
    public <T> ResponseEntity<T> get(String url, HttpEntity httpEntity, ParameterizedTypeReference<T> responseType, Object... uriVariables){
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType, uriVariables);
    }
    public <T> ResponseEntity<T> post(String url,HttpEntity httpEntity, Class<T> clazz, Object... uriVariables){
        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, clazz, uriVariables);
    }
    public <T> ResponseEntity<T> post(String url, HttpEntity httpEntity, ParameterizedTypeReference<T> responseType, Object... uriVariables){
        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, responseType,uriVariables);
    }



    /**
     *  rest架构下，往往返回的是整个结果，不是我们要的！  {"code":0,"msg":"OK","data":"test-username"}
     *  String body = rest.get(url, new ParameterizedTypeReference<String>() {}).getBody();
     *  ParameterizedTypeReference需要深度泛型
     *
     *  注意：ResultInfo必须有无参构造，否则会序列化失败
     */
    public <T> ResponseEntity<T> post(String url, Object reqBody, ParameterizedTypeReference<T> responseType){
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(reqBody), responseType);
    }
    public <T> ResponseEntity<T> get(String url, ParameterizedTypeReference<T> responseType){
        return restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, responseType);
    }

    /**
     * 设置请求头
     *      可根据实际情况更改参数
     * 知识：
     *   Content-Type 实体头  代表发送端（客户端|服务器）发送的实体数据的数据类型
     *         互联网媒体类型；也叫做MIME类型，在Http协议消息头中，使用Content-Type来表示具体请求中的媒体类型信息。
     *   Accept   请求头  代表发送端（客户端）希望接受的数据类型
     */
    public HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("token", "4fd8a04ef06c5484bfa4112d3d86074b");
        //设置请求的类型及编码
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //设置接受的数据类型
        httpHeaders.add("Accept", "application/json");
        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.ALL);
        httpHeaders.setAccept(acceptableMediaTypes);
        return httpHeaders;
    }

    /**
     * HttpEntity
     */
    public <T> HttpEntity getHttpEntity(T t) {
        HttpHeaders headers = new HttpHeaders();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, String> headers2= new LinkedMultiValueMap<String, String>();
        //  也支持中文
        headers2.add("username", "用户名");
        headers2.add("password", "123456");
        return new HttpEntity(t, headers2);
    }

}

