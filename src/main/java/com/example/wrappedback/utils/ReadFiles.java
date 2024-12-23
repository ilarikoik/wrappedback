package com.example.wrappedback.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadFiles {

    public static Set<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    // public static HashMap<String, Long> processJsonFiles(String directory) {
    // Set<String> fileNames = listFilesUsingJavaIO(directory);
    // ObjectMapper objectMapper = new ObjectMapper();
    // HashMap<String, Long> map = new HashMap<>();

    // for (String fileName : fileNames) {
    // if (!fileName.endsWith(".json")) {
    // continue;
    // }
    // if (!fileName.startsWith("StreamingHistory")) {
    // continue;
    // }
    // String filePath = directory + "/" + fileName;

    // try {
    // JsonNode rootNode = objectMapper.readTree(new File(filePath));
    // if (rootNode.isArray()) {
    // for (JsonNode node : rootNode) {
    // System.out.println("---+-++-+--+" + node);
    // String podcastName = null;
    // String song = null;
    // if (node.get("podcastName") != null) {
    // podcastName = node.get("podcastName").asText();
    // }
    // if (node.get("trackName") != null) {
    // song = node.get("trackName").asText();
    // }

    // long msPlayed = node.get("msPlayed").asLong();
    // if (map.containsKey(podcastName)) {
    // long updatedMsPlayed = map.get(podcastName) + msPlayed;
    // map.replace(podcastName, updatedMsPlayed);
    // } else if (map.containsKey(song)) {
    // long updatedMsPlayed = map.get(song) + msPlayed;
    // map.replace(song, updatedMsPlayed);
    // } else {
    // if (podcastName != null) {
    // map.put(podcastName, msPlayed);
    // }
    // if (song != null) {
    // map.put(song, msPlayed);
    // }
    // }
    // }

    // }
    // } catch (IOException e) {
    // System.err.println("Virhe tiedostoa käsiteltäessä: " + fileName);
    // e.printStackTrace();
    // }
    // }
    // return map;
    // }

    public static HashMap<String, Long> processJsonFiles(String directory) {
        Set<String> fileNames = listFilesUsingJavaIO(directory); // Hakee tiedostot hakemistosta
        ObjectMapper objectMapper = new ObjectMapper(); // Jacksonin ObjectMapper JSON-käsittelyyn
        HashMap<String, Long> map = new HashMap<>(); // HashMap, johon tallennetaan tiedot

        // Käydään läpi tiedostot hakemistossa
        for (String fileName : fileNames) {
            // Varmistetaan, että tiedosto on JSON ja alkaa "StreamingHistory"-sanoilla
            if (!fileName.endsWith(".json") || !fileName.startsWith("StreamingHistory")) {
                continue;
            }
            String filePath = directory + "/" + fileName;
            try {
                // Luetaan JSON-tiedosto
                JsonNode rootNode = objectMapper.readTree(new File(filePath));
                // Tarkistetaan, että JSON on taulukko
                if (rootNode.isArray()) {
                    for (JsonNode node : rootNode) {
                        String podcastName = null;
                        String song = null;
                        // Haetaan mahdolliset kentät
                        if (node.has("podcastName")) {
                            podcastName = node.get("podcastName").asText();
                        }
                        if (node.has("trackName")) {
                            song = node.get("trackName").asText();
                        }
                        long msPlayed = node.get("msPlayed").asLong(); // Haetaan soittotunnit
                        // Lisätään data HashMapiin
                        if (podcastName != null) {
                            map.put(podcastName, map.getOrDefault(podcastName, 0L) + msPlayed);
                        }
                        if (song != null) {
                            map.put(song, map.getOrDefault(song, 0L) + msPlayed);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Virhe tiedostoa käsiteltäessä: " + fileName);
                e.printStackTrace();
            }
        }
        return map;
    }

    public static String toJson(HashMap<String, Long> map) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = mapper.writeValueAsString(map);

            return jsonString;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
