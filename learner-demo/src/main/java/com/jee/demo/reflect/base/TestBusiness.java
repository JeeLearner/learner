package com.jee.demo.reflect.base;

import com.jee.demo.api.User;

import java.util.ArrayList;
import java.util.List;

public class TestBusiness implements IBusiness{
    @Override
	public int getPrice(String good){
		return good.equals("yifu")?10:20;
	}

	@Override
	public List<User> list(User user) {
		List<User> list = new ArrayList<>(2);
		for (int i=0; i<2; i++){
			list.add(new User(i, "name" +i));
		}
		return list;
	}
}