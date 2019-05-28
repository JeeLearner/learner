package com.jee.demo.junit;

import com.jee.common.result.ResultInfo;
import com.jee.demo.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/20
 * @Version:v1.0
 */
@RestController
@RequestMapping(value = "/junit/user2")
public class UserController2 {

    @Autowired
    SysUserService userService;

    @GetMapping("/get/{id}")
    public ResultInfo getUser(@PathVariable("id") int userId){
        System.out.println("---"+userId);
        User user = new User(1, "jee");
        ResultInfo<User> success = ResultInfo.success(user);
        System.out.println(success);
        return ResultInfo.success(user);
    }

    @GetMapping("/modelView")
    public ModelAndView modelView(){
        ModelAndView modelAndView = new ModelAndView("/success.btl");
        return modelAndView;
    }

    @GetMapping("/model")
    public Model model(Model model){
        model.addAttribute("person", "jee");
        return model;
    }

    @GetMapping("/forward")
    public void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("index.html").forward(request, response);
    }

    @GetMapping("/redirect")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("index.html");
    }

    @GetMapping("/content")
    public String content() {
        return "hello world";
    }

}

