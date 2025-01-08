package com.example.wrappedback.web;

import org.springframework.web.bind.annotation.*;

import com.example.wrappedback.utils.ReadFiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/datas")
    public List<SongDetails> getAll() {
        List<SongDetails> tulos = ReadFiles.processJsonFiles("data/Spotify Account Data");
        return tulos;
    }

    @GetMapping("/artists")
    public List<ArtistDetails> getArtists() {
        List<ArtistDetails> tulos = ReadFiles.processJsonFilesArtists("data/Spotify Account Data");
        return tulos;
    }

    @GetMapping("/test")
    public List<SongDetails> getTest() {
        List<SongDetails> tulos = ReadFiles.processJsonFilesTestData("data/Spotify Account Data/test.json");
        return tulos;
    }

}
