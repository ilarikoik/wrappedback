package com.example.wrappedback.web;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.wrappedback.domain.FileUploadUtil;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/files")
public class UploadController {

    @Value("${file.upload-dir}") // Polku määritetään application.properties-tiedostossa
    private String uploadDir;

    @PostMapping("/upload-zip")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile zipFile) throws Exception {
        StringBuilder result = new StringBuilder("ZIP-tiedoston sisältö: \n");

        try (ZipInputStream zipInputStream = new ZipInputStream(zipFile.getInputStream())) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                result.append("Tiedosto -->: ").append(entry.getName()).append("\n");
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Virhe tiedoston käsittelyssä: " + e.getMessage());
        }

        try {
            // Tallenna tiedosto omaan kansioon
            FileUploadUtil.saveFile(uploadDir, zipFile);
            // Jos tiedosto on ZIP, purkaa se
            if (zipFile.getOriginalFilename().endsWith(".zip")) {
                String zipFilePath = uploadDir + "/" + zipFile.getOriginalFilename();
                FileUploadUtil.extractZipFile(zipFilePath, uploadDir);
            }
            return ResponseEntity.ok("Tiedosto ladattu ja käsitelty onnistuneesti.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Virhe tiedoston käsittelyssä: " + e.getMessage());
        }
    }
}
