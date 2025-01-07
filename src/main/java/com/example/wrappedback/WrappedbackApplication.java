package com.example.wrappedback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.wrappedback.utils.ReadFiles;

@SpringBootApplication
public class WrappedbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(WrappedbackApplication.class, args);
		System.out.println(" \n\n\nJJJOU");
		// HashMap<String, Long> tulos = ReadFiles.processJsonFiles("data/Spotify
		// Account Data");
		// String json = ReadFiles.toJson(tulos);
		// System.out.println(json);
		// System.out.println(json.getClass().getName());

		System.out.println(ReadFiles.processJsonFiles("data/Spotify Account Data"));
	}

}
