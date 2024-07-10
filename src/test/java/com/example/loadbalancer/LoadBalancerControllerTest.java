package com.example.loadbalancer;

import com.example.loadbalancer.model.BackendServer;
import com.example.loadbalancer.service.LoadBalancer;
import com.example.loadbalancer.strategy.RoundRobinStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoadBalancerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoadBalancer loadBalancer;

    @BeforeEach
    public void setUp() {
        loadBalancer.addServer(new BackendServer("http://localhost:8081"));
        loadBalancer.addServer(new BackendServer("http://localhost:8082"));
    }

    @Test
    public void testHandleRequest() throws Exception {
        mockMvc.perform(get("/load-balancer/request").param("path", "/api/data"))
                .andExpect(status().isOk());
    }
}
