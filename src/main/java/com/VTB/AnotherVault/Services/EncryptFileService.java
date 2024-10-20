package com.VTB.AnotherVault.Services;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpHeaders;

@Service
public class EncryptFileService {

    private static final String SERVER_URL = "http://31.128.38.18:8080/encrypt-file/";

    public void encryptFile(String filePath) throws IOException, InterruptedException {
        // Создание HTTP клиента
        HttpClient client = HttpClient.newHttpClient();

        // Создание запроса для отправки файла на сервер
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SERVER_URL))
                .header("Content-Type", "multipart/form-data; boundary=--boundary")
                .POST(BodyPublishers.ofFile(Path.of(filePath)))
                .build();

        // Отправка запроса и получение ответа
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Обработка ответа
        if (response.statusCode() == 200) {
            System.out.println("File encrypted successfully: " + response.body());
        } else {
            System.out.println("Failed to encrypt file: " + response.statusCode());
        }
    }
}
