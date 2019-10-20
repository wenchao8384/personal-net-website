package com.chao.controller;

import ch.qos.logback.core.util.FileUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author by wangwenchao
 * @Description:
 * @Create: 2019/9/23
 */
@RestController
@RequestMapping("/download")
@CrossOrigin
public class DownLoadController {

    @RequestMapping("/downloadFileNormalIo")
    private void downloadFile(HttpServletResponse response) {
        InputStream f = this.getClass().getResourceAsStream("/2.pdf");

        response.reset();
        response.setContentType("application/x-msdownload;charset=utf-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(("1" + ".pdf").getBytes("gbk"), "iso-8859-1"));//下载文件的名称
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            ServletOutputStream sout = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            bis = new BufferedInputStream(f);
            bos = new BufferedOutputStream(sout);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bos.flush();
            bos.close();
            bis.close();
        } catch (final IOException e) {
        }
    }

    @RequestMapping("/downloadFileBase64IoError")
    public String downloadFileBase64IoError() {
        System.out.println("pdf生成方法被请求！！！！！！！！！！！！！！！！！！！！！！！！！！！");
        String urlStr = this.getClass().getClassLoader().getResource("2.pdf").getPath();
        System.out.println(urlStr);
        File file = new File(urlStr);
        BASE64Encoder encoder = new BASE64Encoder();
        FileInputStream fin =null;
        BufferedInputStream bin =null;
        ByteArrayOutputStream baos = null;
        BufferedOutputStream bout =null;
        try {
            fin = new FileInputStream(urlStr);
            bin = new BufferedInputStream(fin);
            baos = new ByteArrayOutputStream();
            bout = new BufferedOutputStream(baos);
            byte[] buffer = new byte[1024];
            int len = bin.read(buffer);
            while(len != -1){
                bout.write(buffer, 0, len);
                len = bin.read(buffer);
            }
            //刷新此输出流并强制写出所有缓冲的输出字节
            bout.flush();
            byte[] bytes = baos.toByteArray();
            return encoder.encodeBuffer(bytes).trim();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fin.close();
                bin.close();
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    @RequestMapping("/downloadFileBase64Io")
    public String downloadFileBase64Io() {
        System.out.println("pdf生成方法被请求！！！！！！！！！！！！！！！！！！！！！！！！！！！");
        try {
            BASE64Encoder encoder = new BASE64Encoder();
            InputStream in= FileUtil.class.getClassLoader().getResource("2.pdf").openStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(in));
            StringBuilder sb=new StringBuilder();
            String line="";
            while((line=br.readLine())!=null) {
                sb.append(line);
            }
            return encoder.encodeBuffer(sb.toString().getBytes()).trim();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] readConfigFile(String cfgFile) {
        try {
            InputStream in= FileUtil.class.getClassLoader().getResource(cfgFile).openStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(in));
            StringBuilder sb=new StringBuilder();
            String line="";
            while((line=br.readLine())!=null) {
                sb.append(line);
            }
            return sb.toString().getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
