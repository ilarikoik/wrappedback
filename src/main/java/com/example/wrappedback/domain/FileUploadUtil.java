package com.example.wrappedback.domain;

import java.nio.file.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

    // Tallentaa tiedoston määritettyyn kansioon
    public static void saveFile(String uploadDir, MultipartFile file) throws IOException {
        String projectRoot = System.getProperty("user.dir"); // Hakee projektin juuren
        File uploadDirectory = new File(projectRoot, uploadDir); // Määritetään uploadDir suhteessa projektin juureen

        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs(); // Luo kansion, jos sitä ei ole
        }

        String filePath = uploadDirectory.getPath() + File.separator + file.getOriginalFilename();
        File dest = new File(filePath);

        file.transferTo(dest); // Siirtää ladatun tiedoston kohdekansioon
    }

    public static void extractZipFile(String zipFilePath, String destDir) throws IOException {
        File destDirFile = new File(destDir);
        if (!destDirFile.exists()) {
            destDirFile.mkdirs(); // Luo kohdekansion, jos sitä ei ole
        }

        try (ZipInputStream zipIn = new ZipInputStream(Files.newInputStream(Paths.get(zipFilePath)))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                String entryName = entry.getName();
                System.out.println("Purkamassa tiedostoa: " + entryName);

                // Tarkistetaan, että tiedosto kuuluu kansioon "Spotify Account Data"
                if (entryName.startsWith("Spotify Account Data")) {
                    // Luodaan oikea polku purkamista varten
                    File newFile = new File(destDir + File.separator + entryName);

                    // Jos kyseessä on kansio, luodaan se
                    if (entry.isDirectory()) {
                        newFile.mkdirs();
                    } else {
                        // Jos tiedosto, kirjoitetaan tiedosto
                        try (BufferedOutputStream bos = new BufferedOutputStream(
                                Files.newOutputStream(newFile.toPath()))) {
                            byte[] bytesIn = new byte[4096];
                            int read;
                            while ((read = zipIn.read(bytesIn)) != -1) {
                                bos.write(bytesIn, 0, read);
                            }
                        }
                    }
                }
                zipIn.closeEntry();
            }
        }
    }
}