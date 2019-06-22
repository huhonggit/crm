package com.crm.auth.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class ShiroPasswordUtil {

//    private static final String encodeRules = "Rc6Pi3LkmOdYIYG";

    public static String encode(String content,String encodeRules){
        //加密方式
        String hashAlgorithmName = "MD5";
        ByteSource salt = ByteSource.Util.bytes(encodeRules);
        //加密1024次
        int hashIterations = 1024;
        SimpleHash hash = new SimpleHash(hashAlgorithmName,content,salt,hashIterations);
        return hash.toString();
    }

    public static ByteSource getSalt(String encodeRules){
        ByteSource salt = ByteSource.Util.bytes(encodeRules);
        return salt;
    }



    public static void main(String[] args) {
        String token = encode( "123","test");
        System.out.println(token);
    }
}
