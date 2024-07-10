package com.example.loadbalancer.controller;

import com.example.loadbalancer.model.BackendServer;
import com.example.loadbalancer.service.HttpRequestHandler;
import com.example.loadbalancer.service.LoadBalancer;
import com.example.loadbalancer.strategy.RandomStrategy;
import com.example.loadbalancer.strategy.RoundRobinStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/load-balancer")
public class LoadBalancerController {

    @Autowired
    private LoadBalancer loadBalancer;

    @Autowired
    private HttpRequestHandler httpRequestHandler;

    @GetMapping("/request")
    public String handleRequest(@RequestParam String path) {
        try {
            return httpRequestHandler.handleRequest(path);
        } catch (Exception e) {
            return "Error handling request: " + e.getMessage();
        }
    }


    @PostMapping("/add-server")
    public String addServer(@RequestParam String url) {
        loadBalancer.addServer(new BackendServer(url));
        return "Server added successfully!";
    }

    @DeleteMapping("/remove-server")
    public String removeServer(@RequestParam String url) {
        loadBalancer.removeServer(new BackendServer(url));
        return "Server removed successfully!";
    }

    @PostMapping("/set-strategy")
    public String setStrategy(@RequestParam String strategy) {
        switch (strategy.toLowerCase()) {
            case "roundrobin":
                loadBalancer.setStrategy(new RoundRobinStrategy());
                break;
            case "random":
                loadBalancer.setStrategy(new RandomStrategy());
                break;
            default:
                return "Invalid strategy!";
        }
        return "Strategy set to " + strategy;
    }
}
