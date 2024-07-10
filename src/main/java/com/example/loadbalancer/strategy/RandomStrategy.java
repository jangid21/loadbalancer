package com.example.loadbalancer.strategy;

import com.example.loadbalancer.model.BackendServer;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomStrategy implements LoadBalancerStrategy {

    @Override
    public BackendServer getServer(List<BackendServer> servers) {
        int randomIndex = ThreadLocalRandom.current().nextInt(servers.size());
        return servers.get(randomIndex);
    }
}
