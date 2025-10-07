package org.example.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.controllers.WeatherController;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {
    private final int port;
    private HttpServer server;
    private HandlerWrapper wrapper;
    private final AppContext context;

    public Server(int port) {
        this.port = port;
        this.wrapper = new HandlerWrapper();
        this.context = AppConfig.init();
    }

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        HttpHandler weatherController = context.get(WeatherController.class);
        server.createContext("/weather", wrapper.requireGet(weatherController::handle));

        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();

        System.out.println("✅ Server started on http://localhost:" + port);
    }
}