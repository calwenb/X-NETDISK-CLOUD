package com.wen.user.service.impl;

import com.wen.common.pojo.Result;
import com.wen.common.pojo.User;
import com.wen.user.mapper.UserMapper;
import com.wen.user.service.FileStoreService;
import com.wen.user.service.UserService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.文
 */
@GlobalTransactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;


    @Resource
    FileStoreService storeService;


    /**
     * 查询全部用户
     *
     * @return 全部用户
     */
    @Override
    public List<User> queryUsers() {
        return userMapper.queryUsers();
    }

    /**
     * 增加全部用户
     *
     * @param user
     * @return 修改状态
     */
    @Override
    public int addUser(User user) {
        return 0;
    }

    /**
     * 修改全部用户
     *
     * @param
     * @return
     */
    @Override
    public int deleteUser(int userId) {
        return 0;
    }

    /**
     * 修改全部用户
     *
     * @param user
     * @return 修改状态
     */
    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    /**
     * 登录
     *
     * @param loginName
     * @param pwd
     * @return
     */
    @Override
    public User login(String loginName, String pwd) {
        return userMapper.login(loginName, pwd);
    }


    @Override
    public Map<String, Object> register(String userName, String loginName, String pwd) {
        HashMap<String, Object> rs = new HashMap<>(2);
        User user = new User(-1, userName, loginName, pwd, 2, "", "", "/#", new Date());
        try {
            userMapper.addUser(user);
        } catch (Exception e) {
            rs.put("error", "注册失败，账号已存在");
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return rs;
        }
        Result result = storeService.initStore(user.getId());
        if (result.getCode() != 200) {
            //回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            rs.put("error", "初始化用户仓库失败");
        }
        rs.put("user", user);
        return rs;
    }


    @Override
    public User getUserById(int userId) {
        return userMapper.getUserById(userId);
    }
}
