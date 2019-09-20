package com.chao.encodetest.feiduichen;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

/**
 * @Author by wangwenchao
 * @Description:
 * @Create: 2019/9/17
 */
public class DHTest {
    private static String src="Hello DH";

    public static void jdkDH(){
        try {
            //初始化发送方密钥
            KeyPairGenerator senderKeyPairGenerator=KeyPairGenerator.getInstance("DH");
            senderKeyPairGenerator.initialize(512);   //密钥长度
            KeyPair senderKeyPair = senderKeyPairGenerator.generateKeyPair();
            byte[] senderPublicKeyEnc = senderKeyPair.getPublic().getEncoded();  //发送方key,需传递给接收方（网络，文件）

            //初始化接收方密钥
            KeyFactory factory=KeyFactory.getInstance("DH");
            X509EncodedKeySpec x509EncodedKeySpec=new X509EncodedKeySpec(senderPublicKeyEnc);  //根据从发送方得到的key解析
            PublicKey receiverPublicKey=factory.generatePublic(x509EncodedKeySpec);
            DHParameterSpec dhParameterSpec=((DHPublicKey)receiverPublicKey).getParams();
            KeyPairGenerator receiverKeyPairGenerator=KeyPairGenerator.getInstance("DH");
            receiverKeyPairGenerator.initialize(dhParameterSpec);
            KeyPair receiverKeyPair = receiverKeyPairGenerator.generateKeyPair();
            PrivateKey receiverPrivateKey = receiverKeyPair.getPrivate();
            byte[] receiverPublicKeyEnc = receiverKeyPair.getPublic().getEncoded();

            //密钥构建
            KeyAgreement receiverKeyAgreement=KeyAgreement.getInstance("DH");
            receiverKeyAgreement.init(receiverPrivateKey);
            receiverKeyAgreement.doPhase(receiverPublicKey, true);
            SecretKey receiverDESKey=receiverKeyAgreement.generateSecret("DES");  //发送发密钥(公钥)
            KeyFactory senderKeyFactory=KeyFactory.getInstance("DH");
            x509EncodedKeySpec=new X509EncodedKeySpec(receiverPublicKeyEnc);
            PublicKey senderPublicKey=senderKeyFactory.generatePublic(x509EncodedKeySpec);
            KeyAgreement senderKeyAgreement=KeyAgreement.getInstance("DH");
            senderKeyAgreement.init(senderKeyPair.getPrivate());
            senderKeyAgreement.doPhase(senderPublicKey, true);
            SecretKey senderDESKey=senderKeyAgreement.generateSecret("DES");        //接收方密钥(私钥)
            if(Objects.equals(receiverDESKey, senderDESKey)){
                System.out.println("双方密钥相同");
            }
            //加密
            Cipher cipher=Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, senderDESKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("jdk DH加密: "+org.apache.commons.codec.binary.Base64.encodeBase64String(result));

            //解密
            cipher.init(Cipher.DECRYPT_MODE, receiverDESKey);
            result=cipher.doFinal(result);
            System.out.println("jdk DH解密: "+new String(result));


            //加密
            Cipher cipher2=Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, receiverDESKey);
            byte[] result2 = cipher.doFinal(src.getBytes());
            System.out.println("jdk DH加密2: "+org.apache.commons.codec.binary.Base64.encodeBase64String(result));

            //解密
            cipher.init(Cipher.DECRYPT_MODE, senderDESKey);
            result=cipher.doFinal(result);
            System.out.println("jdk DH解密2: "+new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        jdkDH();
    }
}
