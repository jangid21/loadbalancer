package com.example.loadbalancer.strategy;

import com.example.loadbalancer.model.BackendServer;

import java.util.List;

public interface LoadBalancerStrategy {
    BackendServer getServer(List<BackendServer> servers);
}
