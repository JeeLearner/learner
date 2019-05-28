package com.jee.demo.junit;

import org.springframework.stereotype.Service;

/**
 * @Description: 积分系统service
 * @Auther: lyd
 * @Date: 2019/5/20
 * @Version:v1.0
 */
@Service
public class CreditSystemService {

    public int getUserCredit(int userId){
        return 0;
    }

    public boolean addCedit(int userId, int score){
        return true;
    }
}

