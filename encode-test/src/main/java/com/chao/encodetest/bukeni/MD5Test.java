package com.chao.encodetest.bukeni;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD2Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * @Author by wangwenchao
 * @Description:
 * @Create: 2019/9/16
 */
public class MD5Test {

    private static String src="Hello MD";

    public static void jdkMD5(){
        try {
            MessageDigest md=MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(src.getBytes());
            System.out.println("JDK MD5: "+ Hex.encodeHexString(digest));           //使用的是cc中带的Hex需要转换为十六进制
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    public static void jdkMD2(){
        try {
            MessageDigest md=MessageDigest.getInstance("MD2");
            byte[] digest = md.digest(src.getBytes());
            System.out.println("JDK MD2: "+Hex.encodeHexString(digest));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    public static void bcMD4(){
        //方式一
//        Digest digest=new MD4Digest();
//        digest.update(src.getBytes(), 0, src.getBytes().length);
//        byte[] md4Bytes=new byte[digest.getDigestSize()];
//        digest.doFinal(md4Bytes, 0);
//        System.out.println("BC MD4: "+org.bouncycastle.util.encoders.Hex.toHexString(md4Bytes));


        //方式二(通过添加provider的方式，将sun的改为bc的provider)
        try {
            Security.addProvider(new BouncyCastleProvider());  //通过添加provider的方式
            MessageDigest md=MessageDigest.getInstance("MD4");
            byte[] digest = md.digest(src.getBytes());
            System.out.println("BC MD4: "+Hex.encodeHexString(digest));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void bcMD5(){
        Digest digest=new MD5Digest();
        digest.update(src.getBytes(), 0, src.getBytes().length);
        byte[] md4Bytes=new byte[digest.getDigestSize()];
        digest.doFinal(md4Bytes, 0);
        System.out.println("BC MD5: "+Hex.encodeHexString(md4Bytes));
    }

    public static void bcMD2(){
        Digest digest=new MD2Digest();
        digest.update(src.getBytes(), 0, src.getBytes().length);
        byte[] md4Bytes=new byte[digest.getDigestSize()];
        digest.doFinal(md4Bytes, 0);
        System.out.println("BC MD2: "+Hex.encodeHexString(md4Bytes));
    }

    public static void ccMD2(){        //有方法直接就可以转换十六进制
        System.out.println("CC MD2: "+ DigestUtils.md2Hex(src.getBytes()));
    }

    public static void ccMd5(){
        System.out.println("CC MD5: "+DigestUtils.md5Hex(src.getBytes()));
    }

    public static void main(String[] args) {
        System.out.println("jdkMD5");
        jdkMD5();
        System.out.println("jdkMD2");
        jdkMD2();
        System.out.println("bcMD4");
        bcMD4();
        System.out.println("bcMD5");
        bcMD5();
        System.out.println("bcMD2");
        bcMD2();
        System.out.println("ccMD2");
        ccMD2();
        System.out.println("ccMd5");
        ccMd5();
    }
}
