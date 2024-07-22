package ru.otus.java.basic.http.server.processors;

import ru.otus.java.basic.http.server.BadRequestException;
import ru.otus.java.basic.http.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CalculatorRequestProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest request, OutputStream out) throws IOException {
        if (!request.containsParameter("a")) {
            throw new BadRequestException("Parameter 'a' is missing");
        }
        if (!request.containsParameter("b")) {
            throw new BadRequestException("Parameter 'b' is missing");
        }
        int a, b;
        try {
            a = Integer.parseInt(request.getParameter("a"));
        } catch (NumberFormatException e) {
            throw new BadRequestException("Parameter 'a' has incorrect type");
        }
        try {
            b = Integer.parseInt(request.getParameter("b"));
        } catch (NumberFormatException e) {
            throw new BadRequestException("Parameter 'b' has incorrect type");
        }

        String result = a + " + " + b + " = " + (a + b);

        String response = "" +
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body><h1>" + result + "</h1></body></html>";
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
