package com.cskaoyan.hackernews2.dao;

import com.cskaoyan.hackernews2.bean.ActiveUser;
import com.cskaoyan.hackernews2.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface Userdao {

@Select("select * from users where username=#{user.username} and password=#{user.password}")
    public User findUsernameAndPasswd(@Param("user") User user);

@Select("select * from users where username =#{username}")
    User queryUsername(@Param("username") String username);
@Insert("insert into users values(#{user.username},#{user.password},#{user.headUrl},null)")
    int insertUser(@Param("user") User user);
@Select("select * from users where id=#{id}")
    User findUserById(@Param("id") int i);
}
