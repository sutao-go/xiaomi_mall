
package com.imooc.service.impl;

import com.imooc.mapper.UserCenterMapper;
import com.imooc.service.UserCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserCenterImpl implements UserCenterService {
    @Autowired
    private UserCenterMapper userCenterMapper;
    @Override
    public Boolean changePassword(String userName,String passWord){
            System.out.println(userName+"密码"+passWord);
            userCenterMapper.changePassword(userName,passWord);
            int a = userCenterMapper.changePassword(userName,passWord);
            System.out.println("请查看结果"+a);
            return true;
    }


}

