package com.jee.demo.resttemp;

import com.jee.common.result.ResultInfo;
import com.jee.demo.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/22
 * @Version:v1.0
 */
@RestController
@RequestMapping("/rest")
public class RestTemplateDemo {

    @Autowired
    RestTemplate restTemplate;

    private String IP_ADDR = "localhost:8080";

    /**
     * http简单请求GET  getForObject
     * @param userId
     * @return
     */
    @GetMapping(value = "/user/info/{id}")
    public User getUser(@PathVariable("id") int userId){
        String url = "http://"+IP_ADDR+"/api/user/get/{id}";
        User user = restTemplate.getForObject(url, User.class, userId);
        User user2 = restTemplate.getForEntity(url, User.class, userId).getBody();

        System.out.println(user.getName());
        System.out.println(user2.getName());
        return user;
    }

    /**
     * http多参数请求GET  getForObject
     * @param userId
     * @return
     */
    @GetMapping(value = "/user/info/{id}/{name}")
    public User getUser(@PathVariable("id") int userId, @PathVariable String name){
        String url =  "http://"+IP_ADDR+"/api/user/get/{id}/{name}";
        Map<String, Object> params = new HashMap<>(5);
        params.put("id", userId);
        params.put("name", name);
        //方式一
        User user = restTemplate.getForObject(url, User.class, params);
        //方式二
        ResponseEntity<User> responseEntity = restTemplate.getForEntity(url, User.class, params);
        User user2 = responseEntity.getBody();

        System.out.println(user.getName());
        System.out.println(user2.getName());
        return user;
    }

    /**
     * http请求POST  postForObject
     * http获取服务器响应头和状态码
     * @return
     */
    @PostMapping(value = "/user/save")
    public User postUser(){
        String url =  "http://"+IP_ADDR+"/api/user/save";
        User user = new User(1,"jee-post");

        //请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<User> httpEntity = new HttpEntity<>(user, headers);
        //User apiUser = restTemplate.postForObject(url, httpEntity, User.class);

        ResponseEntity<User> responseEntity = restTemplate.postForEntity(url, httpEntity, User.class);
        //http获取服务器响应头和状态码
        User apiUser = responseEntity.getBody();
        HttpHeaders respHeaders = responseEntity.getHeaders();
        List<String> success = respHeaders.get("success");
        int status = responseEntity.getStatusCodeValue();
        System.out.println(user.getId());
        System.out.println(status);
        return apiUser;
    }

    /**
     * http请求DELETE  delete
     * @return
     */
    @DeleteMapping(value = "/user/delete/{id}")
    public void deleteUser(@PathVariable("id") String userId){
        String url =  "http://"+IP_ADDR+"/api/user/delete/{id}";
        restTemplate.delete(url, userId);
    }

    /**
     * http获取服务器响应头和状态码
     * @return
     */
    public void exchangeUser(){
        String url =  "http://"+IP_ADDR+"/api/user/exchange";
        int userId = 1;
        User user = new User(userId,"jee-exchange");

        //请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<User> httpEntity = new HttpEntity<>(user, headers);
        //GET
        ResponseEntity<User> getResponseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, User.class, userId);
        //POST
        ResponseEntity<User> postResponseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, User.class);
    }

    /**
     * http请求GET  返回复杂结果
     * @param userId
     * @return
     */
    @GetMapping(value = "/user/result/{id}")
    public ResultInfo getResultUser(@PathVariable("id") int userId){
        String url = "http://"+IP_ADDR+"/api/user/result/{id}";
        /**
         * 注意：ResultInfo必须有无参构造，否则会序列化失败
         */
        ResultInfo<User> apiResultUser = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<ResultInfo<User>>() {}, userId).getBody();
        return apiResultUser;
    }

    /**
     * http请求POST  返回复杂结果
     * @return
     */
    @GetMapping(value = "/user/result/save")
    public ResultInfo postResultUser(){
        String url = "http://"+IP_ADDR+"/api/user/result/save";
        User user = new User(1, "jee");
        ResultInfo<User> apiResultUser = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(user), new ParameterizedTypeReference<ResultInfo<User>>() {}).getBody();
        return apiResultUser;
    }


    public <T> ResponseEntity<T> post(String url, Object reqBody, ParameterizedTypeReference<T> responseType){
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity(reqBody), responseType);
    }

    public <T> ResponseEntity<T> get(String url, ParameterizedTypeReference<T> responseType){
        return restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, responseType);
    }

    /*public String getUsername(Long id){
        String url = "http://user-UserService/getUsername?id=" + id;
        //这里返回的是整个结果，不是我们要的  {"code":0,"msg":"OK","result":"test-username"}
        //String body = rest.get(url, new ParameterizedTypeReference<String>() {}).getBody();
        //ParameterizedTypeReference需要深度泛型
        RestResponse<String> response = rest.get(url, new ParameterizedTypeReference<RestResponse<String>>() {}).getBody();
        return response.getResult();
    }*/

}

