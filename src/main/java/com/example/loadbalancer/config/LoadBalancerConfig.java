package com.example.loadbalancer.config;

import com.example.loadbalancer.model.BackendServer;
import com.example.loadbalancer.service.LoadBalancer;
import com.example.loadbalancer.strategy.RoundRobinStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadBalancerConfig {

    @Bean
    public LoadBalancer loadBalancer() {
        LoadBalancer loadBalancer = new LoadBalancer(new RoundRobinStrategy() , 10);
        loadBalancer.addServer(new BackendServer("http://localhost:8081"));
        loadBalancer.addServer(new BackendServer("http://localhost:8082"));
        return loadBalancer;
    }
}
