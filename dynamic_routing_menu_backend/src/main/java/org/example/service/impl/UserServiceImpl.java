package org.example.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.PageResult;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean login(User user){

       List<User> users = userMapper.selectList(null);
       for (User item: users) {
            if (Objects.equals(user.getUserName(), item.getUserId()))
                 return Objects.equals(item.getPassword(), user.getPassword());
       }

       return false;
    }

    @Override
    public List<User> findUsers() {
         return userMapper.selectList(null);
    }

    @Override
    public PageResult<User> findUsersByPage(long pageNum, long pageSize) {
        // MyBatis-Plus 分页查询
        Page<User> page = new Page<>(pageNum, pageSize);
        userMapper.selectPage(page, null);
        // 封装返回结果
        return new PageResult<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                page.getPages(),
                page.getRecords());
    }

    @Override
    public User findUserById(String id) {
         return userMapper.selectById(id);
    }

    @Override
    public boolean addUser(User user) {
        // 2. 检查ID是否已存在
        if (this.getById(user.getUserId()) != null) {
            throw new IllegalArgumentException("用户已存在");
        }
        if (checkUserInfo(user))
            return false;
        // 4. 保存到数据库
        return this.save(user);
    }

    @Override
    public boolean deleteUser(String userId) {
        // 1. 检查菜单是否存在（可选）
        User user = userMapper.selectById(userId);
        if (user == null) {
            return false;
        }
        return userMapper.deleteById(userId) > 0;
    }

    @Override
    public boolean updateUser(User user) {
        // 2. 检查ID是否已存在
        if (this.getById(user.getUserId()) == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (checkUserInfo(user))
            return false;
        return  userMapper.updateById(user) > 0;
    }

    public boolean checkUserInfo(User user) {
        // 1. 验证ID必须存在
        if (user.getUserId() == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        //密码不为空
        if (user.getPassword() == null) {
            throw new IllegalArgumentException("用户密码不能为空");
        }

        // 3. 其他业务验证
        if (!StringUtils.hasText(user.getUserName()) || !StringUtils.hasText(user.getUserName())) {
            throw new IllegalArgumentException("模型名称不能为空");
        }
        return false;
    }
}
