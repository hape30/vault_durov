package com.VTB.AnotherVault.Controllers;

import com.VTB.AnotherVault.Services.CLOService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class CLOController {

    private final CLOService cloService;

    @Autowired
    public CLOController(CLOService cloService) {

        this.cloService = cloService;
    }

    @PostMapping("/upload/")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println("Получен запрос на загрузку файла: {}" + file.getOriginalFilename());

        if (file == null) {
            System.out.println("Файл не был получен.");
            return ResponseEntity.badRequest().body("Файл не был получен.");
        }

        if (file.isEmpty()) {
            System.out.println("Файл не выбран для загрузки.");
            return ResponseEntity.badRequest().body("Файл не выбран для загрузки");
        }

        String originalFileName = file.getOriginalFilename();
        if (originalFileName != null && originalFileName.contains("%0A")) {
            System.out.println("Имя файла содержит недопустимые символы: {}" + originalFileName);
            return ResponseEntity.badRequest().body("Имя файла содержит недопустимые символы.");
        }

        File tempFile = null;
        try {
            // Сохраняем файл временно
            tempFile = File.createTempFile("temp", originalFileName != null ? originalFileName.replace("%0A", "") : "temp");
            file.transferTo(tempFile);

            // Загружаем файл в S3
            String sanitizedFileName = originalFileName != null ? originalFileName.replace("%0A", "") : "temp";
            String sanitizedTempFilePath = tempFile.getAbsolutePath().replace("%0A", "");
            System.out.println("Имя файла: {}" + sanitizedFileName);
            System.out.println("Путь файла: {}" + sanitizedTempFilePath);

            cloService.uploadFile("s3-user-91c47d-default-bucket", sanitizedFileName, sanitizedTempFilePath);

            return ResponseEntity.ok("Файл успешно загружен");
        } catch (IOException e) {
            System.out.println("Ошибка загрузки файла: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка загрузки файла: " + e.getMessage());
        } finally {
            // Удаляем временный файл, если он был создан
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
