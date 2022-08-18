package com.px.blog.util;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class MD5 {
    public static String encryptToMD5(String str){
        return DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
    }

    public static void main(String[] args) {
        System.out.println(encryptToMD5("v8v8v8v8"));
    }
}
