package com.gofun.utils;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestClientBuilder.RequestConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 客户端生成工厂
 * @author cuiqh
 */
@Component
public class ElasticHttpClientAgg implements InitializingBean, DisposableBean {

  private static final Logger logger = LoggerFactory.getLogger(ElasticHttpClientAgg.class);
  private static RestHighLevelClient restHighLevelClient;
  @Value("${elastic.aggregator.name}")
  private String elasticName;
  @Value("${elastic.aggregator.url}")
  private String elasticUrls;
  @Value("${elastic.aggregator.username}")
  private String username;
  @Value("${elastic.aggregator.password}")
  private String password;
  @Value("${deployModel}")
  private String deployModel;
  public static final String SCHEMA = "http";
  private static final int CONNECT_TIME_OUT = 5000;
  private static final int SOCKET_TIME_OUT = 30000;
  private static final int CONNECTION_REQUEST_TIME_OUT = 5000;
  private static final int MAX_CONNECT_NUM = 1000;
  private static final int MAX_CONNECT_PER_ROUTE = 500;
  private static HttpHost[] httpHosts;
  private static RestClientBuilder builder;
  private BulkProcessor bulkProcessor;
  public static String MASTER_NODE_ADDRESS = "";
  private final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

  /**
   * 需采用线程安全的方式初始化；
   * 最理想的客户端生命周期是与应用相同，在应用停止服务之前应该关闭客户端链接，释放资源。
   * @author cuiqh
   */
  private synchronized RestHighLevelClient initClient() {
    try {
      builder = RestClient.builder(httpHosts);
      setAsyncConnectTimeOutConfig(credentialsProvider,username,password);
      restHighLevelClient = new RestHighLevelClient(builder);
      logger.info("##elasticSearch##初始化成功 config is elasticName：" + elasticName + " elasticUrls:" + elasticUrls);
      return restHighLevelClient;
    } catch (Exception e) {
      logger.info("##elasticSearch##初始化失败 config is elasticName：" + elasticName + " elasticUrls:" + elasticUrls, e);
    }
    return null;
  }

  /**
   * 创建RestClientBuilder 客户端配置
   * @author cuiqh
   */
  public void setAsyncConnectTimeOutConfig(CredentialsProvider credentialsProvider,String userName,String password) {
	// 加载连接延迟配置
    builder.setRequestConfigCallback(new RequestConfigCallback() {
      @Override
      public Builder customizeRequestConfig(Builder requestConfigBuilder) {
        requestConfigBuilder.setConnectTimeout(CONNECT_TIME_OUT);
        requestConfigBuilder.setSocketTimeout(SOCKET_TIME_OUT);
        requestConfigBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIME_OUT);
        return requestConfigBuilder;
      }
    });
    // 加载连接数配置
    builder.setHttpClientConfigCallback(new HttpClientConfigCallback() {
      @Override
      public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
        httpClientBuilder.setMaxConnTotal(MAX_CONNECT_NUM);
        httpClientBuilder.setMaxConnPerRoute(MAX_CONNECT_PER_ROUTE);
        if("cloud".equals(deployModel)){
        	credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));
        	httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
        }
        
        return httpClientBuilder;
      }
    });
  }

  /**
   * 配置es集群节点信息
   * @author cuiqh
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    String[] urlArray = elasticUrls.split(",");
    int nodeSize = urlArray.length;
    httpHosts = new HttpHost[nodeSize];
    for (int i = 0; i < nodeSize; i++) {
      String[] urlInfo = urlArray[i].split(":");
      httpHosts[i] = new HttpHost(urlInfo[0], Integer.parseInt(urlInfo[1]), SCHEMA);
      MASTER_NODE_ADDRESS = (i == 0 && MASTER_NODE_ADDRESS.length() == 0) ? urlArray[i] : MASTER_NODE_ADDRESS;
    }
    logger.info("等待第一次调用es的时候初始化Es配置信息 config is elasticName：" + elasticName + " elasticUrls:" + elasticUrls);
  }

  public RestHighLevelClient getEsHttpClient() {
    if (null == restHighLevelClient) {
      return initClient();
    }
    return restHighLevelClient;
  }

  /**
   * 批量写入
   * @author cuiqh
   *
   * @date 2018年8月10日
   */
  public BulkProcessor getBulkProcessor(RestHighLevelClient restHighLevelClient) {
    if (null != bulkProcessor) {
      return bulkProcessor;
    }
    bulkProcessor = BulkProcessor.builder(restHighLevelClient::bulkAsync, new BulkProcessor.Listener() {
      @Override
      public void beforeBulk(long executionId, BulkRequest request) {
        logger.info("准备写入[{}]条数据", request.requests().size());
      }

      @Override
      public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
        if (response.hasFailures()) {
          logger.error("buildFailureMessage:" + response.buildFailureMessage());
        }
//        List<String> savedDataIds = new ArrayList<>(response.getItems().length);
//        for (BulkItemResponse itemResponse : response.getItems()) {
//          savedDataIds.add(itemResponse.getId());
//        }
        logger.info("成功写入[{}]条数据, 耗时[{}]", response.getItems().length, response.getTook().secondsFrac());
      }

      /**
       * 写入方式设置
       *  or ？
       */
      @Override
      public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
        logger.info("写入数据时发生错误 错误信息如下", failure);
      }
    }).setConcurrentRequests(0)
        .setBulkActions(100000)
        .setBulkSize(new ByteSizeValue(10, ByteSizeUnit.MB))
        .setFlushInterval(TimeValue.timeValueSeconds(30))
        .setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(5L), 3))
        .build();
    return bulkProcessor;
  }


  @Override
  public void destroy() {
    try {
      if (null != restHighLevelClient) {
        restHighLevelClient.close();
      }
      if (null != bulkProcessor) {
        bulkProcessor.awaitClose(10, TimeUnit.SECONDS);
      }
    } catch (Exception e) {
      logger.info(e.getMessage(), e);
    }
  }
}
