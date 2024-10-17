package com.VTB.AnotherVault.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.exception.SdkException;

import java.nio.file.Paths;

@Service
public class CLOService {

    private final S3Client cloClient;
    private static final Logger logger = LoggerFactory.getLogger(CLOService.class);

    public CLOService(S3Client cloClient) {
        this.cloClient = cloClient;
    }

    public void uploadFile(String bucketName, String keyName, String filePath) {
        try {

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            // Загрузка файла
            cloClient.putObject(request, Paths.get(filePath));
            logger.info("Файл '{}' успешно загружен в bucket '{}'.", keyName, bucketName);
        } catch (SdkException e) {
            logger.error("Ошибка при загрузке файла '{}': {}", keyName, e.getMessage());
            throw new RuntimeException("Ошибка при загрузке файла в CLO", e);
        } catch (Exception e) {
            logger.error("Неизвестная ошибка при загрузке файла '{}': {}", keyName, e.getMessage());
            throw new RuntimeException("Неизвестная ошибка при загрузке файла", e);
        }
    }

}
