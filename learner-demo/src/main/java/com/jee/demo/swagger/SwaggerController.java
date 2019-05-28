package com.jee.demo.swagger;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * @Description:  http://localhost:8080/swagger3/index.html
 * @Auther: lyd
 * @Date: 2019/5/19
 * @Version:v1.0
 */
@RestController
@RequestMapping(value = "/api/v1")
public class SwaggerController {

    @GetMapping(value = "/user/{userId}")
    public Long getUserById(@PathVariable Long userId){
        System.out.println(userId);
        return userId;
    }

    @GetMapping(value = "/user/list")
    public String list(String username){
        System.out.println(username);
        return username;
    }

    @PostMapping(value = "/user/save")
    public User save(@RequestBody User user){
        System.out.println(user.getUserId() + user.getUsername());
        return user;
    }

    @PostMapping(value = "/user/file")
    public String file(MultipartFile headImg){
        System.out.println(headImg.getOriginalFilename());
        System.out.println(headImg.getSize());
        return headImg.getOriginalFilename();
    }

    @DeleteMapping(value = "/user/delete")
    public List<Long> delete(Long[] ids){
        System.out.println(Arrays.asList(ids));
        return Arrays.asList(ids);
    }



}

