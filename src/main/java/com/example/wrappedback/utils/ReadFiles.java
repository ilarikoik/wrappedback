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

import com.example.wrappedback.web.ArtistDetails;
import com.example.wrappedback.web.SongDetails;
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

    // KAPPALEIDEN TIEDOT
    public static List<SongDetails> processJsonFiles(String directory) {
        Set<String> fileNames = listFilesUsingJavaIO(directory); // Hakee tiedostot hakemistosta
        ObjectMapper objectMapper = new ObjectMapper(); // Jacksonin ObjectMapper JSON-käsittelyyn
        List<SongDetails> songs = new ArrayList<>();

        //
        for (String fileName : fileNames) {
            // Varmistetaan, että tiedosto on JSON ja alkaa "StreamingHistory"-sanoilla
            if (!fileName.endsWith(".json") || !fileName.startsWith("StreamingHistory")) {
                continue;
            }
            String filePath = directory + "/" + fileName;
            File file = new File(filePath);
            //
            try {
                JsonNode rootNode = objectMapper.readTree(file);
                if (rootNode.isArray()) {
                    for (JsonNode node : rootNode) {
                        String songName = node.get("trackName").toString();
                        Integer ms = node.get("msPlayed").asInt(); // uudet msPlayed
                        System.out.println(songName);
                        SongDetails found = songs.stream()
                                .filter(s -> s.getSong().equals(songName)) // Varmistetaan, että kappaleen nimi täsmää
                                .findFirst() // Hae ensimmäinen vastaava kappale
                                .orElse(null);
                        if (found != null) {
                            // pitää saada lisättyy olemassa olevaan objektiin
                            found.setMs(found.getMs() + ms);
                            found.setTimesPlayed(found.getTimesPlayed() + 1);
                        }
                        if (found == null) {
                            // luodaan objekti ja lisätää lsitaan
                            SongDetails song = new SongDetails();
                            song.setSong(songName);
                            song.setMs(ms);
                            song.setTimesPlayed(1);
                            songs.add(song);
                        }
                    }
                } else {
                    System.out.println("VIIIRHE");
                }
            } catch (Exception e) {
                // TODO: handle exception
            }

        }
        // System.out.println(songs);
        return songs;
    }

    // ARTISTIT JA NIIDEN KUUNTELUKERRAT

    public static List<ArtistDetails> processJsonFilesArtists(String directory) {
        Set<String> fileNames = listFilesUsingJavaIO(directory); // Hakee tiedostot hakemistosta
        ObjectMapper objectMapper = new ObjectMapper(); // Jacksonin ObjectMapper JSON-käsittelyyn
        List<ArtistDetails> artists = new ArrayList<>();

        for (String fileName : fileNames) {
            // Varmistetaan, että tiedosto on JSON ja alkaa "StreamingHistory"-sanoilla
            if (!fileName.endsWith(".json") || !fileName.startsWith("StreamingHistory")) {
                continue;
            }
            String filePath = directory + "/" + fileName;
            File file = new File(filePath);
            //
            try {
                JsonNode rootNode = objectMapper.readTree(file);
                if (rootNode.isArray()) {
                    for (JsonNode node : rootNode) {
                        String artistName = node.get("artistName").toString();
                        Integer ms = node.get("msPlayed").asInt();
                        System.out.println(artistName);
                        ArtistDetails found = artists.stream()
                                .filter(s -> s.getArtist().equals(artistName))
                                .findFirst()
                                .orElse(null);
                        if (found != null) {
                            // pitää saada lisättyy olemassa olevaan objektiin
                            found.setMs(found.getMs() + ms);
                            found.setTimesPlayed(found.getTimesPlayed() + 1);
                        }
                        if (found == null) {
                            // luodaan objekti ja lisätää lsitaan
                            ArtistDetails artist = new ArtistDetails();
                            artist.setArtist(artistName);
                            artist.setMs(ms);
                            artist.setTimesPlayed(1);
                            artists.add(artist);
                        }
                    }
                } else {
                    System.out.println("VIIIRHE");
                }
            } catch (Exception e) {
                // TODO: handle exception
            }

        }
        System.out.println(artists);
        return artists;
    }

    // PODCASTEILLE TIEDOT

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