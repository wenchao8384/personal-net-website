package com.chao.encodetest.duichen;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

/**
 * @Author by wangwenchao
 * @Description:
 * @Create: 2019/9/17
 */
public class ThreeDESTest {
    private static String src="Hello 3DES";

    public static void jdkDES(){
        try {
            //生成key
            KeyGenerator keyGenerator=KeyGenerator.getInstance("DESede");
            //keyGenerator.init(112);      //3DES需要112 or 168位
            keyGenerator.init(new SecureRandom());   //或者使用这种方式默认长度，无需指定长度
            SecretKey secretKey = keyGenerator.generateKey(); //生成key的材料
            byte[] key = secretKey.getEncoded();  //生成key

            //key转换成密钥
            DESedeKeySpec desKeySpec=new DESedeKeySpec(key);
            SecretKeyFactory factory=SecretKeyFactory.getInstance("DESede");
            SecretKey key2 = factory.generateSecret(desKeySpec);      //转换后的密钥

            //加密
            Cipher cipher=Cipher.getInstance("DESede/ECB/PKCS5Padding");  //算法类型/工作方式/填充方式
            cipher.init(Cipher.ENCRYPT_MODE, key2);   //指定为加密模式
            byte[] result=cipher.doFinal(src.getBytes());
            System.out.println("jdk3DES加密: "+ Hex.encodeHexString(result));  //转换为十六进制

            //解密
            cipher.init(Cipher.DECRYPT_MODE,key2);  //相同密钥，指定为解密模式
            result = cipher.doFinal(result);   //根据加密内容解密
            System.out.println("jdk3DES解密: "+new String(result));  //转换字符串
            System.out.println("秘钥"+Arrays.toString(key));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bcDES(){
        try {
            //通过改变provider的方式，其他操作一样
            Security.addProvider(new BouncyCastleProvider());

            //生成key
            KeyGenerator keyGenerator=KeyGenerator.getInstance("DESede");
            keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey(); //生成key的材料
            byte[] key = secretKey.getEncoded();  //生成key

            //key转换成密钥
            DESedeKeySpec desKeySpec=new DESedeKeySpec(key);
            SecretKeyFactory factory=SecretKeyFactory.getInstance("DESede");
            SecretKey key2 = factory.generateSecret(desKeySpec);      //转换后的密钥

            //加密
            Cipher cipher=Cipher.getInstance("DESede/ECB/PKCS5Padding");  //算法类型/工作方式/填充方式
            cipher.init(Cipher.ENCRYPT_MODE, key2);   //指定为加密模式
            byte[] result=cipher.doFinal(src.getBytes());
            System.out.println("jdk3DES加密: "+Hex.encodeHexString(result));  //转换为十六进制

            //解密
            cipher.init(Cipher.DECRYPT_MODE,key2);  //相同密钥，指定为解密模式
            result = cipher.doFinal(result);   //根据加密内容解密
            System.out.println("jdk3DES解密: "+new String(result));  //转换字符串
            System.out.println("秘钥"+ Arrays.toString(key));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        jdkDES();
        bcDES();
    }
}
