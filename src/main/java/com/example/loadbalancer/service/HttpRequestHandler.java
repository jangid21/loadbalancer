package com.example.loadbalancer.service;

import com.example.loadbalancer.model.BackendServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class HttpRequestHandler {
    private final LoadBalancer loadBalancer;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    public HttpRequestHandler(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    public String handleRequest(String requestPath) throws Exception {
        BackendServer server = loadBalancer.getServer();
        URL url;
        try {
            url = new URL(server.getUrl() + requestPath);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid URL: " + e.getMessage(), e);
        }

        executorService.submit(() -> {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder content = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    // Process content if needed
                    System.out.println("Response from server: " + content.toString());
                } catch (IOException e) {
                    throw new RuntimeException("Failed to read response: " + e.getMessage(), e);
                }
            } catch (IOException e) {
                throw new RuntimeException("Connection failed: " + e.getMessage(), e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });

        return "Request submitted to server: " + server.getUrl() + requestPath;
    }
}
