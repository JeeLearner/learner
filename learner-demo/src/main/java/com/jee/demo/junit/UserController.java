package com.jee.demo.junit;

import com.jee.demo.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/20
 * @Version:v1.0
 */
@RestController
@RequestMapping(value = "/junit/user")
public class UserController {

    @Autowired
    SysUserService userService;

    @GetMapping("/get/{id}")
    public int getUser(@PathVariable("id") int userId){
        System.out.println("---"+userService.getCredit(userId));
        return userService.getCredit(userId);
    }

    @PostMapping("/post/{id}")
    public int PostUser(@PathVariable("id") int userId){
        System.out.println("---"+userService.getCredit(userId));
        return userService.getCredit(userId);
    }

    @GetMapping("/get/{id}/{name}")
    public int getUser(@PathVariable("id") int userId, String message){
        System.out.println("---"+userService.getCredit(userId)+"---" +message);
        return userService.getCredit(userId);
    }

    @GetMapping("/get/{id}/{name}/2")
    public int getUser(@PathVariable("id") int userId, String message, String[] jobs){
        System.out.println("---"+userService.getCredit(userId)+"---" +message + "jobs:"+ Arrays.asList(jobs));
        return userService.getCredit(userId);
    }

    @GetMapping("/session")
    public int getUser(HttpSession session){
        System.out.println(session.getAttribute("sessionName"));
        return userService.getCredit(1);
    }

    @GetMapping("/cookie")
    public int getUser(HttpServletRequest request){
        Arrays.stream(request.getCookies()).forEach(p -> {
            System.out.println("---cookie:" + p.getName()+"=" + p.getValue());
        });
        return userService.getCredit(1);
    }

    @GetMapping("/json")
    public int getUser(@RequestBody User user){
        System.out.println(user.getId()+":"+user.getName());
        return userService.getCredit(1);
    }

    @GetMapping("/header")
    public int getUserHeader(HttpServletRequest request){
        String value = request.getHeader("header1");
        System.out.println("header1----"+value);
        return userService.getCredit(1);
    }

    /**
     * 测试文件上传
     * @param file
     * @throws IOException
     */
    @RequestMapping("/upload")
    public void upload(MultipartFile file) throws IOException {
        file.transferTo(new File("D:/test.xlsx"));
        System.out.println("---"+file.getOriginalFilename());
        //return userService.getCredit(userId);
    }

}

