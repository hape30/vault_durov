package com.VTB.AnotherVault.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.exception.SdkException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public File downloadFile(String bucketName, String keyName, String downloadPath) {
        File downloadedFile = new File(downloadPath);
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            // Получаем файл с S3
            ResponseInputStream<GetObjectResponse> s3Object = cloClient.getObject(getObjectRequest);

            // Сохраняем файл локально
            try (FileOutputStream fos = new FileOutputStream(downloadedFile)) {
                byte[] readBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = s3Object.read(readBuffer)) > 0) {
                    fos.write(readBuffer, 0, bytesRead);
                }
                logger.info("Файл '{}' успешно выгружен из bucket '{}'.", keyName, bucketName);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (SdkException e) {
            logger.error("Ошибка при выгрузке файла '{}': {}", keyName, e.getMessage());
            throw new RuntimeException("Ошибка при выгрузке файла из CLO", e);
        }

        return downloadedFile;
    }

}
