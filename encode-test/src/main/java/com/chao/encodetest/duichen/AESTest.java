package com.chao.encodetest.duichen;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

/**
 * @Author by wangwenchao
 * @Description:
 * @Create: 2019/9/17
 */
public class AESTest {
    private static String src="Hello AES";


    public static void jdkAES(){
        try {
            //生成key
            KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");
            keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] key1 = secretKey.getEncoded();

            //key转换为密钥
            Key key2 = new SecretKeySpec(key1, "AES");

            //加密
            Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5padding");
            cipher.init(Cipher.ENCRYPT_MODE, key2);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("jdkAES加密: "+ Hex.encodeHexString(result));  //转换为十六进制

            //解密
            cipher.init(Cipher.DECRYPT_MODE, key2);
            result = cipher.doFinal(result);
            System.out.println("jdkAES解密: "+new String(result));  //转换字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void bcAES(){
        try {
            Security.addProvider(new BouncyCastleProvider());

            //生成key
            KeyGenerator keyGenerator=KeyGenerator.getInstance("AES", "BC");
            keyGenerator.getProvider();
            keyGenerator.init(128);      //显示指定密钥长度
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] key1 = secretKey.getEncoded();

            //key转换为密钥
            Key key2 = new SecretKeySpec(key1, "AES");

            //加密
            Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5padding");
            cipher.init(Cipher.ENCRYPT_MODE, key2);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("jdkAES加密: "+Hex.encodeHexString(result));  //转换为十六进制

            //解密
            cipher.init(Cipher.DECRYPT_MODE, key2);
            result = cipher.doFinal(result);
            System.out.println("jdkAES解密: "+new String(result));  //转换字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        jdkAES();
        bcAES();
    }
}
