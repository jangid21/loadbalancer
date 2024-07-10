package com.example.loadbalancer;

import com.example.loadbalancer.model.BackendServer;
import com.example.loadbalancer.strategy.RoundRobinStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoundRobinStrategyTest {

    private List<BackendServer> servers;
    private RoundRobinStrategy strategy;

    @BeforeEach
    public void setUp() {
        servers = Arrays.asList(
                new BackendServer("http://localhost:8081"),
                new BackendServer("http://localhost:8082")
        );
        strategy = new RoundRobinStrategy();
    }

    @Test
    public void testGetServer() {
        assertEquals("http://localhost:8081", strategy.getServer(servers).getUrl());
        assertEquals("http://localhost:8082", strategy.getServer(servers).getUrl());
        assertEquals("http://localhost:8081", strategy.getServer(servers).getUrl());
    }
}
