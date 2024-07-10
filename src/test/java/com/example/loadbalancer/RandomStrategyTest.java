package com.example.loadbalancer;

import com.example.loadbalancer.model.BackendServer;
import com.example.loadbalancer.strategy.RandomStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomStrategyTest {

    private List<BackendServer> servers;
    private RandomStrategy strategy;

    @BeforeEach
    public void setUp() {
        servers = Arrays.asList(
                new BackendServer("http://localhost:8081"),
                new BackendServer("http://localhost:8082")
        );
        strategy = new RandomStrategy();
    }

    @Test
    public void testGetServer() {
        BackendServer server = strategy.getServer(servers);
        assertTrue(servers.contains(server));
    }
}
