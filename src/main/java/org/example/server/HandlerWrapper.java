package org.example.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.utils.HttpUtils;

import java.io.IOException;

public class HandlerWrapper {
    public HttpHandler requirePost(HttpHandler handler) {
        return exchange -> {
            try {
                if (!handleCommon(exchange, "POST")) return;

                handler.handle(exchange);
            } catch (Exception e) {
                HttpUtils.sendInternalError(exchange, e);
            }
        };
    }

    public HttpHandler requireGet(HttpHandler handler) {
        return exchange -> {
            try {
                if (!handleCommon(exchange, "GET")) return;

                handler.handle(exchange);
            } catch (Exception e) {
                HttpUtils.sendInternalError(exchange, e);
            }
        };
    }

    private void addCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }

    private boolean handleCommon(HttpExchange exchange, String requiredMethod) throws IOException {
        addCorsHeaders(exchange);

        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            exchange.close();
            return false;
        }
        if (!requiredMethod.equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            exchange.close();

            return false;
        }
        return true;
    }
}
