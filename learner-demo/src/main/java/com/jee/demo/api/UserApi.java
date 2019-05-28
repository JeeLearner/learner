package com.jee.demo.api;

import com.jee.common.result.ResultInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/24
 * @Version:v1.0
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserApi {


    @GetMapping("/get/{id}")
    public User getUser(@PathVariable("id") int userId){
        System.out.println("---"+userId);
        User user = new User(userId, "jee");
        return user;
    }

    @GetMapping("/result/{id}")
    public ResultInfo getResultUser(@PathVariable("id") int userId){
        System.out.println("---"+userId);
        User user = new User(userId, "resultUser:jee");
        return ResultInfo.success(user);
    }

    @GetMapping("/get/{id}/{name}")
    public User getUser(@PathVariable("id") int userId, @PathVariable String name){
        System.out.println("---"+userId);
        User user = new User(userId, name);
        return user;
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User postUser(@RequestBody User user){
        user.setName("api:" + user.getName());
        return user;
    }

    @PostMapping("/result/save")
    public ResultInfo postResultUser(@RequestBody User user){
        user.setName("result:" + user.getName());
        return ResultInfo.success(user);
    }

    @DeleteMapping("/delete/{id}")
    public User deleteUser(@PathVariable("id") int userId){
        User user = new User(userId, "jee已删除");
        System.out.println("---"+user.getName());
        return user;
    }
}

