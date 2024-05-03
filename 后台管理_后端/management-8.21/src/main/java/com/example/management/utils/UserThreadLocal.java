package com.example.management.utils;


import com.example.management.pojo.User;

/**
 * @author 14158
 */
public class UserThreadLocal {

    private UserThreadLocal(){}

    private static final ThreadLocal<User> LOCAL = new ThreadLocal<>();
    public static User get(){
        return LOCAL.get();
    }

    public static void put(User sysUser){
        LOCAL.set(sysUser);
    }
    public static void remove(){
        LOCAL.remove();
    }
}
