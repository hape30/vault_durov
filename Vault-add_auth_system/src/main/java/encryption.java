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

public class EncryptFileClient {

    private static final String SERVER_URL = "http://localhost:8000/encrypt-file/";

    public static void main(String[] args) throws IOException, InterruptedException {
        // Путь к файлу, который нужно зашифровать
        Path filePath = Paths.get("path/to/your/file.txt");

        // Создание HTTP клиента
        HttpClient client = HttpClient.newHttpClient();

        // Создание запроса для отправки файла на сервер
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SERVER_URL))
                .header("Content-Type", "multipart/form-data; boundary=--boundary")
                .POST(BodyPublishers.ofFile(filePath))
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
