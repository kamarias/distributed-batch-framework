package io.github.kamarias.dbf.system.gateway;

import io.github.kamarias.dbf.system.dto.UserDto;

public interface UserStoreGateway {


    /**
     * 通过电话号码获取用户信息
     *
     * @param phone 电话号码
     * @return 返回查找到的用户
     */
    UserDto selectUserByPhone(String phone);

    /**
     * 通过邮箱号获取用户信息
     *
     * @param email 邮箱
     * @return 返回查找到的用户
     */
    UserDto selectUserByEmail(String email);


    /**
     * 通过用户名获取用户信息
     *
     * @param userName 用户名
     * @return 返回查找到的用户
     */
    UserDto selectUserByAccount(String userName);


    boolean phoneExists(String phone);

    boolean accountExists(String phone);

    boolean emailExists(String phone);


    boolean creatUser(UserDto userDto);


}
