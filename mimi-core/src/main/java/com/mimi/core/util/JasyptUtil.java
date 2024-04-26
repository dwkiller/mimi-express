package com.mimi.core.util;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置文件加解密工具类
 * https://blog.csdn.net/qq_41617261/article/details/121448939
 */
@Component
public class JasyptUtil {
    private static String password;

    @Value("${jasypt.encryptor.password}")
    public static void setPassword(String password) {
        JasyptUtil.password = password;
    }

    private static final String PBEWITHMD5ANDDES = "PBEWithMD5AndDES";

    private static final String PBEWITHHMACSHA512ANDAES_256 = "PBEWITHHMACSHA512ANDAES_256";

    public static void main(String[] args) {
        JasyptUtil.password = "+oE67TSCE/j7==";
        // 加密
        String password = encyptPwd("rs2laMCX8YwtTT1QmtFyrLIhKT7kr7");
        System.out.println(password);
        System.out.println(decryptPwd(password));
    }

    /**
     * 加密方法
     *
     * @param value 需要加密的值
     */
    public static String encyptPwd(String value) {
        // 1. 创建加解密工具实例
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        // 2. 加解密配置
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm(PBEWITHMD5ANDDES);
        // 3. 为减少配置文件的书写，以下都是 Jasyp 3.x 版本，配置文件默认配置
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        String result = encryptor.encrypt(value);
        return result;
    }

    /**
     * 解密方法
     *
     * @param value 需要解密的值
     */
    public static String decryptPwd(String value) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm(PBEWITHMD5ANDDES);
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        String result = encryptor.decrypt(value);
        return result;
    }
}