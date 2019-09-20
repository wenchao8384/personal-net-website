package com.chao.encodetest.feiduichen;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
/**
 * @Author by wangwenchao
 * @Description:
 * @Create: 2019/9/18
 */
public class RSATest {
    private static String src="Hello RSA";

    public static void jdkRSA(){
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey=(RSAPublicKey) keyPair.getPublic();           //公钥
            RSAPrivateKey rsaPrivateKey=(RSAPrivateKey) keyPair.getPrivate();       //私钥
            System.out.println("public key:"+ Base64.encodeBase64String(rsaPublicKey.getEncoded()));
            System.out.println("private key:"+Base64.encodeBase64String(rsaPrivateKey.getEncoded()));

            //私钥加密，公钥解密--加密
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec=new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory=KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher=Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("RSA私钥加密，公钥解密--加密:"+Base64.encodeBase64String(result));

            //私钥加密，公钥解密--解密
            X509EncodedKeySpec x509EncodedKeySpec=new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            keyFactory=KeyFactory.getInstance("RSA");
            PublicKey publicKey=keyFactory.generatePublic(x509EncodedKeySpec);
            cipher=Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE,publicKey);
            result = cipher.doFinal(result);
            System.out.println("RSA私钥加密，公钥解密--解密:"+new String(result));

            //公钥加密，私钥解密--加密
            x509EncodedKeySpec=new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            keyFactory=KeyFactory.getInstance("RSA");
            publicKey=keyFactory.generatePublic(x509EncodedKeySpec);
            cipher=Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,publicKey);
            result = cipher.doFinal(src.getBytes());
            System.out.println("RSA公钥加密，私钥解密--加密:"+Base64.encodeBase64String(result));

            //公钥加密，私钥解密--解密
            pkcs8EncodedKeySpec=new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            keyFactory=KeyFactory.getInstance("RSA");
            privateKey =keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            cipher=Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE,privateKey);
            result=cipher.doFinal(result);
            System.out.println("RSA公钥加密，私钥解密--解密:"+new String(result));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey=(RSAPublicKey) keyPair.getPublic();           //公钥
            RSAPrivateKey rsaPrivateKey=(RSAPrivateKey) keyPair.getPrivate();       //私钥
            System.out.println("public key:"+ Base64.encodeBase64String(rsaPublicKey.getEncoded()));
            System.out.println("private key:"+Base64.encodeBase64String(rsaPrivateKey.getEncoded()));


            //公钥加密，私钥解密--加密
            X509EncodedKeySpec x509EncodedKeySpec=new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            KeyFactory keyFactory=KeyFactory.getInstance("RSA");
            PublicKey publicKey=keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher=Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,publicKey);
            String encript = "123";
            byte[] result = cipher.doFinal(encript.getBytes());
            System.out.println("RSA公钥加密，私钥解密--加密:"+Base64.encodeBase64String(result));



            System.out.println("解密前数据1:" + Base64.encodeBase64String(result));
            String privateKeyString = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
            byte[] bytes = Base64.decodeBase64(privateKeyString);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec=new PKCS8EncodedKeySpec(bytes);
            KeyFactory keyFactory2=KeyFactory.getInstance("RSA");
            PrivateKey privateKey =keyFactory2.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher2 =Cipher.getInstance("RSA");
            cipher2.init(Cipher.DECRYPT_MODE,privateKey);
            byte[] result2 = encript.getBytes();
            result2 = cipher.doFinal(result2);
            String oldSource = new String(result2);
            System.out.println("解密后数据:" + oldSource);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //ZSM1MWnoexEWQc01i/CAkLF5xL2TI7LwaGzW7E0lcPJ4ouZLDpMKEegkIU4GAazpIVH0V4IrF9gDI1ffN2m1Mg==
    }
}
