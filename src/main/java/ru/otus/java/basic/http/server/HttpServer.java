package ru.otus.java.basic.http.server;

import ru.otus.java.basic.http.server.processors.AnotherHelloWorldRequestProcessor;
import ru.otus.java.basic.http.server.processors.HelloWorldRequestProcessor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private int port;
    private Dispatcher dispatcher;

    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);
            try (Socket socket = serverSocket.accept()) {
                byte[] buffer = new byte[8192];
                int n = socket.getInputStream().read(buffer);
                String rawRequest = new String(buffer, 0, n);
                HttpRequest request = new HttpRequest(rawRequest);
                request.printInfo(true);
                dispatcher.execute(request, socket.getOutputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
