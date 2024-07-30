package ru.otus.java.basic.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private int port;
    private Dispatcher dispatcher;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                 OutputStream out = socket.getOutputStream()) {
                                StringBuilder rawRequest = new StringBuilder();
                                String line;
                                while (in.ready() && !(line = in.readLine()).isEmpty()) {
                                    rawRequest.append(line).append("\r\n");
                                }
                                if(rawRequest.toString().isEmpty()){
                                    throw new RuntimeException("Пустой запрос Postman");
                                }
                                rawRequest.append("\r\n");
                                HttpRequest request = new HttpRequest(rawRequest.toString());
                                request.loggingInfo();
                                dispatcher.execute(request, out);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
