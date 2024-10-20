package com.VTB.AnotherVault.Controllers;

import com.VTB.AnotherVault.Configurations.SecurityConfig;
import com.VTB.AnotherVault.Entities.AuthRequest;
import com.VTB.AnotherVault.Services.CLOService;
import com.VTB.AnotherVault.Services.EncryptFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@Tag(name = "File API", description = "API для управления S3")
public class CLOController {

    private final CLOService cloService;
    private final EncryptFileService encryptFileService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public CLOController(CLOService cloService, EncryptFileService encryptFileService) {

        this.cloService = cloService;

        this.encryptFileService = encryptFileService;
    }


    @GetMapping("/")
    public ResponseEntity<String> S3SystemPage(){
        return ResponseEntity.ok("S3 System");
    }

    @Operation(summary = "Система выгрузки файла из S3")
    @GetMapping("/download/")
    public ResponseEntity<InputStreamResource> downloadFile(
            @RequestParam("fileName") String fileName) {

        // Локальный путь для сохранения скаченного файла (временно)
        String downloadPath = System.getProperty("java.io.tmpdir") + fileName;

        try {
            // Вызываем метод для скачивания файла
            File downloadedFile = cloService.downloadFile("s3-user-91c47d-default-bucket", fileName, downloadPath);

            if (!downloadedFile.exists()) {
                System.out.println("Файл не найден после загрузки с S3: " + fileName);
                return ResponseEntity.notFound().build();
            }

            // Подготовка ответа с файлом
            InputStreamResource resource = new InputStreamResource(new FileInputStream(downloadedFile));

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downloadedFile.getName());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(downloadedFile.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: файл не найден: " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Система загрузки файла в S3")
    @PostMapping("/upload/")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestBody AuthRequest authRequest) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );


            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("Получен запрос на загрузку файла: " + file.getOriginalFilename());

            if (file == null) {
                System.out.println("Файл не был получен.");
                return ResponseEntity.badRequest().body("Файл не был получен.");
            }

            if (file.isEmpty()) {
                System.out.println("Файл не выбран для загрузки.");
                return ResponseEntity.badRequest().body("Файл не выбран для загрузки.");
            }

            String originalFileName = file.getOriginalFilename();
            if (originalFileName != null && originalFileName.contains("%0A")) {
                System.out.println("Имя файла содержит недопустимые символы: " + originalFileName);
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
                System.out.println("Имя файла: " + sanitizedFileName);
                System.out.println("Путь файла: " + sanitizedTempFilePath);

                encryptFileService.encryptFile(sanitizedTempFilePath);
                cloService.uploadFile("s3-user-91c47d-default-bucket", sanitizedFileName, sanitizedTempFilePath);

                return ResponseEntity.ok("Файл успешно загружен");
            } catch (IOException e) {
                System.out.println("Ошибка загрузки файла: " + e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Ошибка загрузки файла: " + e.getMessage());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                // Удаляем временный файл, если он был создан
                if (tempFile != null && tempFile.exists()) {
                    tempFile.delete();
                }
            }

        } catch (Exception e) {
            System.out.println("Ошибка аутентификации: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ошибка аутентификации: " + e.getMessage());
        }
    }

}
