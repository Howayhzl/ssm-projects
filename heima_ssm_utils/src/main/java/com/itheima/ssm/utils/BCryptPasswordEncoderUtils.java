package com.itheima.ssm.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderUtils {

    private static BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();

    public static String encodePassWord(String passWord){
      return   bCryptPasswordEncoder.encode(passWord);
    }


    public static void main(String[] args) {
        String s = encodePassWord("123");//$2a$10$nn1MrTyq.txOviWIRhOZQudZlbEhxlWiSVqq98X9tUgUcF2FwyXya
        //$2a$10$5fwD0TTVZYLGNtP8OEr9H.4m56a7wJ7WPj8joIYA50Wv3vkABupCm
        System.out.println(s);
    }
}
