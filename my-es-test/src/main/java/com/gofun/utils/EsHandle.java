package com.gofun.utils;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author by wangwenchao
 * @Description:
 * @Create: 2019/8/26
 */
@Service
public class EsHandle implements InitializingBean {

    @Autowired
    private ElasticHttpClientAgg elasticHttpClient;

    protected BulkProcessor processor;

    @Override
    public void afterPropertiesSet() throws Exception {
        processor = elasticHttpClient.getBulkProcessor(elasticHttpClient.getEsHttpClient());
    }

    public void queryUserForGroupRule(String userId) throws Exception {
        RestHighLevelClient restHighLevelClient = elasticHttpClient.getEsHttpClient();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0).size(10000).timeout(new TimeValue(10, TimeUnit.SECONDS)).query(QueryBuilders.termQuery("userId", userId));
        sourceBuilder.sort("_id", SortOrder.DESC);
        SearchRequest searchRequest = new SearchRequest("user_group").types("user_group").source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest);
        List<SearchHit> list = new ArrayList<>();
        if (null != response.getHits() && response.getHits().getHits().length > 0) {
            for (SearchHit searchHit : response.getHits()) {
                list.add(searchHit);
            }
        }
        System.out.println("--------------------------------list");
        System.out.println(JSON.toJSONString(list));
    }
}
