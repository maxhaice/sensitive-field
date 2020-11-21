package com.hub.eventgenerator.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


/**
 * Implements GET, POST HTTP requests and remote host connectivity check.
 */
public class HttpService {
    private final HttpClient client;

    public HttpService() {
        client = HttpClient.newHttpClient();
    }

    public HttpResponse<?> get(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();
    }

    public HttpResponse<?> post(String url, String payload) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.discarding()).join();
    }

    public boolean checkConnection(String url, int port) {
        try {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(url, port), 1000);
            }
            return true;
        } catch (IOException exception) {
            return false;
        }
    }
}
