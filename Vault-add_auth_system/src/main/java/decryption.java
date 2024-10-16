import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.http.HttpRequest.BodyPublishers;
import com.google.gson.JsonObject;

public class DecryptFileClient {

    private static final String SERVER_URL = "http://localhost:8000/decrypt-file/";

    public static void main(String[] args) throws IOException, InterruptedException {
        // Зашифрованный файл и необходимые данные
        String encryptedFilePath = "path/to/encrypted_file.txt";
        String nonce = "your_nonce_value";
        String tag = "your_tag_value";
        String encryptedKey = "your_encrypted_key_value";

        // Создание HTTP клиента
        HttpClient client = HttpClient.newHttpClient();

        // Создание JSON объекта для отправки данных
        JsonObject jsonRequest = new JsonObject();
        jsonRequest.addProperty("encrypted_file_path", encryptedFilePath);
        jsonRequest.addProperty("nonce", nonce);
        jsonRequest.addProperty("tag", tag);
        jsonRequest.addProperty("encrypted_key", encryptedKey);

        // Создание запроса для расшифровки файла
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SERVER_URL))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(jsonRequest.toString()))
                .build();

        // Отправка запроса и получение ответа
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Обработка ответа
        if (response.statusCode() == 200) {
            System.out.println("File decrypted successfully: " + response.body());
        } else {
            System.out.println("Failed to decrypt file: " + response.statusCode());
        }
    }
}
