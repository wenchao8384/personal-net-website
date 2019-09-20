package com.chao.encodetest.base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.Arrays;

/**
 * @Author by wangwenchao
 * @Description:
 * @Create: 2019/9/16
 */
public class Base64Test {
    private static String src="Hello Base64";

    public static void jdkBase64(){
        try {
            BASE64Encoder encoder=new BASE64Encoder();
            String encode = encoder.encode(src.getBytes());
            System.out.println("encode: "+encode);

            BASE64Decoder decoder=new BASE64Decoder();
            String decode=new String(decoder.decodeBuffer(encode));
            System.out.println("decode: "+decode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void test1(){
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAINzCPJI/hPrX/yd4pEPONfDupTV\n" +
                "DU+3sys5LPm67UaYgZPSxvGxzVljRhfssG9KUH0R06NWRV++A0z+yyYEpDD9xVGErljMR1QZDC++\n" +
                "9tiX3t2Jh9N+vnYNWh5F0qWh1Ke4qZIGk7iM46gR7BGo8GXL0dQdyWZAmjRNSKD0PM9vAgMBAAEC\n" +
                "gYAzIEDwi3dXJAs3Y+lFZlhDg3tEfAEralWjkB9wGkZDWPm9FxQN2Yv3ImeW0pZlEtBvdMmOE/Xz\n" +
                "oSIDhm5ZISEC5LhB+t8LR4mkFN4bfxTu8HKqoKtMHfXDz2EoPIPRulkLTJ9tgF8KEf9La/47oUnt\n" +
                "SeNWtWd+BNETim0Y7WbxUQJBANWHQkRxPTpQBMniFKRTgzEFd/tUDxSPe/Cmw2Xw28wuP2A4xlGF\n" +
                "Za8VoNcJmn+I0G7uo/xA9Xd3CWq6CB5qYDMCQQCdmFxPjeSwRF1dUswNb99M6/c6Yf9+/n2vn0ro\n" +
                "wnOKx9Wy3KNLrYDVZR+Pyv0FjhVGOUrCCmefjDcfXzhLoSfVAkEAmjh07kXzePhuXPmC+ySuLmvK\n" +
                "uqV9ttXjKG7p1ejed1w3veGDq0Fzrb8rSeTPx6kjEdweaITqRXyeOo1ea8lc7QJAMtUZOWPoVt7G\n" +
                "SrrRLKhgG3ylMwS3F6xYuBQmYmuOPz5z9Ixsc5WUT8CdbJEqCeepfwwty+b1Q6ZDhW/+RY7GvQJB\n" +
                "ALfrdzOegdiTNcuSZNPX6PqVUPeTBg67RG7ofEd+zs9OILTZU5ldUA583zBNxwCHBjwx3VlPOqzM\n" +
                "1CngQZZ1prw=";
        BASE64Decoder decoder=new BASE64Decoder();
        String decode= null;
        try {
            decode = new String(decoder.decodeBuffer(privateKey));
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = decode.getBytes();
        System.out.println("decode: "+ Arrays.toString(bytes));
    }

    public static void bouncybastleBase64(){
        byte[] encode = org.bouncycastle.util.encoders.Base64.encode(src.getBytes());
        System.out.println("encode: "+new String(encode));

        byte[] decode = org.bouncycastle.util.encoders.Base64.decode(encode);
        System.out.println("decode: "+new String(decode));
    }

    public static void commonscodecBase64(){
        byte[] encode= org.apache.commons.codec.binary.Base64.encodeBase64(src.getBytes());
        //需要转化为String
        System.out.println("encode: "+new String(encode));

        byte[] decode = org.apache.commons.codec.binary.Base64.decodeBase64(encode);
        System.out.println("decode: "+new String(decode));
    }

    public static void main(String[] args) {
        /*System.out.println("jdkBase64方式");
        jdkBase64();
        System.out.println("bouncybastleBase64方式");
        bouncybastleBase64();
        System.out.println("commonscodecBase64方式");
        commonscodecBase64();*/
        test1();
    }
}
