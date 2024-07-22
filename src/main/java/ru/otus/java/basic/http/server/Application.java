package ru.otus.java.basic.http.server;

public class Application {
    /*

    Домашнее задание:
    - Избавиться от System.out.println, перейти на использование логирования
    - Сделайте парсинг заголовков запроса в Map<String, String>
    - * Добавьте возможность удалять продукты через DELETE /items?id=10 (т.е. сервер должен удалить продукт с ид=10)

     */

    public static void main(String[] args) {
        new HttpServer(8189).start();
    }
}
