package com.gofun.util;

import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author by wangwenchao
 * @Description:
 * @Create: 2019/10/15
 */
public class MyImageUtil {

    private static String strNetImageToBase64;

    public static String imgFileToBase64(String filePath){
        ;
        try {
            FileInputStream fileInputStream = new FileInputStream(MyImageUtil.class.getClassLoader().getResource(filePath).getPath());
            final ByteArrayOutputStream data = new ByteArrayOutputStream();
            // 创建URL
            final byte[] by = new byte[1024];

            InputStream is = fileInputStream;
            // 将内容读取内存中
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            // 对字节数组Base64编码
            BASE64Encoder encoder = new BASE64Encoder();
            strNetImageToBase64 = encoder.encode(data.toByteArray());
            System.out.println("data:image/jpeg;base64,"+ strNetImageToBase64);
            // 关闭流
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return strNetImageToBase64;
    }

    public static String netImageToBase64(String netImagePath) {
        final ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            // 创建URL
            URL url = new URL(netImagePath);
            final byte[] by = new byte[1024];
            // 创建链接
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            InputStream is = conn.getInputStream();
            // 将内容读取内存中
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            // 对字节数组Base64编码
            is.close();
            BASE64Encoder encoder = new BASE64Encoder();
            strNetImageToBase64 = encoder.encode(data.toByteArray());
            //System.out.println("data:image/jpeg;base64,"+ strNetImageToBase64);

            // 关闭流
        } catch (IOException e) {
            e.printStackTrace();
        }

        return strNetImageToBase64;
    }

    public static void main(String[] args) {
        //imgFileToBase64("file/1.jpg");
        //System.out.println(netImageToBase64("http://imgpub1.shouqiev.com/gofunapi/images/20191013/ctlMpxFssH.jpg"));;
    }
}
