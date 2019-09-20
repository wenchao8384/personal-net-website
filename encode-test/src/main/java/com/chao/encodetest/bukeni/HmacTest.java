package com.chao.encodetest.bukeni;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 * @Author by wangwenchao
 * @Description:
 * @Create: 2019/9/17
 */
public class HmacTest {

    private static String src="Hello HMAC";

    public static void jdkHmacMD5(){
        try {
            KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacMD5");  //初始化KeyGenerator
            SecretKey secretKey=keyGenerator.generateKey(); //产生密钥
            //byte[] key=secretKey.getEncoded();     //获得密钥(默认生成)

            byte[] key=Hex.decodeHex(new char[]{'a','a','a','a','a','a','a','a','a','b'});  //手动生成密钥(十位)

            SecretKey secretKey2=new SecretKeySpec(key, "HmacMD5"); //还原密钥
            Mac mac=Mac.getInstance(secretKey2.getAlgorithm());  //实例化mac
            //初始化mac
            mac.init(secretKey2);
            byte[] hmacMD5Bytes=mac.doFinal(src.getBytes());
            System.out.println("jdk hmacMD5: "+Hex.encodeHexString(hmacMD5Bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bcHmacMd5(){
        HMac hMac=new HMac(new MD5Digest());
        hMac.init(new KeyParameter(org.bouncycastle.util.encoders.Hex.decode("aaaaaaaaac")));  //需要十位密钥
        hMac.update(src.getBytes(),0,src.getBytes().length);

        byte[] hmacMD5=new byte[hMac.getMacSize()];
        hMac.doFinal(hmacMD5, 0);
        System.out.println("bc hmacMD5: "+Hex.encodeHexString(hmacMD5));

    }

    public static void main(String[] args) {
        jdkHmacMD5();
        bcHmacMd5();
    }
}
