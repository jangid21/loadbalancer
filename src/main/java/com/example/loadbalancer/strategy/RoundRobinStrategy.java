package com.example.loadbalancer.strategy;

import com.example.loadbalancer.model.BackendServer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinStrategy implements LoadBalancerStrategy {
    private final AtomicInteger index = new AtomicInteger(0);

    @Override
    public BackendServer getServer(List<BackendServer> servers) {
        int currentIndex = index.getAndUpdate(i -> (i + 1) % servers.size());
        return servers.get(currentIndex);
    }
}
