package com.chao.encodetest.bukeni;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author by wangwenchao
 * @Description:
 * @Create: 2019/9/17
 */
public class SHATest {
    private static String src="Hello SHA";

    public static void jdkSHA1(){
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA");
            digest.update(src.getBytes());
            System.out.println("JDK SHA1: "+ Hex.encodeHexString(digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void mcSHA1(){
        Digest digest=new SHA1Digest();
        digest.update(src.getBytes(),0,src.getBytes().length);
        byte[] sha1Byte1=new byte[digest.getDigestSize()];
        digest.doFinal(sha1Byte1, 0);
        System.out.println("MC SHA1:"+Hex.encodeHexString(sha1Byte1));
    }

    public static void ccsha(){
        System.out.println("CC sha1:"+ DigestUtils.sha1Hex(src));
    }

    public static void main(String[] args) {
        jdkSHA1();
        mcSHA1();
        ccsha();
    }
}
