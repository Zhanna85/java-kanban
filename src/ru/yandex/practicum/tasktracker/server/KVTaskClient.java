package ru.yandex.practicum.tasktracker.server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final String responseBody; // тело ответа которое выдается при регистрации.
    private final URI url; // URL к серверу хранилища
    public KVTaskClient(URI url) throws IOException, InterruptedException {
        this.url = url;

        // создаём экземпляр URI, содержащий адрес нужного ресурса
        URI uri = URI.create(url + "/register");

        // HTTP-клиент с настройками по умолчанию
        HttpClient client = HttpClient.newHttpClient();

        // создаём объект, описывающий HTTP-запрос
        HttpRequest request = HttpRequest.newBuilder() // получаем экземпляр билдера
                .uri(uri) // указываем адрес ресурса
                .GET()    // указываем HTTP-метод запроса
                .version(HttpClient.Version.HTTP_1_1) // указываем версию протокола
                .header("Content-type", "application/json") // название заголовка и его значение
                .build(); // заканчиваем настройку и создаём ("строим") http-запрос

        // отправляем запрос и получаем HTTP-ответ от сервера
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // получаем тело ответа в виде строки - токен (API_TOKEN)
        responseBody = response.body();
    }

    // сохраняем состояние менеджера задач через запрос с методом POST
    public void put(String key, String json) {
        HttpClient client = HttpClient.newHttpClient();
        URI newUri = URI.create(url + "/save/" + key + "?API_TOKEN=" + responseBody);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(newUri)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Content-type", "application/json")
                .build();
        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса ресурса по URL-адресу: '" + newUri + "', возникла ошибка.\n"
                    + "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
    }

    // возвращаем состояние менеджера задач через запрос с методом GET
    public String load(String key) throws IOException, InterruptedException {
        URI newUrl = URI.create(url + "/load/" + key + "?API_TOKEN=" + responseBody);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(newUrl)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
