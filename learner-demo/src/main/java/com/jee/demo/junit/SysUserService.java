package com.jee.demo.junit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserService{

    @Autowired
    CreditSystemService creditSystemService;

    public int getCredit(int userId){
        return creditSystemService.getUserCredit(userId);
    }

}