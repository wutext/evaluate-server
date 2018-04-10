package com.litsoft.evaluateserver.util;

import com.litsoft.evaluateserver.entity.User;
import com.mysql.jdbc.StringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.assertj.core.util.Preconditions;
import org.thymeleaf.expression.Strings;

import java.security.MessageDigest;
import java.util.UUID;

public class MdUtil {

    public static String getUniqueString() {

        UUID uuid = UUID.randomUUID();
        String s = UUID.randomUUID().toString();
        return s;
    }

    public static String getMD5(String pass, String salt) throws Exception {

        String MD5 = "";
        String str = pass+salt;
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bytes = str.getBytes();
        byte[] digest = md5.digest(bytes);

        for (int i = 0; i < digest.length; i++) {
            //摘要字节数组中各个字节的"十六进制"形式.
            int j = digest[i];
            j = j & 0x000000ff;
            String s1 = Integer.toHexString(j);

            if (s1.length() == 1) {
                s1 = "0" + s1;
            }
            MD5 += s1;
        }
        return MD5;
    }

    public static User md5Password(String username, String password){
        Preconditions.checkArgument(!StringUtils.isNullOrEmpty(username),"username不能为空");
        Preconditions.checkArgument(!StringUtils.isNullOrEmpty(password),"password不能为空");
        SecureRandomNumberGenerator secureRandomNumberGenerator=new SecureRandomNumberGenerator();
        String salt= secureRandomNumberGenerator.nextBytes().toHex();
        //组合username,两次迭代，对密码进行加密
        String password_cipherText= new Md5Hash(password,username+salt,2).toHex();
        User user=new User();
        user.setPassword(password_cipherText);
        user.setSalt(salt);
        user.setUsername(username);
        return user;
    }
}
