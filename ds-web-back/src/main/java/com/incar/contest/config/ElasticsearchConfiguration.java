package com.incar.contest.config;

import com.incar.contest.elastic.Elasticsearch;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;


/**
 * ES Configuration
 *
 * @author Kong, created on 2020-02-27T11:59.
 * @since 1.0.0-SNAPSHOT
 */
@Configuration
public class ElasticsearchConfiguration {

    /**
     * ES Host,支持集群模式
     */
    @Value("${elasticsearch.host}")
    private String hosts;

    /**
     * ES port
     */
    @Value("${elasticsearch.port}")
    private Integer port = 9300;

    /**
     * 集群名称
     */
    @Value("${elasticsearch.clusterName}")
    private String clusterName;

    /**
     * 客户端去嗅探整个集群的状态, 一般默认是true
     * 单机版的是false
     */
    @Value("${elasticsearch.clientTransportSniff}")
    private Boolean clientTransportSniff;

    @Bean
    public Elasticsearch elasticsearch() {
        List<String> hostArr = Arrays.asList(hosts.split(","));
        return new Elasticsearch(hostArr, port, clusterName, clientTransportSniff);
    }

}
