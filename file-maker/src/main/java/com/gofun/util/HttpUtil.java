package com.gofun.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;

@Slf4j
public class HttpUtil {


  private final static String OPERATER_NAME = "【HTTP操作】";

  private final static int SUCCESS = 200;

  private final static String UTF8 = "UTF-8";

  private HttpClient client;

  private static HttpUtil instance = new HttpUtil();
  /**
   * 私有化构造器
   */
  private HttpUtil() {
    HttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
    HttpConnectionManagerParams params = httpConnectionManager.getParams();
    params.setConnectionTimeout(5000);
    params.setSoTimeout(20000);
    params.setDefaultMaxConnectionsPerHost(1000);
    params.setMaxTotalConnections(1000);
    client = new HttpClient(httpConnectionManager);
    //client.getState().setCredentials();
    client.getParams().setContentCharset(UTF8);
    client.getParams().setHttpElementCharset(UTF8);
  }

  /**
   * get请求
   */
  public static String get(URL url) {
    return instance.doGet(url);
  }

  private String doGet(URL url) {
    long beginTime = System.currentTimeMillis();
    String respStr = StringUtils.EMPTY;
    try {
      System.out.println(OPERATER_NAME + "开始get通信，目标host:" + url);
      HttpMethod method = new GetMethod(url.toString());
      // 中文转码
      method.getParams().setContentCharset(UTF8);
      try {
        client.executeMethod(method);
      } catch (Exception e) {
        log.info(e.getMessage(), e);
      }
      if (method.getStatusCode() == SUCCESS) {
        respStr = method.getResponseBodyAsString();
      }
      // 释放连接
      method.releaseConnection();

      System.out.println(OPERATER_NAME + "通讯完成，返回码：" + method.getStatusCode());
      System.out.println(OPERATER_NAME + "返回内容：" + method.getResponseBodyAsString());
      System.out.println(OPERATER_NAME + "结束..返回结果:" + respStr);
    } catch (Exception e) {
      log.info(OPERATER_NAME, e);
    }
    long endTime = System.currentTimeMillis();
    System.out.println(OPERATER_NAME + "共计耗时:" + (endTime - beginTime) + "ms");

    return respStr;
  }


  /**
   * PUT请求
   */
  public static String put(URL url, String content) {
    return instance.doPut(url, content);
  }

  private String doPut(URL url, String content) {
    long beginTime = System.currentTimeMillis();
    String respStr = StringUtils.EMPTY;
    try {
      System.out.println(OPERATER_NAME + "开始put通信，目标host:" + url.toString());
//      log.info("通信内容:" + content);
      PutMethod post = new PutMethod(url.toString());
      RequestEntity requestEntity = new StringRequestEntity(content, "application/json;charse=UTF-8", UTF8);
      post.setRequestEntity(requestEntity);
      // 设置格式
      post.getParams().setContentCharset(UTF8);

      client.executeMethod(post);
      if (post.getStatusCode() == SUCCESS) {
        respStr = post.getResponseBodyAsString();
      }

      System.out.println(OPERATER_NAME + "通讯完成，返回码：" + post.getStatusCode());
      System.out.println(OPERATER_NAME + "返回内容：" + post.getResponseBodyAsString());
      System.out.println(OPERATER_NAME + "结束..返回结果:" + respStr);
      post.releaseConnection();

    } catch (Exception e) {
      log.error(OPERATER_NAME, e);
    }
    long endTime = System.currentTimeMillis();
    log.info(OPERATER_NAME + "共计耗时:" + (endTime - beginTime) + "ms");
    return respStr;
  }

  /**
   * POST请求
   */
  public static String post(URL url, String content) {
    return instance.doPost(url, content);
  }

  private String doPost(URL url, String content) {
    long beginTime = System.currentTimeMillis();
    String respStr = StringUtils.EMPTY;
    try {
      System.out.println(OPERATER_NAME + "开始post通信，目标host:" + url.toString());
//      log.info("通信内容:" + content);
      PostMethod post = new PostMethod(url.toString());
      RequestEntity requestEntity = new StringRequestEntity(content, "application/json;charse=UTF-8", UTF8);
      post.setRequestEntity(requestEntity);
      // 设置格式
      post.getParams().setContentCharset(UTF8);

      client.executeMethod(post);
      if (post.getStatusCode() == SUCCESS) {
        respStr = post.getResponseBodyAsString();
      }

      System.out.println(OPERATER_NAME + "通讯完成，返回码：" + post.getStatusCode());
      System.out.println(OPERATER_NAME + "返回内容：" + post.getResponseBodyAsString());
      System.out.println(OPERATER_NAME + "结束..返回结果:" + respStr);
      post.releaseConnection();

    } catch (Exception e) {
      log.error(OPERATER_NAME, e);
    }
    long endTime = System.currentTimeMillis();
    System.out.println(OPERATER_NAME + "共计耗时:" + (endTime - beginTime) + "ms");
    return respStr;
  }


  /**
   * get请求
   */
  public static String del(URL url) {
    return instance.doDel(url);
  }

  private String doDel(URL url) {
    long beginTime = System.currentTimeMillis();
    String respStr = StringUtils.EMPTY;
    try {
      System.out.println(OPERATER_NAME + "开始del通信，目标host:" + url);
      HttpMethod method = new DeleteMethod(url.toString());
      // 中文转码
      method.getParams().setContentCharset(UTF8);
      try {
        client.executeMethod(method);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
      if (method.getStatusCode() == SUCCESS) {
        respStr = method.getResponseBodyAsString();
      }
      // 释放连接
      method.releaseConnection();

      System.out.println(OPERATER_NAME + "通讯完成，返回码：" + method.getStatusCode());
      System.out.println(OPERATER_NAME + "返回内容：" + method.getResponseBodyAsString());
      System.out.println(OPERATER_NAME + "结束..返回结果:" + respStr);
    } catch (Exception e) {
      log.info(OPERATER_NAME, e);
    }
    long endTime = System.currentTimeMillis();
    System.out.println(OPERATER_NAME + "共计耗时:" + (endTime - beginTime) + "ms");

    return respStr;
  }

}
