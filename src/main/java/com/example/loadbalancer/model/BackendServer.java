package com.example.loadbalancer.model;

public class BackendServer {
    private final String url;

    public BackendServer(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
