package com.example.loadbalancer.service;

import com.example.loadbalancer.model.BackendServer;
import com.example.loadbalancer.strategy.LoadBalancerStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class LoadBalancer {
    private  List<BackendServer> servers = new CopyOnWriteArrayList<>();
    private LoadBalancerStrategy strategy;
    private final ExecutorService executorService;

    public LoadBalancer(LoadBalancerStrategy initialStrategy, int threadPoolSize) {
        this.strategy = initialStrategy;
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void addServer(BackendServer server) {
        servers.add(server);
    }

    public void removeServer(BackendServer server) {
        servers.remove(server);
    }

    public void setStrategy(LoadBalancerStrategy strategy) {
        this.strategy = strategy;
    }

    public BackendServer getServer() {
        if (servers.isEmpty()) {
            throw new IllegalStateException("No backend servers available");
        }
        return strategy.getServer(servers);
    }

    public void handleRequest(Runnable requestHandler) {
        executorService.submit(requestHandler);
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
